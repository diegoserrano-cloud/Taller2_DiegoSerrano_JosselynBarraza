package Clases;

import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private String medallas;
    private ArrayList<Pokemon> pokemones;

    public Jugador(String nombre) {
        this.nombre = nombre;
        //Dado a que el jugador nuevo no posee medallas, se le asignara el valor "SM" (Sin Medallas)
        this.medallas = "none";
        this.pokemones = new ArrayList<>();
    }

    public void mostrarEquipo() {
    	int cont = 1;
        for (Pokemon p : pokemones) {
            System.out.println(cont + ") " + p.getNombre() +"|"+ p.getTipo()+ "|Stats totales: " +p.getStatsTotales());
            cont++;
        }
    }
    
    public String getMedallas() {
    	return medallas;
    }
    public void agregarPokemon(Pokemon p) {
        pokemones.add(p);
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Pokemon> getPokemones() {
        return pokemones;
    }

	public Boolean buscarE(String nm) {
		for(Pokemon pk: pokemones) {
			if(pk.getNombre().equalsIgnoreCase(nm)) {
				return true;
			}
		}
		return false;
	}

	public void setMedallas(String medallas) {
		this.medallas = medallas;
	}
}