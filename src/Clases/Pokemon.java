package Clases;

public class Pokemon {

    private pokedex pokedex;
    private String estado;

    public Pokemon(pokedex pokedex) {
        this.pokedex = pokedex;
        this.estado = "Vivo";
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return getNombre() + " | " + getTipo() + " | Stats totales: " + (int) getStatsTotales();
    }
}