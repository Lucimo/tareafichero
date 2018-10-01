package vista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import controlador.Controlador;
import modelo.Modelo;

public class VistaImpl implements Vista {
	
	private Controlador Controlador;
	private Modelo Modelo;
	private String ruta;
	
	
	public void menu() throws IOException, SQLException {
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
				//Modelo.TestConexion();
				Controlador.muestraContenido();

			} else if (num == 2) {
				//Modelo.TestConexion();
				System.out.println(" ");
				System.out.print("id=");
				int id = Integer.parseInt(sc.nextLine());
				System.out.print("nombre=");
				String nombre = sc.nextLine();

				System.out.print("clase=");
				String clase = sc.nextLine();
				System.out.print("lvl=");
				String lvl = sc.nextLine();
				Controlador.escribirLocal(id, nombre, clase, lvl);
			} else if (num == 3) {
				//Modelo.TestConexion();
				Modelo.Consulta();
			} else if (num == 4) {
				//Modelo.TestConexion();
				Modelo.insertaDatos();
			} else if (num == 5) {
				Modelo.TestConexion();
				Modelo.sobreescribirLocal();

			} else if (num == 6) {
			//Modelo.TestConexion();
				Modelo.borrarTabla();
				Modelo.sobreescribirBbdd(ruta);

			} else if (num == 7) {
				//Modelo.TestConexion();
				Modelo.borrarTabla();

			} else if (num == 8) {
				opciones++;
			}
		}
	}

	@Override
	public void setControlador(Controlador Controlador) {
		this.Controlador = Controlador;
		
	}

	@Override
	public void setModelo(Modelo Modelo) {
		this.Modelo = Modelo;
		
	}

	@Override
	public void setRuta(String ruta) {
		this.ruta = ruta;
		Controlador.setRuta(ruta);
		
	}

}
