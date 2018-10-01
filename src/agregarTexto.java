import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
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

public class agregarTexto {

	private String nombre;
	private String clase;
	private String lvl;
	private String data = "";
	private ResultSet rset;

	public static void main(String[] args) throws IOException, SQLException {
		agregarTexto agregarTexto = new agregarTexto();
		Scanner sc = new Scanner(System.in);
		int opciones = 0;
		while (opciones == 0) {
			System.out.println("1 para leer");
			System.out.println("2 para agregar");
			System.out.println("3 para leer bbdd");
			System.out.println("4 para agregar a la bbdd");
			System.out.println("5 para sobreescribir local");
			System.out.println("6 para sobreescribir bbdd");
			System.out.println("7 para borrar la tabla");
			System.out.println("8 para salir");
			int num = 0;

			try {
				num = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException ex) {
				System.out.println("No metas letras wey");

			}

			if (num == 1) {
				agregarTexto.TestConexion();
				agregarTexto.muestraContenido();

			} else if (num == 2) {
				agregarTexto.TestConexion();
				System.out.println(" ");
				System.out.print("id=");
				int id = Integer.parseInt(sc.nextLine());
				System.out.print("nombre=");
				String nombre = sc.nextLine();

				System.out.print("clase=");
				String clase = sc.nextLine();
				System.out.print("lvl=");
				String lvl = sc.nextLine();
				agregarTexto.escribirLocal(id, nombre, clase, lvl);
			} else if (num == 3) {
				agregarTexto.TestConexion();
				agregarTexto.Consulta();
			} else if (num == 4) {
				agregarTexto.TestConexion();
				agregarTexto.insertaDatos();
			} else if (num == 5) {
				agregarTexto.TestConexion();
				agregarTexto.sobreescribirLocal();

			} else if (num == 6) {
				agregarTexto.TestConexion();
				agregarTexto.borrarTabla();
				agregarTexto.sobreescribirBbdd(agregarTexto.ruta);

			} else if (num == 7) {
				agregarTexto.TestConexion();
				agregarTexto.borrarTabla();

			} else if (num == 8) {
				opciones++;
			}
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

	public void escribirLocal(int id, String nombre, String clase, String lvl) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true));
		writer.append("\n" + id + "\n");
		writer.append(nombre + "\n");
		writer.append(clase + "\n");
		writer.append(lvl);

		writer.close();
	}

	public void muestraContenido() throws IOException {
		ArrayList<Heroe> heroes = leerFichero();
		for (Heroe h : heroes) {

			System.out.println(h.getId());
			System.out.println(h.getNombre());
			System.out.println(h.getClase());
			System.out.println(h.getLvl());
		}

	}

	private String url = "";
	private String login = "";
	private String pwd = "";
	private String ruta = "";
	private Connection conexion;

	// Constructor que crea la conexión
	public void TestConexion() {
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

	public ArrayList<Heroe> leerFichero() throws IOException {
		ArrayList<Heroe> heroes = new ArrayList<Heroe>();

		int contador = 0;
		Heroe h = new Heroe();
		String cadena;

		FileReader f = new FileReader(ruta);
		BufferedReader b = new BufferedReader(f);

		while ((cadena = b.readLine()) != null) {
			Scanner sc = new Scanner(cadena);
			if (sc.hasNext()) {
				if (contador == 0) {
					h = new Heroe();
					h.setId(sc.nextInt());
					contador++;
				} else if (contador == 1) {
					h.setNombre(sc.next());
					contador++;
				} else if (contador == 2) {
					h.setClase(sc.next());
					contador++;
				} else if (contador == 3) {
					h.setLvl(sc.next());
					contador = 0;
					heroes.add(h);
				}
			}
		}
		b.close();
		return heroes;
	}

}
