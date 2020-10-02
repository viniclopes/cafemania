package view;

import java.util.concurrent.Semaphore;

import controller.ThreadsRequisicao;

public class Main {

	public static void main(String[] args) {
		Semaphore smTransacaoBd = new Semaphore(1);
		ThreadsRequisicao tr []= new ThreadsRequisicao[21];
		
		for (int i = 0; i < tr.length; i++) {
			tr[i]= new ThreadsRequisicao(i, smTransacaoBd);
		}
		for (int i = 0; i < tr.length; i++) {
			tr[i].start();
		}
		

	}

}
