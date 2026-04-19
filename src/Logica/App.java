/*
 * Nombres: Diego Serrano Fuentes, Josselyn Barraza Yáñez
 * Rut(s): 22.105.561-6 | 22.246.539-7
 * Carrera: ICCI
 * Taller: Taller N° 1 POO
 */

package Logica;

import java.io.*;
import java.util.*;

import Clases.*;

public class App {
	public static Jugador jugador;

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);

		String opcion = "";

		while (true) {
			menuPrincipal();
			opcion = sc.nextLine();
			if (opcion.equals("1")) {
				// Funcion continuar partida
				menuContinuarPartida(sc);

			} else if (opcion.equals("2")) {
				// Nueva partida
				menuNuevaPartida(sc);
				System.out.println();

			} else if (opcion.equals("3")) {
				break;
			} else {
				System.out.println("\nOpción inválida intente de nuevo\n");
			}
		}

		sc.close();
	}

	// Función N° 1
	public static void menuPrincipal() {
		System.out.print("1) Continuar." + "\n2) Nueva Partida." + "\n3) Salir." + "\nIngrese Opcion: ");

	}

	// Función N°2
	public static void menuContinuarPartida(Scanner sc) {
		/*
		 * Está función se encargara de cargar nuestro archivo de nombres, en caso de
		 * que no hayan jugadores cargados, se debera mostrar por pantalla que no hay
		 * jugadores cargados, y volveremos al menú inicial.
		 */

		try {
			BufferedReader br = new BufferedReader(new FileReader("Registros.txt"));
			String linea;

			linea = br.readLine();

			if (linea == null) {
				System.out.println("No hay partidas guardadas");
				return;
			}

			String[] partes = linea.split(";");

			String nombreCuenta = partes[0];
			String medallas = partes[1]; // Todavía no usamos este dato

			// inicializamos al jugador
			jugador = new Jugador(nombreCuenta);

			while ((linea = br.readLine()) != null) {
				String[] partes2 = linea.split(";");

				String nombrePokemon = partes2[0];
				String estado = partes2[1];

				// Simplemente para verificar si lo lee bien, después se borrara
				System.out.println("Pokemon: " + nombrePokemon + " | Estado: " + estado);
			}

			br.close();

			System.out.println("Bienvenido de vuelta " + nombreCuenta);
			menuJuego(sc);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No se ha encontrado el archivo");

		} catch (IOException e) {
			System.out.println("Error ");
			e.printStackTrace();
		}

	}

	// Función n° 3
	private static void menuNuevaPartida(Scanner sc) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("Registros.txt", true)); // para registrar la partida

		System.out.print("Ingrese su apodo de jugador: ");
		String apodo = sc.nextLine();

		jugador = new Jugador(apodo);

		System.out.println("Bienvenido " + apodo + "!!");
		
		//Todo lo relacionado al juego va a quedar en una función
		menuJuego(sc);

	}
	
	//Función N° 4
	public static void menuJuego(Scanner sc) {
		int op;

		do {
			System.out.print("¡" + jugador.getNombre() + "!, que deseas hacer?" + "\n1) Revisar equipo.\r\n"
					+ "2) Salir a capturar.\r\n" + "3) Acceso al PC (cambiar Pokémon del equipo).\r\n"
					+ "4) Retar un gimnasio.\r\n" + "5) Desafío al Alto Mando.\r\n" + "6) Curar Pokémon.\r\n"
					+ "7) Guardar.\r\n" + "8) Guardar y Salir.");
			System.out.print("\nIngrese una opcion: ");

			try {
				op = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Ingrese una opción valida (un número).");
				op = -1;
				continue;
			}
			
			switch (op) {
				case 1:
					System.out.println("Equipo actual: ");
					jugador.mostrarEquipo();
					break;
				case 2:
					//si se captura un pokemon usaremos el setNombre
				case 7:
					guardarPartida();
				case 8:
					System.out.println("Hasta la proxima " + jugador.getNombre() + "...");
					guardarPartida();
					break;
			}
			
		} while (op != 8);
	}
	
	//Función N° 5
	public static void guardarPartida() {
		try {
			//Se usa el false porque queremos que el archivo se reinicie cada vez que alguién guarde
			BufferedWriter bw = new BufferedWriter(new FileWriter("Registros.txt", false));
			//Mantenemos el formato del enunciado -> [nombre;medallas]
			bw.write(jugador.getNombre() + ";" + jugador.getMedallas());
			bw.newLine();
			
			//Acá falta guardar los pokemones, debería ser un arrayList y recorrerlo en un ciclo for gracias 
			//A la función ".size()" para tener el tamaño
			
			bw.close();
			System.out.println("Partida guardada con exito!");
			
 		} catch (Exception e) {
			// TODO: handle exception
 			System.out.println("Error al guardar la partida");
 			e.printStackTrace();

		}
	}
	
}
