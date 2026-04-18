package Clases;

public class Pokemon {

	public String nombre;
	public String estado;
	public String tipo;
	
	public Pokemon(String nombre, String estado, String tipo) {
		
		this.nombre = nombre;
		this.estado = estado;
		this.tipo = tipo;
	}
	
	//Get y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
	
	
}
