package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ServidorHttp implements HttpHandler{
	public static final String folder = "C:/Users/alejandro/eclipse-workspace/examen/docs";

	 HttpServer server;
	 ArrayList<String> clientes;
		Hashtable<String, ArrayList<String>> datos;
		public ServidorHttp() {
			clientes=new ArrayList<>();
			datos=new Hashtable<>();
			cargarFiles();
			connect();
		}
	public void connect() {
		try {
			server=HttpServer.create(new InetSocketAddress(2345),0);
			server.createContext("/").setHandler(this);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void cargarFiles() {
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
				if(!lin.equals("")) {
					String spli[] = lin.split(",");
					ArrayList<String> tmp = datos.get(spli[0]);
					if (tmp == null) {
						tmp = new ArrayList<>();
					}
					tmp.add(spli[1]);
					datos.put(spli[0], tmp);
					lin = lec.readLine();
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new ServidorHttp(); 
	}
	@Override
	public void handle(HttpExchange e) throws IOException {
		String m=e.getRequestMethod();
		
		BufferedReader lec=new BufferedReader(new InputStreamReader(e.getRequestBody()));
		OutputStream out=e.getResponseBody();
		String cliente=lec.readLine();
		System.out.println(m+" "+cliente);
		e.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		if(clientes.contains(cliente)) {
			HTTP res=new HTTP(datos.get(cliente),cliente);
			String respuesta=res.getFile();
			e.sendResponseHeaders(200, respuesta.getBytes().length);

			out.write(respuesta.getBytes());
			out.close();
		}
		

	}

}
