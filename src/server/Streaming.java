package server;

import java.io.File;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
/**
 * 
 * @author alejandro
 *	trasmite la voz del locutor a los clientes usando udp Streaming 
 */
public class Streaming {
	private final byte audioBuffer[] = new byte[10000];
	private TargetDataLine targetDataLine;
	private boolean type;
	private InputStream intputStrem;
	private String route="./img/song.wav";
	private int port;
	public Streaming(boolean t) {
		type=t;
		setupAudio();
		broadcastAudio();
		
	}

	public AudioFormat getAudioFormat() {
		float sampleRate = 16000F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	private void broadcastAudio() {
		try {
			DatagramSocket socket = getSocket();
			InetAddress inetAddres = InetAddress.getByName("224.0.0.3");
			while (true) {
				int count = intputStrem.read(audioBuffer, 0, audioBuffer.length);
				if (count > 0) {
					DatagramPacket packet = new DatagramPacket(audioBuffer, audioBuffer.length, inetAddres, port);
					socket.send(packet);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private DatagramSocket getSocket() throws SocketException {
		if(type) {
			port=8001;
		}else {
			port=8002;
		}
		return new DatagramSocket(8000);
	}

	private void setupAudio() {
		AudioFormat audioFormat = getAudioFormat();
		try {
		if(type) {
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
				targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
				targetDataLine.open(audioFormat);
				targetDataLine.start();
				intputStrem=new AudioInputStream(targetDataLine);
			
		}else {
			File file=new File(route);
			intputStrem=AudioSystem.getAudioInputStream(file);
						
		}
		} catch (Exception e) {

		}

	}


}
