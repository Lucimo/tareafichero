import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LeerFichero {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		 muestraContenido("src/fichero");
		System.out.println("Escribe algo");
	}
	public static void muestraContenido(String fichero) throws FileNotFoundException, IOException {
	      String cadena;
	      FileReader f = new FileReader(fichero);
	      BufferedReader b = new BufferedReader(f);
	      while((cadena = b.readLine())!=null) {
	          System.out.println(cadena);
	      }
	      b.close();
	}
	
}
