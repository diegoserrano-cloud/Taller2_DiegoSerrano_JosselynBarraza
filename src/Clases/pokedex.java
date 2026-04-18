public class Pokedex {

    private String nombre;

    private String habitat;
    private double porcentaje;
    private int vida, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad;
    private String tipo;

    public Pokedex(String nombre, String habitat, double porcentaje,
                   int vida, int ataque, int defensa,
                   int ataqueEspecial, int defensaEspecial,
                   int velocidad, String tipo) {

        this.nombre = nombre;
        this.habitat = habitat;
        this.porcentaje = porcentaje;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.velocidad = velocidad;
        this.tipo = tipo;
    }
    public double getTotalStats() {
        return vida + ataque + defensa + ataqueEspecial + defensaEspecial + velocidad; //suma de sus estadisticas
    }
	
	public String getNombre() {
		return nombre;
	}
	public double getPorcentajeAparición() {
		return getPorcentajeAparición();
	}
	public int getVida() {
		return vida;
	}
	public int getAtaque() {
		return ataque;
	}
	public int getDefensa() {
		return defensa;
	}
	public int getAtaqueEspecial() {
		return ataqueEspecial;
	}
	public int getDefensaEspecial() {
		return defensaEspecial;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public String getTipo() {
		return tipo;
	}

}
