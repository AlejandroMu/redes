package server;

import java.io.IOException;
import java.net.*;

public class ServiceMusic {
	
	public static void main(String[] args) throws IOException {
		ServerSocket ss=new ServerSocket(8801);
		TransmitirMusica sound=new TransmitirMusica();
		sound.start();
		while(true) {
			sound.addSocket(ss.accept());
		}
	}

}
