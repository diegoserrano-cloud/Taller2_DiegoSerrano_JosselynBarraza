package Clases;

import java.util.ArrayList;

public class AltoMando {
	private int nro;
	private String nombre;
	private ArrayList<Pokedex> pk;

	public AltoMando(int nro, String nombre) {
		this.nro = nro;
		this.nombre = nombre;
		this.pk = new ArrayList<>();
	}

	public int getNro() {
		return nro;
	}

	public String getNombre() {
		return nombre;
	}

	public ArrayList<Pokedex> getPk() {
		return pk;
	}

	public void agregarP(Pokedex p) {
		pk.add(p);
	}
}
