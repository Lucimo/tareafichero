package controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import modelo.Modelo;
import vista.Vista;

public class ControladorImpl implements Controlador {
	
	
	private String ruta;

	@Override
	public void setModelo(Modelo Modelo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVista(Vista Vista) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void setRuta(String ruta) {
	this.ruta = ruta;
		
	}
	

}
