package Clases;

public class Pokemon {

    private Pokedex pokedex;
    private String estado;
	public Pokemon(Pokedex pokedex) {
		
		this.pokedex = pokedex;
		this.estado = "Vivo";
	}
	public Pokedex getPokedex() {
		return pokedex;
	}
	public String getEstado() {
		return estado;
	} 
	public String getNombre() {
		return pokedex.getNombre();
	}

    public String getTipo() {
        return pokedex.getTipo();
    }

    public double getStatsTotales() {
        return pokedex.getTotalStats();
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return pokedex.getNombre() + " | " + getTipo() + " | Stats totales: " + (int) getStatsTotales();
    }

    
    
    
}