package Clases;

import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private String medallas;
    private ArrayList<Pokemon> pokemones;

    public Jugador(String nombre) {
        this.nombre = nombre;
        //Dado a que el jugador nuevo no posee medallas, se le asignara el valor "SM" (Sin Medallas)
        this.medallas = "SM";
        this.pokemones = new ArrayList<>();
    }

    public void mostrarEquipo() {
        for (int i = 0; i < pokemones.size(); i++) {
            System.out.println((i + 1) + ") " + pokemones.get(i));
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
}