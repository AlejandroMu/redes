package cliente;

import java.io.*;
import java.net.*;

import javax.sound.sampled.*;

public class HiloStreamingAudio {

	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;
	String rout;
	int port;
	

	public HiloStreamingAudio(String gro,int port) {
		rout = gro;
		this.port=port;
		initiateAudio();
	}

	public AudioFormat getAudioFormat() {
		float sampleRate = 16000F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	private void initiateAudio() {
		try {
			InetAddress inetAddres = InetAddress.getByName(rout);
			MulticastSocket socket = new MulticastSocket(port);
			socket.joinGroup(inetAddres);
			byte audioBuffer[] = new byte[10000];
			while (true) {
				DatagramPacket packet = new DatagramPacket(audioBuffer, audioBuffer.length);
				socket.receive(packet);
				byte audioData[] = packet.getData();
				InputStream byteInputStream = new ByteArrayInputStream(audioData);
				AudioFormat audioFormat = getAudioFormat();
				audioInputStream = new AudioInputStream(byteInputStream, audioFormat,
						audioData.length / audioFormat.getFrameSize());
				DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();
				playAudio();

			}
		} catch (Exception e) {
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


}
