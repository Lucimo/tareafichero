import java.io.IOException;
import java.sql.SQLException;

import controlador.Controlador;
import controlador.ControladorImpl;
import modelo.Modelo;
import modelo.ModeloImpl;
import vista.Vista;
import vista.VistaImpl;

public class Start {
	public static void main(String[] args) throws IOException, SQLException {
		Controlador Controlador = new ControladorImpl();
		Modelo Modelo = new ModeloImpl();
		Vista Vista = new VistaImpl();
		
		Controlador.setModelo(Modelo);
		Modelo.setVista(Vista);
		Vista.setModelo(Modelo);
		Vista.setControlador(Controlador);
		Controlador.setVista(Vista);
		
		Modelo.TestConexion();
		
	}
}
