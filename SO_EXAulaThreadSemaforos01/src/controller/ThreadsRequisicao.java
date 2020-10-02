package controller;

import java.util.concurrent.Semaphore;

public class ThreadsRequisicao extends Thread{
	private int id;
	private int qtdeTransacoes;
	private double tempoMinCalc;
	private int multiTempCalc;
	private double tempoTransacaoBD;
	private int transacoesFeitas=0;
	private Semaphore smTransacaoBD;
	
	public ThreadsRequisicao(int id,Semaphore smTransacaoBD) {
		this.id=id;
		double temp[][]  = new double [3][4];
		double r1[]={2,0.2,5,1};
		double r2[]={3,0.5,3,1.5};
		double r0[]={3,1,2,1.5};
		temp[0]=r0;
		temp[1]=r1;
		temp[2]=r2;
		int resto = id%(temp.length);
		this.qtdeTransacoes=(int)temp[resto][0];
		this.tempoMinCalc=temp[resto][1];
		this.multiTempCalc=(int)temp[resto][2];
		this.tempoTransacaoBD=temp[resto][3];
		this.smTransacaoBD=smTransacaoBD;
	}
	
	@Override
	public void run() {
		do {
			transacao();
			transacoesFeitas++;
		} while (transacoesFeitas<qtdeTransacoes);
	}

	private void transacao() {
		calculo();
		transacaoBD();
	}

	private void transacaoBD() {
		try {
			this.smTransacaoBD.acquire();
			try {
				Thread.sleep((long) (this.tempoTransacaoBD*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("A Transação de BD da Thread #"+this.id+" Levou "+String.format("%.2f",this.tempoTransacaoBD ) +" Segundos");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}finally {
			this.smTransacaoBD.release();
		}
	}

	private void calculo() {
		double tempoCalculo=((Math.random()*((this.tempoMinCalc*this.multiTempCalc)-this.tempoMinCalc))+this.tempoMinCalc);
		try {
			Thread.sleep((long) (tempoCalculo*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("A Transação de calculo da Thread #"+this.id+" Levou "+String.format("%.2f",tempoCalculo ) +" Segundos");
	}
}
