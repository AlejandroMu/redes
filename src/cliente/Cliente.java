package cliente;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Cliente extends JFrame implements ActionListener {
	public static final String server = "";
	public static final int port = 1234;
	public static final String folder = "C:/Users/alejandro/eclipse-workspace/examen/docs";

	/**
	 * 
	 */
	ArrayList<String> clientes;
	Hashtable<String, ArrayList<String>> datos;
	JTextField txt;
	Hipodromo hip;
	boolean enable = true;
	JButton apuesta;
	int hourse = -1;
	double apuestaT;
	String idcliente;
	JLabel winner;
	Hourse[] hourses;
	private static final long serialVersionUID = 1L;

	public Cliente() {
		setTitle("Apuestas");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 500);
		apuesta = new JButton("Apostar");
		apuesta.addActionListener(this);
		apuesta.setActionCommand("Apostar");
		txt = new JTextField();
		JPanel pne = new JPanel();
		pne.setLayout(new GridLayout(1, 2));
		pne.add(txt);
		pne.add(apuesta);
		pne.setBorder(new TitledBorder("Apuesta"));
		hip = new Hipodromo();
		winner = new JLabel("");
		setBackground(Color.WHITE);
		add(pne, BorderLayout.SOUTH);
		add(hip, BorderLayout.NORTH);
		cargarFiles();

	}

	private void cargarFiles() {
		File clentes = new File(folder + "/clientes.txt");
		try {
			clientes = new ArrayList<>();
			BufferedReader lec = new BufferedReader(new FileReader(clentes));
			String lin = lec.readLine();
			while (lin != null) {
				clientes.add(lin);
				lin = lec.readLine();
			}
			lec.close();
			clentes = new File(folder + "/datos.txt");
			datos = new Hashtable<>();
			lec = new BufferedReader(new FileReader(clentes));
			lin = lec.readLine();
			while (lin != null) {
				String spli[] = lin.split(",");
				ArrayList<String> tmp = datos.get(spli[0]);
				if (tmp == null) {
					tmp = new ArrayList<>();
				}
				tmp.add(spli[1]);
				datos.put(spli[0], tmp);
				lin = lec.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void guardar() {
		try {
			if (!clientes.contains(idcliente)) {
				File clentes = new File(folder + "/clientes.txt");
				BufferedWriter esc = new BufferedWriter(new FileWriter(clentes, true));
				esc.newLine();
				esc.write(idcliente);
				esc.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadHourses() {
		hourses = new Hourse[6];
		Point pos = new Point(10, 50);
		for (int i = 0; i < hourses.length; i++) {
			hourses[i] = new Hourse(pos);
			pos = new Point(pos.x, pos.y + 60);
		}
		changeHourses();
	}

	private void conectar() {
		apuestasTCP ap = new apuestasTCP(this, hourse, apuestaT, idcliente);
		ap.start();
	}

	public void setWiner(String c) {
		String msm = c.equals("" + (hourse + 1)) ? "Eres el ganador" : "El caballo ganador es: " + c;
		winner.setText(msm);
		JPanel aux = new JPanel();
		aux.setLayout(new GridLayout(1, 3));
		aux.setBorder(new TitledBorder("Resultado"));
		aux.add(new JLabel(""));
		aux.add(winner);
		aux.add(new JLabel(""));
		add(aux, BorderLayout.CENTER);
		repaint();
		pack();
		try {
			Calendar cal=Calendar.getInstance();
			String dia=cal.get(Calendar.DATE)+"/";
			String mes=cal.get(Calendar.MONTH)+"/";
			String an=""+cal.get(Calendar.YEAR);
			String fecha=dia+mes+an+"-";
			File clentes = new File(folder + "/datos.txt");
			BufferedWriter esc = new BufferedWriter(new FileWriter(clentes, true));
			esc.newLine();
			esc.write(idcliente + ","+fecha+apuestaT+"-"+hourse+"-"+msm);
			esc.close();
		} catch (Exception a) {

		}

	}

	public synchronized void setHourses(String a) {

		String[] hourses1 = a.split(" ");
		for (int i = 0; i < hourses1.length; i++) {
			hourses[i].move(Integer.parseInt(hourses1[i]));
		}
		changeHourses();
	}

	private void changeHourses() {
		hip.changeHourse(hourses, hourse, apuestaT);
		hip.repaint();
	}

	public static void main(String[] args) {
		new Cliente().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("Apostar") && enable) {
			String input[] = txt.getText().split(" ");
			hourse = Integer.parseInt(input[0]) - 1;
			apuestaT = Double.parseDouble(input[1]);
			idcliente = input[2];
			conectar();
			apuesta.setEnabled(false);
			loadHourses();
			enable = false;
			guardar();
			
		}
	}
}
