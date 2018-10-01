package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import vista.Vista;

public class ModeloImpl implements Modelo {
	private Vista Vista;
	
	private String data = "";
	private ResultSet rset;
	
	
	private String nombre;
	private String clase;
	private String lvl;
	private String url = "";
	private String login = "";
	private String pwd = "";
	private String ruta = "";
	private Connection conexion;

	// Constructor que crea la conexión
	public void TestConexion() throws IOException, SQLException {
		Properties propiedades = new Properties();
		InputStream entrada = null;
		try {
			File miFichero = new File("src/inicio");
			if (miFichero.exists()) {
				entrada = new FileInputStream(miFichero);

				propiedades.load(entrada);

				url = (propiedades.getProperty("url"));
				login = (propiedades.getProperty("login"));
				pwd = (propiedades.getProperty("pass"));
				ruta = (propiedades.getProperty("ruta"));
				File fi = new File(ruta);
				if (!fi.exists()) {
					System.out.println("Fichero no encontrado ");
					return;
				}
			} else
				System.err.println("Fichero no encontrado");
		} catch (Exception e) {
			// TODO: handle exception

		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, login, pwd);
			// Quitamos esta instrucción: conexion.close();
			System.out.println(" - Conexión con MySQL establecida -");
		} catch (Exception e) {
			// System.out.println (" – Error de Conexión con MySQL -");
			e.printStackTrace();
		}
		Vista.menu();
	}
	public void Consulta() {
		try {
			Statement stmt = conexion.createStatement();
			rset = stmt.executeQuery("SELECT * FROM fichero");

			while (rset.next()) {

				System.out.println(rset.getInt(1));
				System.out.println(rset.getString(2));
				System.out.println(rset.getString(3));
				System.out.println(rset.getString(4) + "\n");

			}
			rset.close();
			stmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}
	public int borrarTabla() throws SQLException {
		PreparedStatement pstm;
		int r = 0;
		try {
			pstm = conexion.prepareStatement("Delete from fichero");

			r = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	public void sobreescribirBbdd(String ruta) throws SQLException, IOException {
		int contador = 0;

		PreparedStatement pstm = null;
		String cadena;
		FileReader f = new FileReader(ruta);
		BufferedReader b = new BufferedReader(f);

		while ((cadena = b.readLine()) != null) {

			Scanner sc = new Scanner(cadena);
			if (sc.hasNext()) {
				if (contador == 0) {
					pstm = conexion.prepareStatement(
							"INSERT INTO `fichero`( `id`, `nombre`, `clase`, `lvl`) VALUES (?,?,?,?)");
					pstm.setInt(1, Integer.parseInt(cadena));
					contador++;
				} else if (contador == 1) {
					pstm.setString(2, cadena);
					contador++;
				} else if (contador == 2) {
					pstm.setString(3, cadena);
					contador++;
				} else if (contador == 3) {
					pstm.setString(4, cadena);
					contador = 0;
					pstm.executeUpdate();
				}
			}
		}
		b.close();

	}
	public void sobreescribirLocal() throws SQLException, IOException {
		ArrayList<String[]> result = new ArrayList<String[]>();
		Statement stmt = conexion.createStatement();
		rset = stmt.executeQuery("SELECT * FROM fichero");
		int columnCount = rset.getMetaData().getColumnCount();
		while (rset.next()) {
			String[] row = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				row[i] = rset.getString(i + 1);
			}
			result.add(row);
		}

		for (String[] ar : result) {
			for (String dato : ar) {
				data += dato + "\n";
			}
			data += "";

		}

	}
	public int insertaDatos() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nombre: ");
		nombre = sc.nextLine();
		System.out.println("Clase: ");
		clase = sc.nextLine();
		System.out.println("lvl: ");
		lvl = sc.nextLine();
		PreparedStatement pstm;
		int r = 0;
		try {
			pstm = conexion.prepareStatement("INSERT INTO `fichero`( `nombre`, `clase`, `lvl`) VALUES (?,?,?)");

			pstm.setString(1, nombre);
			pstm.setString(2, clase);
			pstm.setString(3, lvl);
			r = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	@Override
	public void setVista(Vista Vista) {
		this.Vista = Vista;
		
	}
	
	

}
