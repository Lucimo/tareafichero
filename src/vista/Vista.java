package vista;

import java.io.IOException;
import java.sql.SQLException;

import controlador.Controlador;
import modelo.Modelo;

public interface Vista {
	public void setControlador(Controlador Controlador);
	public void setModelo(Modelo Modelo);
	public void setRuta(String ruta);
	public void menu() throws IOException, SQLException;
}
