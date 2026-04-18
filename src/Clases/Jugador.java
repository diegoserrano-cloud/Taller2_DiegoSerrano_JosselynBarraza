public class Jugador {
import java.util.ArrayList;

public class Jugador {
	private String nombre; 
    private ArrayList<Pokemon> pokemones; //el jugador tendra una lista de pokemones

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.pokemones = new ArrayList<>();
    }

    public void mostrarEquipo() { //este es el metodo que muestra el equipo
    	
        for (int i = 0; i < pokemones.size(); i++) {
            System.out.println((i+1) + ") " + pokemones.get(i));
        }
    }
}

