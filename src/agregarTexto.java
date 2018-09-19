import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.ParseConversionEvent;

public class agregarTexto {

	private static int id;
	private static String nombre;
	private static String clase;
	private static String lvl;
	private static String data = "";
	private static ResultSet rset;

	public static void main(String[] args) throws IOException, SQLException {

		Scanner sc = new Scanner(System.in);
		System.out.println("1 para leer");
		System.out.println("2 para agregar");
		System.out.println("3 para leer bbdd");
		System.out.println("4 para agregar a la bbdd");
		System.out.println("5 para sobreescribir local");
		System.out.println("6 para obreescribir bbdd");
		int num = Integer.parseInt(sc.nextLine());
		if (num == 1) {
			muestraContenido("src/fichero.txt");
		} else if (num == 2) {
			System.out.println(" ");
			System.out.print("id=");
			int id = Integer.parseInt(sc.nextLine());
			System.out.print("nombre=");
			String nombre = sc.nextLine();

			System.out.print("clase=");
			String clase = sc.nextLine();
			System.out.print("lvl=");
			String lvl = sc.nextLine();
			escribirlocal(id, nombre, clase, lvl);
		} else if (num == 3) {
			TestConexion();
			Consulta();
		} else if (num == 4) {
			TestConexion();
			insertaDatos(/* id, */ nombre, clase, lvl);
		} else if (num == 5) {
			TestConexion();
			sobreescribirLocal();

		} else if (num == 6) {

		}

	}

	public static int sobreescribirbbdd() throws SQLException {
		PreparedStatement pstm;
		int r = 0;
		while (rset.next()) {
			try {
				pstm = conexion.prepareStatement("INSERT INTO `fichero`( `nombre`, `clase`, `lvl`) VALUES (?,?,?)");
				for (int i = 0; i < data.length(); i++) {

				}
				pstm.setString(1, nombre);
				pstm.setString(2, clase);
				pstm.setString(3, lvl);
				r = pstm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return r;
	}

	public static void escribirlocal(int id, String nombre, String clase, String lvl) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter("src/fichero.txt", true));
		writer.append("\n\n" + "id=" + id + "\n");
		writer.append("nombre=" + nombre + "\n");
		writer.append("clase=" + clase + "\n");
		writer.append("lvl=" + lvl);

		writer.close();
	}

	public static void muestraContenido(String fichero) throws FileNotFoundException, IOException {
		String cadena;
		FileReader f = new FileReader(fichero);
		BufferedReader b = new BufferedReader(f);
		while ((cadena = b.readLine()) != null) {
			System.out.println(cadena);
		}
		b.close();
	}

	private String bd = "bbdd_investigacion";
	private static String login = "root";
	private static String pwd = "";
	private String url = "jdbc:mysql://localhost/" + bd;
	private static Connection conexion;

	// Constructor que crea la conexión
	public static void TestConexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/tareaficheros?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			conexion = DriverManager.getConnection(url, login, pwd);
			// Quitamos esta instrucción: conexion.close();
			System.out.println(" - Conexión con MySQL establecida -");
		} catch (Exception e) {
			// System.out.println (" – Error de Conexión con MySQL -");
			e.printStackTrace();
		}
	}

	public static void Consulta() {
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

	public static int insertaDatos(/* String id, */ String nombre, String clase, String lvl) {
		Scanner sca = new Scanner(System.in);
		System.out.println("Nombre: ");
		nombre = sca.nextLine();
		System.out.println("Clase: ");
		clase = sca.nextLine();
		System.out.println("lvl: ");
		lvl = sca.nextLine();
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

	/*
	 * public static void sobreescribirLocal() throws IOException, SQLException {
	 * try { Statement stmt = conexion.createStatement(); rset =
	 * stmt.executeQuery("SELECT * FROM fichero");
	 * 
	 * while (rset.next()) {
	 * 
	 * BufferedWriter writer = new BufferedWriter(new
	 * FileWriter("src/fichero.txt")); writer.append("\n\n" + +rset.getInt(1) +
	 * "\n"); writer.append(rset.getString(2) + "\n");
	 * writer.append(rset.getString(3) + "\n"); writer.append(rset.getString(4));
	 * System.out.println(rset.getInt(1)); System.out.println(rset.getString(2));
	 * System.out.println(rset.getString(3)); System.out.println(rset.getString(4) +
	 * "\n");
	 * 
	 * writer.close();
	 * 
	 * } rset.close(); stmt.close(); } catch (SQLException s) { s.printStackTrace();
	 * } }
	 */
	public static void sobreescribirLocal() throws SQLException, IOException {
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
			data += "\n\n";

		}

		BufferedWriter writer = new BufferedWriter(new FileWriter("src/fichero.txt"));
		writer.append(data);

		writer.close();
	}

}
