package server;

import java.io.*;
import java.net.*;
import java.util.ArrayDeque;
import java.util.Scanner;

import javax.net.ssl.SSLServerSocketFactory;

public class Servidor {
	public static final int port = 1234;
	private static final String LOCATIOKEY = "./img/key.jks";
	private static final String LOCATIOKEY_PASSWORD = "password";
	private double[] apuestas;
	private int[] hourses;
	private int[] moves;
	ServerSocket socket;
	ArrayDeque<Integer> orden = new ArrayDeque<>();

	public Servidor() {
		apuestas = new double[6];
		hourses = new int[6];
		for (int i = 0; i < hourses.length; i++) {
			hourses[i] = 10;
		}
		moves = new int[] { 10, 20, 30, 15, 5 };
	}

	public synchronized double[] getApuestas() {
		return apuestas;
	}

	public synchronized void setApuestas(double[] apuestas) {
		this.apuestas = apuestas;
	}

	public synchronized int[] getHourses() {
		return hourses;
	}

	public synchronized void setHourses(int[] hourses) {
		this.hourses = hourses;
	}

	public synchronized int[] getMoves() {
		return moves;
	}

	public synchronized void setMoves(int[] moves) {
		this.moves = moves;
	}

	public synchronized void stop() {
		try {

			socket.close();
		} catch (IOException e) {

		}
	}

	public void apostar() {
		GroupThread group = new GroupThread();
		starTransmision();
		try {
			socket = getSocket();
			Runnable run = new Runnable() {

				@Override
				public void run() {
					Scanner sc = new Scanner(System.in);
					boolean start = false;
					while (!start) {
						String l = sc.nextLine();
						start = l.equals("Start");
					}

					stop();
					sc.close();
				}
			};
			new Thread(run).start();
			while (true) {
				Socket tmp = socket.accept();
				new Clientes(tmp, this, group);
			}
		} catch (Exception e) {

		}
		try {
			AnimarCaballos animacion = new AnimarCaballos(this);
			group.addHilo(animacion);
			group.startAll();
			System.out.println("Empezo");
			while (animacion.enable()) {
				Thread.sleep(100);
			}
			for (int i = 0; i < apuestas.length; i++) {
				System.out.println((i + 1) + " " + apuestas[i]);
			}
			int w = winner();
			System.out.println("El ganador es el Caballo " + w);
		} catch (Exception e) {

		}

	}

	private void starTransmision() {
		Runnable run = new Runnable() {
			@Override
			public void run() {
				new Streaming(true);
			}
		};
		new Thread(run).start();
		// Runnable run1=new Runnable() {
		// @Override
		// public void run() {
		// new Streaming(false);
		// }
		// };
		// new Thread(run1).start();

	}

	private ServerSocket getSocket() throws IOException {
		System.setProperty("javax.net.ssl.keyStore", LOCATIOKEY);
		System.setProperty("javax.net.ssl.keyStorePassword", LOCATIOKEY_PASSWORD);
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		ServerSocket socket = ssf.createServerSocket(port);
		// ServerSocket socket=new ServerSocket(port);
		return socket;
	}

	public synchronized int winner() {

		return orden.getFirst();
	}

	public synchronized void updateApuestas(int c, double ap) {
		apuestas[c] += ap;
	}

	public static void main(String[] args) {
		new Servidor().apostar();
	}

	public synchronized void ready() {
		System.out.println("Ready");
	}
}
