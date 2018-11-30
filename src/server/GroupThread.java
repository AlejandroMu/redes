package server;

import java.util.*;

public class GroupThread {
	private ArrayList<Thread> hilos;
	public GroupThread() {
		hilos=new ArrayList<>();
	}
	public void addHilo(Thread hil) {
		hilos.add(hil);
	}
	public void notificarAll() {
		for (Thread clientes : hilos) {
			if(clientes instanceof Clientes) {
				((Clientes) clientes).startNotification();

			}
		}
	}
	public void startAll() {
		for (Thread clientes : hilos) {
			clientes.start();
		}
	}
	public void stopAll() {
		for (Thread clientes : hilos) {
			if(clientes instanceof Clientes) {
				((Clientes) clientes).setEnable(false);

			}else {
				((AnimarCaballos) clientes).setEnable(false);

			}
		}
	}
}
