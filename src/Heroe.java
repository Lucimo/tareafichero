
public class Heroe {
    private  int id;
	private  String nombre;
	private  String clase;
	private  String lvl;
	
	public Heroe() {

    }

 

    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getClase() {
		return clase;
	}



	public void setClase(String clase) {
		this.clase = clase;
	}



	public String getLvl() {
		return lvl;
	}



	public void setLvl(String lvl) {
		this.lvl = lvl;
	}



	public Heroe(int id, String nombre, String clase, String lvl) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.clase = clase;
        this.lvl = lvl;
    }

}