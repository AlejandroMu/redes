package server;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TransmitirMusica extends Thread {
	private final byte audioBuffer[] = new byte[10000];
	private InputStream intputStrem;
	private String route = "./img/song.wav";
	private ArrayList<PrintWriter> sockets;
	

	public TransmitirMusica() {
		sockets = new ArrayList<>();
		File file = new File(route);
		try {
			intputStrem = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addSocket(Socket s) {
		try {
			sockets.add(new PrintWriter(s.getOutputStream(), true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		try {

			while (true) {
				int count = intputStrem.read(audioBuffer, 0, audioBuffer.length);
				if (count > 0) {
					for (PrintWriter b : sockets) {
						b.println(audioBuffer);
					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
