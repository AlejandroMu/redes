package cliente;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;


public class apuestasTCP extends Thread{
	public static final int port=1234;
	public static final String LOCATIOKEY="./img/key.jks";
	private Cliente cliente;
	private int hourse;
	private double apuesta;
	private String idCliente;
	public apuestasTCP(Cliente c,int ap,double apu,String id) {
		cliente=c;
		idCliente=id;
		this.hourse=ap;
		apuesta=apu;
		startStreaming();
	
	}
	private void startStreaming() {
		Runnable run=new Runnable() {
			@Override
			public void run() {
				new HiloStreamingAudio("224.0.0.3",8001);
				
			}
		};
		new Thread(run).start();
		
	}
	@Override
	public void run() {
		try {
			Socket socket=getSocket();
			BufferedReader lec=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter es=new PrintWriter(socket.getOutputStream(),true);
			String msm=hourse+" "+apuesta+" "+idCliente;
			es.println(msm);

			String l=lec.readLine();
			while(!l.equals("end")) {
				cliente.setHourses(l);
				l=lec.readLine();
			}
			l=lec.readLine();
			cliente.setWiner(l);
			System.out.println("End");
			lec.close();
			es.close();
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private Socket getSocket() throws Exception {
		System.setProperty("javax.net.ssl.trustStore", LOCATIOKEY);
		SSLSocketFactory ssf=(SSLSocketFactory) SSLSocketFactory.getDefault();
		Socket socket=ssf.createSocket(getAddress(), port);	
//		Socket socket=new Socket(getAddress(), port);
		return socket;
	}
	private String getAddress() throws UnknownHostException {
		// TODO Auto-generated method stub
		return InetAddress.getLocalHost().getHostName();
	}
}
