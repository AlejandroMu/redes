package server;

import java.util.*;

public class AnimarCaballos extends Thread {
	private Servidor server;
	private boolean enable;
	public AnimarCaballos(Servidor a) {
		server=a;
		enable=true;
	}
	public void setEnable(boolean a) {
		enable=a;
	}
	public boolean enable() {
		return enable;
	}
	@Override
	public void run() {		
		Random random=new Random();
		while(enable) {
			int[] hourses=server.getHourses();
			int[] moves=server.getMoves();
			for (int i = 0; i < hourses.length; i++) {
				int ra=Math.abs(random.nextInt()%moves.length);
				boolean f=hourses[i]<=1000;
				if(f) {
					hourses[i]+=moves[ra];
				}
				if(hourses[i]>1000&&f) {
					server.orden.add(i+1);
				}
			}
			server.setHourses(hourses);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
