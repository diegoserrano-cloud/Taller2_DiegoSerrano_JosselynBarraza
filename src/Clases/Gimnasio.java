package Clases;

import java.util.ArrayList;

public class Gimnasio {
	private int nro;
	private String nombre;
	private String estado;
	private ArrayList<Pokedex> pk;
	public Gimnasio(int nro, String nombre, String estado) {
		this.nro = nro;
		this.nombre = nombre;
		this.estado = estado;
		this.pk = new ArrayList<>();
	}
	
	public int getNro() {
		return nro;
	}
	public String getNombre() {
		return nombre;
	}
	public String getEstado() {
		return estado;
	}
	public ArrayList<Pokedex> getPk() {
		return pk;
	}
	public void agregarP(Pokedex p) {
        pk.add(p);
    }

	@Override
	public String toString() {
		return "Gimnasio [nro=" + nro + ", nombre=" + nombre + ", estado=" + estado + "]";
	}

	public void setEstado(String estado) {
		this.estado = estado;
		
	}
	
}
