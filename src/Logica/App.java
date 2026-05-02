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
	public static ArrayList<Gimnasio> gim = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		App a = new App();
		a.ejecutar();

		
	}

	private void ejecutar() throws IOException {
		leerPokedex();// lee el archivo que contiene a los pokedex
		leerHabitat();//lee el archivo de las habitat
		leerGimnasios();//Lee el archivo de los gimasios
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
	// Función: muestra el menu inicial
	public static void menuPrincipal() {
		System.out.print("1) Continuar." + 
						"\n2) Nueva Partida." +
						"\n3) Salir." +
						"\nIngrese Opcion: ");
		
	}
	// Función N°1: Continuar partida
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
				Pokemon poke = new Pokemon(pkx);
				poke.setEstado(estado);
				jugador.agregarPokemon(poke);
				
				
				
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

	// Función: nueva partida
	private static void menuNuevaPartida(Scanner sc) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("Registros.txt", true)); // para registrar la partida

		System.out.print("Ingrese su apodo de jugador: ");
		String apodo = sc.nextLine();

		jugador = new Jugador(apodo);

		System.out.println("Bienvenido " + apodo + "!!");
		
		//Todo lo relacionado al juego va a quedar en una función
		menuJuego(sc);

	}
	//Función: menu Juego
	public static void menuJuego(Scanner sc) throws IOException {
		int op;
		Random r = new Random();
		do {
			System.out.print("¡" + jugador.getNombre() + "!, que deseas hacer?" + "\n1) Revisar equipo.\r\n"
					+ "2) Salir a capturar.\r\n" + "3) Acceso al PC (cambiar Pokémon del equipo).\r\n"
					+ "4) Retar un gimnasio.\r\n" + "5) Desafío al Alto Mando.\r\n" + "6) Curar Pokémon.\r\n"
					+ "7) Guardar.\r\n" + "8) Guardar y Salir.");

			op = inputInt("Ingrese una opciòn: ", sc);
			
			switch (op) {
				case 1:
					System.out.println("Equipo actual: ");
					jugador.mostrarEquipo();//metodo en la clase Jugador
					break;
				case 2:
					capturar(sc, r);
					System.out.println();
					break;
				case 3:
					 AccesoPC(sc);
					 System.out.println();
					break;
				case 4:
					retarGimnasio(sc, r);
					break;
				case 6:
					curarPokemones();
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
	

	//funcion 2:  capturar pokemones
	private static void capturar(Scanner sc, Random r) {
		
		System.out.println();
		System.out.println("Donde deseas ir a explorar? ");
		System.out.println();
		
		int op;
		int i = 1;
		do {
			System.out.println("Zonas disponibles: ");
			
			for(String z: zonas) {//mostramos las zonas que existen
					System.out.println(i+ ") " + z);
					i++;
				}	
			
			System.out.println("7) Volver al menu.");
			op = inputInt("Ingrese una opción: ", sc);
				
			System.out.println();
			
			if(op != 7) {
				String hbt = zonas.get(op-1);//obtenemos la zona en esta posición
				
				
				ArrayList<Pokedex> pokemonesZona = new ArrayList<>(); //Creamos un ArrayList que solo guarde los pokemones de ese habitat
	            for (Pokedex pk : pdxs) {
	                if (pk.getHabitat().equalsIgnoreCase(hbt)) {
	                    pokemonesZona.add(pk);
	                }
	            }
				
	            if (pokemonesZona.isEmpty()) {
	                System.out.println("No hay pokemones en esta zona.");
	                continue;
	            }
	            
	            
	            double aleatorio = r.nextDouble();
	            double acumulado = 0.0;
	            Pokedex elegido = null;

	            for (Pokedex pk : pokemonesZona) {
	                acumulado += pk.getPorcentajeAparición();
	                if (aleatorio <= acumulado) {
	                    elegido = pk;
	                    break;
	                }
	            }

	            // Por si hay algún error de redondeo y no se eligió ninguno
	            if (elegido == null) {
	                elegido = pokemonesZona.get(pokemonesZona.size() - 1);
	            }
				
	            System.out.println("Oh!! Ha aparecido un increible " + elegido.getNombre() + "!!");
	            System.out.println();
	            menuCaptura();
	      
				int opCap;
				do{
					opCap= inputInt("Ingrese una opción: ", sc);
					System.out.println();
					if(opCap == 1) {
						Boolean pk = jugador.buscarE(elegido.getNombre());//busca si el pokemon ya existe en la lista
						if(pk == false) { //si no existe se agrega a la lista del pokemon
							jugador.agregarPokemon(new Pokemon(elegido));
							System.out.println("Se capturo "+ elegido.getNombre()+ " con exito!!");}
						else {
							System.out.println("Ya tienes este pokemon!!");
							System.out.println();
						} break;
					} else if(opCap == 2){
						System.out.println("Has huido del combate!! " + elegido.getNombre() + " esta decepcionado de tí");
					} else {
						System.out.println("Opción invalida, intenta de nuevo.");
					}
				}while(opCap!= 2);
			}

		}while(op != 7);
		
		return;
	}
	//funcion: muestra el menu captura
	private static void menuCaptura() {
		System.out.println("Que deseas hacer?\n"
				+ "\n"
				+ "1) Capturar\n"
				+ "2) Huir");
	}
	//funcion 3: Acceso al PC
		private static void AccesoPC(Scanner sc) {
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
				op = inputInt("Ingrese una opción:", sc);
				if(op == 1) {
					int size = jugador.getPokemones().size();
					int posI;
					int posT;
					System.out.println();
					System.out.println("Las posiciones deben ser numeros señalados anteriormente!!");
					System.out.println();
					//evita posiciones iguales o fuera del rango
					do {
					    posI = inputInt("Ingrese la posicion del primer pokemon: ", sc);
					} while (posI-1 < 0 || posI-1 >= size);
					do {
						posT = inputInt("Ingrese la posicion del segundo pokemon: ", sc);
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
	//funcion 4: Retar Gimnasio
	private static void retarGimnasio(Scanner sc, Random r) {
		
		System.out.println();
		System.out.println("A cual Lider deseas retar? ");
		System.out.println();
		int op;
		do {
			int i = 1;
			for(Gimnasio g: gim) {
				System.out.println(i +") "+ g.getNombre() + "- Estado: " + g.getEstado());
				i++;
			}System.out.println("9 ) Volver al menu ");
			
			//La opción tiene que estar dentro del rango
			do {
				op= inputInt("Ingrese una opción: ", sc);
				if(op < 1 || op >= gim.size());
					System.out.println("Opción invalida");
			}while(op < 0 || op >= gim.size()+1);
			
			System.out.println();
			Boolean est= true;
			if(op!= 9) {
				for(int j = op-2; j>-1; j--) {
					if(gim.get(j).getEstado().equalsIgnoreCase("Sin derrotar")) {
						est = false;
						break;
					}
				}if(est == false) {
					System.out.println();
					System.out.println("Calmado Entrenador!!! No puedes retar a "+ gim.get(op-1).getNombre() +" sin haber derrotado a los lideres anteriores!!");
					System.out.println();
					break;
				}else if(est == true) {
					int limite = calcularlimite();//Busca un pokemon dentro del equipo
					boolean estado= false;
					Pokedex jugadorPoke = null;
					//Busca un pokedex que tenga estado Vivo
					do {
						int ind = r.nextInt(limite);
						Pokemon pok = jugador.getPokemones().get(ind);
						jugadorPoke = buscarPoke(pok.getNombre());
						if(pok.getEstado().equalsIgnoreCase("Vivo")) {
							estado = true;
						}
					}while(estado != true);
					
					
					int index = r.nextInt(gim.get(op-1).getPk().size());
					Pokedex rival = gim.get(op-1).getPk().get(index);

					System.out.println("Desafiando a "+ gim.get(op-1).getNombre() + "!!");
					System.out.println();
					System.out.println(gim.get(op-1).getNombre()+ "saca a "+ rival.getNombre()+"!");
					System.out.println(jugador.getNombre()+ "saca a "+ jugadorPoke.getNombre()+ "!");
					System.out.println();
					
					System.out.println("Que deseas hacer\n"+"1) Ataca\n "+ "2) Cambiar de Pokémon\n"+ "3) Rendirse");
					
					int opp;
					do {
						opp = inputInt("Ingrese una opción: ", sc);
						System.out.println();
						if(opp == 1){
							TablaTipos tabla = new TablaTipos();
							String tipoJ = jugadorPoke.getTipo();
							String tipoR = rival.getTipo();
							
							double efectivo = tabla.getEfectividad(tipoJ, tipoR);
							double totalJugador = jugadorPoke.getTotalStats();
							double totalRival = rival.getTotalStats();
							
							System.out.println(jugadorPoke.getNombre() + " -> " + totalJugador + " puntos");
							System.out.println(rival.getNombre() + " -> " + totalRival + " puntos");
							
							if(efectivo == 2.0){
							    System.out.println(jugadorPoke.getNombre() + " es efectivo contra " + rival.getNombre() + "!");
							    totalJugador = totalJugador * efectivo;
							    System.out.println();
							    System.out.println("Nuevo puntaje:");
								System.out.println(jugadorPoke.getNombre() + " -> " + totalJugador + " puntos");
								System.out.println(rival.getNombre() + " -> " + totalRival + " puntos");
							}
							else if(efectivo == 0.5){
							    System.out.println(jugadorPoke.getNombre() + " no es efectivo contra " + rival.getNombre() + "!");
							    totalJugador = totalJugador * efectivo;
							    System.out.println();
							    System.out.println("Nuevo puntaje:");
								System.out.println(jugadorPoke.getNombre() + " -> " + totalJugador + " puntos");
								System.out.println(rival.getNombre() + " -> " + totalRival + " puntos");
							    
							}
							
							Boolean victoria= false;
							if(totalJugador > totalRival){
							    System.out.println("Ha ganado " + jugadorPoke.getNombre() + "!");
							    victoria = true;
							    
							}else if(totalJugador == totalRival) {
								System.out.println("Empate");
								victoria = null;
							}
							else{
							    System.out.println("Ha ganado " + rival.getNombre() + "!");
							}//aquì falta ver si le quedan pokemones en su equipo (usa el limite)
							//guardar la medalla si gana(agregar el lider del gimnasio como en el ejemplo)
							//si le quedan pokemones que siga enfrentando y si no le queda que vuelva al menu
							//cambiarle el estado del pokemon que salio segun el resultado de la victoria
							//no se si hay empate
							break;
							
						}else if(opp==2) {
							break;
						}
					
					}while(opp!= 3);
					
				}
			break;}
		}while(op != 9);
	}

	private static int calcularlimite() {
		int lim;
		if(jugador.getPokemones().size() < 6) {
		    lim = jugador.getPokemones().size();
		} else {
		    lim = 6;
		}
		return lim;
	}

	//funcion: busca al pokedex por su nombre
	private static Pokedex buscarPoke(String nombrePokemon) {
		for(Pokedex pk: pdxs) {
			if(pk.getNombre().equalsIgnoreCase(nombrePokemon)) {
				return pk;
			}
		}
		return null;
	}
	//Función 6: Cura al equipo
	private static void curarPokemones() {
	    ArrayList<Pokemon> lista = jugador.getPokemones();

	    if (lista.isEmpty()) {
	        System.out.println("No tienes pokemones para curar.");
	        return;
	    }

	    for (Pokemon p : lista) {
	        p.setEstado("Vivo");
	    }

	    System.out.println("Tu equipo se ha recuperado!");
	}

	//Función 7 y 8: Guarda los cambios en "Registros.txt"
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
	}

		
	//funcion : Facilida preguntar las opciones o algun numero en particular
	public static int inputInt(String mg, Scanner sc) {
	   
	    System.out.print(mg);
	    
	    while (!sc.hasNextInt()) {
	        System.out.println("Ingrese un número válido: ");
	        sc.next(); // limpia l
	    }
	    
	    return sc.nextInt();
	}
	
	
	//funcion : lee el archivo "Pokedex.txt"
	private void leerPokedex() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Pokedex.txt"));
		String linea;
		
		while ((linea = br.readLine()) != null) {
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

		br.close();}
	//funcion: lectura archivo "Habitat"
	private static void leerHabitat() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Habitats.txt"));
		String linea;
		while ((linea = br.readLine()) != null) {
			zonas.add(linea);//agregamos la zona a nuestra lista
		}br.close(); 
	}
	//lee el archivo "Gimnasios
	private void leerGimnasios() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Gimnasios.txt"));
		String linea;

		while((linea = br.readLine()) != null) {
		    String[] partes = linea.split("\\s*;\\s*");
		    int nro = Integer.parseInt(partes[0]);
		    String nombre = partes[1];
		    String estado = partes[2];
		    int cant = Integer.parseInt(partes[3]);
		    Gimnasio gii = new Gimnasio(nro, nombre, estado);
		    gim.add(gii);
		    
		    for(int i = 4; i < cant+4; i++) {
		    		Pokedex pokee = buscarPoke(partes[i]);
		    		gii.agregarP(pokee);
		    }
		}

		br.close();
		
	}

}
