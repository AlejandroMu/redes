package cliente;

import java.io.*;
import java.net.*;


import javax.sound.sampled.*;


public class ReproductorMusic {

	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;
	private String route="./img/song.wav";

	public ReproductorMusic() {
		initiateAudio();
	}
	
	public void initiateAudio() {
		try {
			File file=new File(route);
			audioInputStream=AudioSystem.getAudioInputStream(file);
			sourceDataLine=AudioSystem.getSourceDataLine(audioInputStream.getFormat());
			sourceDataLine.open(audioInputStream.getFormat());
			sourceDataLine.start();
			playAudio();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void playAudio() {
		byte[] buffer = new byte[10000];
		try {
			int count;
			while ((count = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
				if (count > 0) {
					sourceDataLine.write(buffer, 0, count);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	public static void main(String[] args) {
		new ReproductorMusic().initiateAudio();
	}
}
