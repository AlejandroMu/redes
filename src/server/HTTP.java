package server;

import java.util.ArrayList;

public class HTTP {
	/**
	 * elabora la script de respuesta
	 */
	private String response;
	ArrayList<String> datos;
	String cliente;

	public HTTP(ArrayList<String> da, String cl) {
		datos = da;
		cliente = cl;
		init();
	}

	private void init() {
		String style = "<style> " + " body{\r\n"
				+ "                background: url('https://images.unsplash.com/photo-1512934772407-b292436089ee?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=5ebc492295c74d134262ada5c85e9234&auto=format&fit=crop&w=648&q=80');\r\n"
				+ "                                background-repeat: no-repeat;"
				+ "                background-size: 100%;" + "            }" + "            #container{ "
				+ "                padding-top: 10%;" + "                 height:100%;" + "                width: 100%;"
				+ "padding-left: 8%;" + "            } " + "            th td{" + "                margin: 200px;"
				+ "            }" + "            table{ " + "                 " + "                text-align: center; "
				+ "                align-items: center " + "                " + "            } " + "button{"
				+ "                font-size: 150%;" + "                border-radius: 20px;"
				+ "                background-color:blue;" + "            }" + "            button:hover{"
				+ "                background-color: red;" + "            }" +

				"        </style>";
		response = "<html>" + "<head> " + "<title> Apuestas </title>" + style + "</head>" + "<body>"
				+ "<h1> Datos del cliente <b> " + cliente + "</b> </h1>" + "<p>Datos</p>"
				+ "<table id=\"table\"  cellpadding=\"10\" border=\"2\">" + "<tr> " + "<th> Fecha </th>"
				+ "<th> Monto Apostado </th> " + "<th> Caballo </th>" + "<th> Resultado </th>" + "</tr>";

		for (int i = 0; i < datos.size(); i++) {
			String line[] = datos.get(i).split("-");
			response += "<tr> ";
			for (int j = 0; j < line.length; j++) {
				response += "<td> " + line[j] + " </td> ";
			}
			response += "</tr>";
		}
		response += "<p><a href=http://localhost>" + "</table>" + "</body>" + "</html>";
		String script = "";

	}

	public String getFile() {
		return response;
	}
}
