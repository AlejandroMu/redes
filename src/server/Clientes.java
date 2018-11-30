package server;

import java.io.*;
import java.net.Socket;

public class Clientes extends Thread {
	private Socket socket;
	private BufferedReader br;
	private PrintWriter bw;
	private Servidor servidor;
	private boolean enable;
	GroupThread gro;
	public Clientes(Socket sc, Servidor ser,GroupThread gr) {
		servidor = ser;
		socket = sc;
		enable = true;
		gr.addHilo(this);
		gro=gr;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new PrintWriter(socket.getOutputStream(),true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setEnable(boolean a) {
		enable=a;
	}
	public void startNotification() {
		bw.write("start");
		System.out.println("notificado");
		bw.flush();
	}

	@Override
	public void run() {
		try {
			String line[] = br.readLine().split(" ");
			int hourse = Integer.parseInt(line[0]);
			double apuesta = Double.parseDouble(line[1]);
			servidor.updateApuestas(hourse, apuesta);
			while (enable) {
				int[] ho=servidor.getHourses();
				String ms="";
				boolean end=true;
				for (int i = 0; i < ho.length; i++) {
					end=end&&ho[i]>1000;
					ms+=ho[i]+" ";
				}
				bw.println(ms);
				if(end) {
					gro.stopAll();;
				}
				Thread.sleep(100);
			}
			bw.println("end");
			int winner=servidor.winner();
			bw.println(winner+"");
			br.close();
			bw.close();
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
			
	}

}
