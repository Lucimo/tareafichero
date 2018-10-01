package controlador;

import java.io.IOException;
import java.util.ArrayList;

import modelo.Modelo;
import vista.Vista;

public interface Controlador {
	public void setModelo(Modelo Modelo);
	public void setVista(Vista Vista);
	public void escribirLocal(int id, String nombre, String clase, String lvl) throws IOException;
	public void muestraContenido() throws IOException;
	public ArrayList<Heroe> leerFichero() throws IOException;
	public void setRuta(String ruta);
}
