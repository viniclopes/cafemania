package view;

import java.util.concurrent.Semaphore;

import controller.ThreadPrato;

public class Main {

	public static void main(String[] args) {
		Semaphore smEntrega=  new Semaphore(1);
		ThreadPrato tp[] = new ThreadPrato[5];
		for (int i = 0; i < tp.length; i++) {
			tp[i]=new ThreadPrato(i, smEntrega);
		}
		for (int i = 0; i < tp.length; i++) {
			tp[i].start();
		}
	}

}
