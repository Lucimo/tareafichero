package modelo;

import java.io.IOException;
import java.sql.SQLException;

import vista.Vista;

public interface Modelo {
	public void setVista(Vista Vista);
	public void TestConexion() throws IOException, SQLException;
	public void Consulta();
	public int borrarTabla() throws SQLException;
	public void sobreescribirBbdd(String ruta) throws SQLException, IOException;
	public void sobreescribirLocal() throws SQLException, IOException;
	public int insertaDatos();
	
}
