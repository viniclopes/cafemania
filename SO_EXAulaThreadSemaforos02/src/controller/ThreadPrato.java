package controller;

import java.util.concurrent.Semaphore;

public class ThreadPrato extends Thread{
	private int idPrato;
	private String NomePrato;
	private double tempodePreparo;
	private double temposendoPreparado=0;
	private Semaphore smEntrega;
	
	public ThreadPrato(int id,Semaphore sm) {
		this.idPrato=id;
		String [][] temp = new String[2][3];
		String r0[]= {"Lasanha a Bolonesa","0.6","1.2"};
		String r1[]= {"Sopa de Cebola","0.5","0.8"};
		temp[0]=r0;
		temp[1]=r1;
		this.NomePrato=temp[id%2][0];
		this.tempodePreparo=(Math.random()*(Double.parseDouble(temp[id%2][2])-(Double.parseDouble(temp[id%2][1])))+Double.parseDouble(temp[id%2][1]));
		System.out.println(this.tempodePreparo);
		this.smEntrega=sm;
	}
	
	@Override
	public void run() {
		System.out.println("A preparação do "+this.NomePrato+" Foi Iniciada("+this.idPrato+")");
		prepararPrato();
		entregarPrato();
	}
	
	private void entregarPrato() {
		try {
			smEntrega.acquire();
			System.out.println("A Entrega do Prato : "+this.NomePrato+"("+this.idPrato+") Esta Sendo Feita");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			smEntrega.release();
		}
		
	}

	private void prepararPrato() {
		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.temposendoPreparado+=0.1;
			System.out.println("A preparação do "+this.NomePrato+" Esta em "+((int)(100*this.temposendoPreparado/tempodePreparo))+"%("+this.idPrato+")");
		} while (temposendoPreparado<=tempodePreparo);
	}
	
}
