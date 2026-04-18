package Clases;

public class Pokemon {

	public class Pokemon {

	 private Pokedex pokedex;// utilizaremos como base al pokedex
	 private String estado;

	    public Pokemon(Pokedex pokedex) {
	        this.pokedex = pokedex;
	        this.estado = "Vivo";// por defecto el pokemon esta vivo al capturar
	    }

	    public String getNombre() {
	        return pokedex.getNombre();//obtenemos el nombre desde pokedex
	    }

	    public String getTipo() {
	        return pokedex.getTipo();//lo obtenemos de pokedex
	    }

	    public double getStatsTotales() {
	        return pokedex.getTotalStats();//lo obtenemos de pokedex
	    }

	    @Override
	    public String toString() { //metodo que imprime el quipo del jugador
	        return getNombre() + " | " + getTipo() + " | Stats totales: " + getStatsTotales();
	    }
	}
