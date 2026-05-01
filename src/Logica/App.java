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


import java.util.Random;

public class App {
	public static Jugador jugador;
	public static ArrayList<Pokedex> pdxs = new ArrayList<>();
	public static ArrayList<String> zonas = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		App a = new App();
		a.ejecutar();

		
	}

	private void ejecutar() throws IOException {
		leerPokedex();// lee el archivo que contiene a los pokedex
		leerHabitat();//lee las habitat
		
		Scanner sc = new Scanner(System.in);

		String opcion = "";

		while (true) {
			menuPrincipal();
			opcion = sc.nextLine();
			System.out.println();
			
			if (opcion.equals("1")) {
				// Funcion continuar partida
				menuContinuarPartida(sc);
				System.out.println();

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
		System.out.print("1) Continuar." + 
						"\n2) Nueva Partida." +
						"\n3) Salir." +
						"\nIngrese Opcion: ");
		
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
			jugador.setMedallas(medallas);

			while ((linea = br.readLine()) != null) {
				String[] partes2 = linea.split(";");

				String nombrePokemon = partes2[0];
				String estado = partes2[1];
				
				//####
				Pokedex pkx = buscarPoke(nombrePokemon);
				jugador.agregarPokemon(new Pokemon(pkx));
				// Simplemente para verificar si lo lee bien, después se borrara
				System.out.println("Pokemon: " + nombrePokemon + " | Estado: " + estado);
				
			}System.out.println();

			br.close();

			System.out.println("Bienvenido de vuelta " + nombreCuenta);
			System.out.println();
			menuJuego(sc);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No se ha encontrado el archivo");

		} catch (IOException e) {
			System.out.println("Error ");
			e.printStackTrace();
		}

	}
	
	//funcion 3: busca al pokedex por su nombre
	private static Pokedex buscarPoke(String nombrePokemon) {
		for(Pokedex pk: pdxs) {
			if(pk.getNombre().equalsIgnoreCase(nombrePokemon)) {
				return pk;
			}
		}
		return null;
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
	public static void menuJuego(Scanner sc) throws IOException {
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
					capturar();
					System.out.println();
					//si se captura un pokemon usaremos el setNombre
					break;
				case 3:
					 AccesoPC();
					 System.out.println();
					break;
				case 7:
					guardarPartida();
					break;
				case 8:
					System.out.println("Hasta la proxima " + jugador.getNombre() + "...");
					guardarPartida();
					break;
			}
			
		} while (op != 8);
	}
	

	//funcion 5:  capturar pokemones
	private static void capturar() {
		Random r = new Random();
		System.out.println();
		System.out.println("Donde deseas ir a explorar? ");
		System.out.println();
		
		int op;
		
		do {
			System.out.println("Zonas disponibles: ");
			int i = 1;
			for(String z: zonas) {//mostramos las zonas que existen
					System.out.println(i+ ") " + z);
					i++;
				}	
			
			System.out.println("7) Volver al menu.");
			op = inputInt("Ingrese una opción: ");
				
			System.out.println();
			
			if(op != 7) {
				String hbt = zonas.get(op-1);//obtenemos la zona en esta posición
				Boolean pok = false;
				int num = -1;
				while(pok != true) { //mientras el pokedex no sea de la misma zona seguira buscando
					num = r.nextInt(pdxs.size());
					pok = buscar(hbt, num);//busca si el habitat es la misma	
				}
				if(pok == true) {//Para cuando ya encuentra un pokedex del mismo habitat
					System.out.println("Oh!! Ha aparecido un increible "+ pdxs.get(num).getNombre()+"!!");
					System.out.println();
				} menuCaptura();
				
				int opCap;
				do{
					opCap= inputInt("Ingrese una opción: ");
					System.out.println();
					if(opCap == 1) {
						Boolean pk = jugador.buscarE(pdxs.get(num).getNombre());//busca si el pokemon ya existe en la lista
						if(pk == false) { //si no existe se agrega a la lista del pokemon
							jugador.agregarPokemon(new Pokemon(pdxs.get(num)));
							System.out.println("Se capturo "+ pdxs.get(num).getNombre()+ " con existo!!");}
						else {
							System.out.println("Ya tienes este pokemon!!");
							System.out.println();
						}break;
					}else if(opCap!= 2){
						System.out.println("Opcion invalida.");
					}
				}while(opCap!= 2);
			}

		}while(op != 7);
		
		return;
	}
	private static void leerHabitat() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Habitats.txt"));
		String linea;
		while ((linea = br.readLine()) != null) {
			zonas.add(linea);//agregamos la zona a nuestra lista
		}br.close(); 
	}

	//funcion 6: busca si el pokemon es de ese habitat
	private static Boolean buscar(String hbt, int num) {
		Pokedex pox = pdxs.get(num);
		if(pox.getHabitat().equalsIgnoreCase(hbt)) {
			return true;
		}
		return false;
	}

	//Función 7: Guarda los cambios en "Registros.txt"
	public static void guardarPartida() {
		try {
			//Se usa el false porque queremos que el archivo se reinicie cada vez que alguién guarde
			BufferedWriter bw = new BufferedWriter(new FileWriter("Registros.txt", false));
			//Mantenemos el formato del enunciado -> [nombre;medallas]
			bw.write(jugador.getNombre() + ";" + jugador.getMedallas());
			bw.newLine();
			
			for(Pokemon p: jugador.getPokemones()) { //por cada pokemon del jugador se guardara en el archivo
				bw.write(p.getNombre()+";"+ p.getEstado());
				bw.newLine();
			}
			
			bw.close();
			System.out.println("Partida guardada con exito!");
			
 		} catch (Exception e) {
			// TODO: handle exception
 			System.out.println("Error al guardar la partida");
 			e.printStackTrace();

		}
	//funcion 8: lee el archivo "Pokedex.txt"
	}private void leerPokedex() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Pokedex.txt"));
		String linea;
		
		while ((linea = br.readLine()) != null) {
			//pokemon;habitat;porcentajeAparicion;
			//vida;ataque;defensa;ataqueEspecial;
			//defensaEspecial;velocidad;Tipo
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String habitat = partes[1];
			double porcentaje = Double.parseDouble(partes[2]);
            int vida= Integer.parseInt(partes[3]);
            int ataque= Integer.parseInt(partes[4]);
            int defensa= Integer.parseInt(partes[5]);
            int ataqueEspecial = Integer.parseInt(partes[6]);
            int defensaEspecial= Integer.parseInt(partes[7]);
            int velocidad = Integer.parseInt(partes[8]);
            String tipo = partes[9];
            pdxs.add(new Pokedex(nombre,habitat,porcentaje,vida,ataque,defensa,
            					ataqueEspecial,defensaEspecial,velocidad,tipo));  
		}

		br.close();
	//funcion 9: muestra el menu captura
	}private static void menuCaptura() {
		System.out.println("Que deseas hacer?\n"
				+ "\n"
				+ "1) Capturar\n"
				+ "2) Huir");
		
	}
	//funcion 10: acceso al PC
		private static void AccesoPC() {
			int j = 1;
			System.out.println();
			System.out.println("Pokemones capturados");
			for(Pokemon p: jugador.getPokemones()) {//muestra los pokemones del jugador
				System.out.println(j+") "+ p.getNombre());
				j++;
			}System.out.println();
			int op;
			do {
				System.out.println("1) Cambiar Pokémon.\n" + 
								   "2) Salir");
				op = inputInt("Ingrese una opción:");
				if(op == 1) {
					int size = jugador.getPokemones().size();
					int posI;
					int posT;
					System.out.println();
					System.out.println("Las posiciones deben ser numeros señalados anteriormente!!");
					System.out.println();
					//evita posiciones iguales o fuera del rango
					do {
					    posI = inputInt("Ingrese la posicion del primer pokemon: ");
					} while (posI-1 < 0 || posI-1 >= size);
					do {
						posT = inputInt("Ingrese la posicion del segundo pokemon: ");
						if(posI == posT) {
							System.out.println("No se puede intercambiar el mismo pokemon");
							System.out.println();
						}
					}while(posT-1 < 0 || posT-1 >= size || posT == posI);
					
					//intercambia pokemones en la lista
					Pokemon aux = jugador.getPokemones().get(posI-1);
					jugador.getPokemones().set(posI-1, jugador.getPokemones().get(posT-1));
					jugador.getPokemones().set(posT-1, aux);
					System.out.println("Pokemones intercambiados con exito!!");
					break;
					
				}else if(op != 2) System.out.println("Opcion invalida");
				
			}while(op!=2);
		}
	//funcion : Facilida preguntar las opciones o algun numero en particular
	public static int inputInt(String mg) {
	    Scanner s = new Scanner(System.in);
	    System.out.print(mg);
	    
	    while (!s.hasNextInt()) {
	        System.out.println("Ingrese un número válido: ");
	        s.next(); // limpia l
	    }
	    
	    return s.nextInt();
	}

}
