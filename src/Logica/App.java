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
	public static boolean campeon = false;
	public static ArrayList<Pokedex> pdxs = new ArrayList<>();
	public static ArrayList<String> zonas = new ArrayList<>();
	public static ArrayList<Gimnasio> gim = new ArrayList<>();
	public static ArrayList<String> medallas = new ArrayList<>();
	public static ArrayList<AltoMando> altoMando = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		App a = new App();
		a.ejecutar();

	}

	private void ejecutar() throws IOException {
		leerPokedex();// lee el archivo que contiene a los pokedex
		leerHabitat();// lee el archivo de las habitat
		leerGimnasios();// Lee el archivo de los gimasios
		leerAltoMando(); // Lee el archivo del Alto mando
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
		System.out.print("1) Continuar." + "\n2) Nueva Partida." + "\n3) Salir." + "\nIngrese Opcion: ");

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
			
			if (medallas.contains("CAMPEON")) {
			    App.campeon = true;
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

				// ####
				Pokedex pkx = buscarPoke(nombrePokemon);
				Pokemon poke = new Pokemon(pkx);
				poke.setEstado(estado);
				jugador.agregarPokemon(poke);

				// Simplemente para verificar si lo lee bien, después se borrara
				System.out.println("Pokemon: " + nombrePokemon + " | Estado: " + estado);

			}
			System.out.println();

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

		// Todo lo relacionado al juego va a quedar en una función
		menuJuego(sc);

	}

	// Función: menu Juego
	public static void menuJuego(Scanner sc) throws IOException {
		int op;
		Random r = new Random();
		
		do {
			System.out.print("¡" + jugador.getNombre() + "!, que deseas hacer?" + "\n1) Revisar equipo.\r\n"
					+ "2) Salir a capturar.\r\n" + "3) Acceso al PC (cambiar Pokémon del equipo).\r\n"
					+ "4) Retar un gimnasio.\r\n" + "5) Desafío al Alto Mando.\r\n" + "6) Curar Pokémon.\r\n"
					+ "7) Guardar.\r\n" + "8) Guardar y Salir.\n");

			op = inputInt("Ingrese una opciòn: ", sc);
			switch (op) {
			case 1:
				System.out.println("Equipo actual: ");
				jugador.mostrarEquipo();// metodo en la clase Jugador
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
			case 5:
				retarAltoMando(sc, r);
				System.out.println();
				System.out.println("fuera de alto mando");//despues se borrara es para ver si funciona
				System.out.println();
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
			default:
				System.out.println("Ingrese un valor correcto!!");
				
			} 
				

		} while (op != 8);
	}
	
	// Desafiar al alto mando función 12
	private static void retarAltoMando(Scanner sc, Random r) {
		for (Gimnasio g : gim) {
	        if (g.getEstado().equalsIgnoreCase("Sin derrotar")) {
	            System.out.println("Necesitas derrotar los 8 gimnasios primero!");
	            System.out.println();
	            return;
	        }
	    }

	    // Verificar que tenga pokémones vivos 
	    int limite = calcularLimite();
	    Pokedex jugadorPoke = null;
	    Pokemon pokemonJugador = null;

	    for (int j = 0; j < limite; j++) {
	        Pokemon pok = jugador.getPokemones().get(j);
	        if (pok.getEstado().equalsIgnoreCase("Vivo")) {
	            Pokedex aux = buscarPoke(pok.getNombre());
	            if (aux != null) {
	                pokemonJugador = pok;
	                jugadorPoke = aux;
	                break;
	            }
	        }
	    }

	    if (jugadorPoke == null) {
	        System.out.println("No tienes pokemones vivos! Cura tu equipo primero.");
	        System.out.println();
	        return;
	    }

	    System.out.println("Desafiando al Alto Mando!");
	    System.out.println();
	   int k = 0;
	   for (int i = 0; i < altoMando.size(); i++) {
		   AltoMando rival = altoMando.get(i);
		   
		   int index = r.nextInt(rival.getPk().size());
	       Pokedex rivalPoke = rival.getPk().get(index);
	       
	       System.out.println("Desafiando a " + rival.getNombre() + "!!");
	       System.out.println(rival.getNombre() + " saca a " + rivalPoke.getNombre() + "!");
	       System.out.println(jugador.getNombre() + " saca a " + jugadorPoke.getNombre() + "!");
	       System.out.println();
	       
	       boolean batallaActiva = true;
	       
	       while(batallaActiva) {
	    	   System.out.println("Qué deseas hacer\n"
	    	   		+ "1) Atacar\n"
	    	   		+ "2) Rendirse\n");
	    	   
	    	   		int opp = inputInt("Ingrese una opción: ", sc);
	    	   		System.out.println();
	           
	    	   		if (opp == 1) {
	    	   			TablaTipos tabla = new TablaTipos();
						String tipoJ = jugadorPoke.getTipo();
						String tipoR = rivalPoke.getTipo();

						double efectivo = tabla.getEfectividad(tipoJ, tipoR);
						double totalJugador = jugadorPoke.getTotalStats();
						double totalRival = rivalPoke.getTotalStats();

						System.out.println(jugadorPoke.getNombre() + " -> " + totalJugador + " puntos");
						System.out.println(rival.getNombre() + " -> " + totalRival + " puntos");

						if (efectivo == 2.0) {
							System.out.println(
								jugadorPoke.getNombre() + " es efectivo contra " + rival.getNombre() + "!");
							totalJugador = totalJugador * efectivo;
							System.out.println();
							System.out.println("Nuevo puntaje:");
							System.out.println(jugadorPoke.getNombre() + " -> " + totalJugador + " puntos");
							System.out.println(rival.getNombre() + " -> " + totalRival + " puntos");
						} else if (efectivo == 0.5) {
							System.out.println(
									jugadorPoke.getNombre() + " no es efectivo contra " + rival.getNombre() + "!");
							totalJugador = totalJugador * efectivo;
							System.out.println();
							System.out.println("Nuevo puntaje:");
							System.out.println(jugadorPoke.getNombre() + " -> " + totalJugador + " puntos");
							System.out.println(rival.getNombre() + " -> " + totalRival + " puntos");

						}

						
						if (totalJugador > totalRival) {
							System.out.println("Ha ganado " + jugadorPoke.getNombre() + "!");
							System.out.println();
						    
						    batallaActiva = false; 
						    k++;

						} else if (totalJugador == totalRival) {
							System.out.println("Empate");
							
							batallaActiva = false;
							
							
						} else {
							for (int j = 0; j < limite; j++) {
							    if (jugador.getPokemones().get(j) == pokemonJugador) {
							        jugador.getPokemones().get(j).setEstado("Debilitado");
							        break;
							    }
							}
							
							System.out.println("Ha ganado " + rival.getNombre() + "!");
							
							//Revisar si queda un siguiente pokemon
							Pokemon nextPokemon = null;
	                        Pokedex nextPokedex = null;
							 for (int j = 0; j < limite; j++) {
		                            Pokemon pok = jugador.getPokemones().get(j);

		                            if (pok.getEstado().equalsIgnoreCase("Vivo")) {
		                                Pokedex aux = buscarPoke(pok.getNombre());

		                                if (aux != null) {
		                                    nextPokemon = pok;
		                                    nextPokedex = aux;
		                                    break;
		                                }
		                            }
		                        }
							
							 if (nextPokemon == null) {
						        System.out.println("Te has quedado sin pokemones!");
						        System.out.println("Volviendo al menu...");
						        return;
						        
							}else {
								pokemonJugador= nextPokemon;
								jugadorPoke = buscarPoke(nextPokemon.getNombre());
								System.out.println(jugador.getNombre()+" saca a " + jugadorPoke.getNombre()+"!");
								System.out.println();
							}
							
						} 
	            } else if (opp == 2) {
	                System.out.println("Te has rendido!! Volviendo al menu...");
	                System.out.println();
	                return; 
	            }
	       }
	       
		}if(k == altoMando.size()) {
	    	System.out.println("Te has coronado como el campeón!!");
	    	App.campeon = true;
	    	return;
		}
		
	}

	// funcion 2: capturar pokemones
	private static void capturar(Scanner sc, Random r) {

		System.out.println();
		System.out.println("Donde deseas ir a explorar? ");
		System.out.println();

		int op;
		
		do {
			System.out.println("Zonas disponibles: ");
			int i = 1;
			for (String z : zonas) {// mostramos las zonas que existen
				System.out.println(i + ") " + z);
				i++;
			}

			System.out.println("7) Volver al menu.");
			do {
		        op = inputInt("Ingrese una opción: ", sc);
		        if (op < 1 || op > 7) {
		            System.out.println("Opción invalida.");
		        }
		    } while (op < 1 || op > 7);
			
			System.out.println();

			if (op != 7) {
				String hbt = zonas.get(op - 1);// obtenemos la zona en esta posición

				ArrayList<Pokedex> pokemonesZona = new ArrayList<>(); // Creamos un ArrayList que solo guarde los
																		// pokemones de ese habitat
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
				do {
					opCap = inputInt("Ingrese una opción: ", sc);
					System.out.println();
					if (opCap == 1) {
						Boolean pk = jugador.buscarE(elegido.getNombre());// busca si el pokemon ya existe en la lista
						if (pk == false) { // si no existe se agrega a la lista del pokemon
							jugador.agregarPokemon(new Pokemon(elegido));
							System.out.println("Se capturo " + elegido.getNombre() + " con exito!!");
						} else {
							System.out.println("Ya tienes este pokemon!!");
							System.out.println();
						}
						break;
					} else if (opCap == 2) {
						System.out
								.println("Has huido del combate!! " + elegido.getNombre() + " esta decepcionado de tí");
					} else {
						System.out.println("Opción invalida, intenta de nuevo.");
					}
				} while (opCap != 2);
			}

		} while (op != 7);

		return;
	}

	// funcion: muestra el menu captura
	private static void menuCaptura() {
		System.out.println("Que deseas hacer?\n" + "\n" + "1) Capturar\n" + "2) Huir");
	}

	// funcion 3: Acceso al PC
	private static void AccesoPC(Scanner sc) {
		int j = 1;
		System.out.println();
		System.out.println("Pokemones capturados");
		for (Pokemon p : jugador.getPokemones()) {// muestra los pokemones del jugador
			System.out.println(j + ") " + p.getNombre());
			j++;
		}
		System.out.println();
		int op;
		do {
			System.out.println("1) Cambiar Pokémon.\n" + "2) Salir");
			op = inputInt("Ingrese una opción:", sc);
			if (op == 1) {
				int size = jugador.getPokemones().size();
				int posI;
				int posT;
				System.out.println();
				System.out.println("Las posiciones deben ser numeros señalados anteriormente!!");
				System.out.println();
				// evita posiciones iguales o fuera del rango
				do {
					posI = inputInt("Ingrese la posicion del primer pokemon: ", sc);
				} while (posI - 1 < 0 || posI - 1 >= size);
				do {
					posT = inputInt("Ingrese la posicion del segundo pokemon: ", sc);
					if (posI == posT) {
						System.out.println("No se puede intercambiar el mismo pokemon");
						System.out.println();
					}
				} while (posT - 1 < 0 || posT - 1 >= size || posT == posI);

				// intercambia pokemones en la lista
				Pokemon aux = jugador.getPokemones().get(posI - 1);
				jugador.getPokemones().set(posI - 1, jugador.getPokemones().get(posT - 1));
				jugador.getPokemones().set(posT - 1, aux);
				System.out.println("Pokemones intercambiados con exito!!");
				break;

			} else if (op != 2)
				System.out.println("Opcion invalida");

		} while (op != 2);
	}

	// funcion 4: Retar Gimnasio
	private static void retarGimnasio(Scanner sc, Random r) {

		boolean todosGanados = true;
	    for (Gimnasio g : gim) {
	        if (g.getEstado().equalsIgnoreCase("Sin derrotar")) {
	            todosGanados = false;
	            break;
	        }
	    }
	    if (todosGanados) {
	        System.out.println("Ya has ganado todas las medallas! No hay lideres que retar.");
	        System.out.println();
	        return; 
	    }

		
		System.out.println();
		System.out.println("A cual Lider deseas retar? ");
		System.out.println();
		int op;
		do {
			int i = 1;
			for (Gimnasio g : gim) {
				System.out.println(i + ") " + g.getNombre() + "- Estado: " + g.getEstado());
				i++;
			}
			System.out.println("9) Volver al menu ");

			// La opción tiene que estar dentro del rango
			do {
				op = inputInt("Ingrese una opción: ", sc);
				if (op < 1 || op > gim.size() + 1) {
					System.out.println("Opción invalida");
				}

			} while (op < 1 || op > gim.size() + 1);

			System.out.println();

			Boolean est = true; // para revisar si puede retar
			if (op != 9) {
				for (int j = op - 2; j > -1; j--) {
					if (gim.get(j).getEstado().equalsIgnoreCase("Sin derrotar")) {
						est = false;
						break;
					}
				}
				if (est == false) {
					System.out.println();
					System.out.println("Calmado Entrenador!!! No puedes retar a " + gim.get(op - 1).getNombre()
							+ " sin haber derrotado a los lideres anteriores!!");
					System.out.println();
					continue; // Para volver a mostrar los gimnasios

				}
				
				if (gim.get(op - 1).getEstado().equalsIgnoreCase("Derrotado")) {
				    System.out.println("Ya derrotaste a " + gim.get(op - 1).getNombre() + "!");
				    System.out.println();
				    continue;
				}

				int limite = calcularLimite();// Calcula el tamaño del equipo
				
				Pokedex jugadorPoke = null;
				Pokemon pokemonJugador = null;
				// Busca un pokedex que tenga estado Vivo
				for (int j = 0; j < limite; j++) {
	                Pokemon pok = jugador.getPokemones().get(j);

	                if (pok.getEstado().equalsIgnoreCase("Vivo")) {
	                    Pokedex aux = buscarPoke(pok.getNombre());

	                    if (aux != null) {
	                        pokemonJugador = pok;
	                        jugadorPoke = aux;
	                        break;
	                    }
	                }
	            } if (jugadorPoke == null) {
	                System.out.println("No tienes pokemones vivos! Cura tu equipo primero.");
	                System.out.println();
	                return; 
	            }

				int index = r.nextInt(gim.get(op - 1).getPk().size());
				Pokedex rival = gim.get(op - 1).getPk().get(index);

				System.out.println("Desafiando a " + gim.get(op - 1).getNombre() + "!!");
				System.out.println();
				System.out.println(gim.get(op - 1).getNombre() + " saca a " + rival.getNombre() + "!");
				System.out.println(jugador.getNombre() + " saca a " + jugadorPoke.getNombre() + "!");
				

				

				int opp = 0;
				boolean batallaActiva = true;
				
				while (batallaActiva) {
					  //verificando que queden pokemones
					  boolean quedanVivosGlobal = false;

					    for (int j = 0; j < limite; j++) {
					        if (jugador.getPokemones().get(j).getEstado().equalsIgnoreCase("Vivo")) {
					            quedanVivosGlobal = true;
					            break;
					        }
					    }

					    if (!quedanVivosGlobal) {
					        System.out.println("Te has quedado sin pokemones!");
					        System.out.println("Volviendo al menu...");
					        return;
					        
					    }
					System.out.println();
					System.out.println("Que deseas hacer\n" + "1) Ataca\n " + "2) Cambiar de Pokémon\n" + "3) Rendirse");
					opp = inputInt("Ingrese una opción: ", sc);
					System.out.println();
					
					if (opp == 1) {
						TablaTipos tabla = new TablaTipos();
						String tipoJ = jugadorPoke.getTipo();
						String tipoR = rival.getTipo();

						double efectivo = tabla.getEfectividad(tipoJ, tipoR);
						double totalJugador = jugadorPoke.getTotalStats();
						double totalRival = rival.getTotalStats();

						System.out.println(jugadorPoke.getNombre() + " -> " + totalJugador + " puntos");
						System.out.println(rival.getNombre() + " -> " + totalRival + " puntos");

						if (efectivo == 2.0) {
							System.out.println(
								jugadorPoke.getNombre() + " es efectivo contra " + rival.getNombre() + "!");
							totalJugador = totalJugador * efectivo;
							System.out.println();
							System.out.println("Nuevo puntaje:");
							System.out.println(jugadorPoke.getNombre() + " -> " + totalJugador + " puntos");
							System.out.println(rival.getNombre() + " -> " + totalRival + " puntos");
						} else if (efectivo == 0.5) {
							System.out.println(
									jugadorPoke.getNombre() + " no es efectivo contra " + rival.getNombre() + "!");
							totalJugador = totalJugador * efectivo;
							System.out.println();
							System.out.println("Nuevo puntaje:");
							System.out.println(jugadorPoke.getNombre() + " -> " + totalJugador + " puntos");
							System.out.println(rival.getNombre() + " -> " + totalRival + " puntos");

						}

						
						if (totalJugador > totalRival) {
							System.out.println("Ha ganado " + jugadorPoke.getNombre() + "!");
							
							
							//Marcar gimnasio como derrotado
						    gim.get(op - 1).setEstado("Derrotado");
						    
						    //guardar la medalla
						    if (jugador.getMedallas().equals("none")) {
						        jugador.setMedallas(gim.get(op - 1).getNombre());
						    } else {
						        jugador.setMedallas(jugador.getMedallas() + ";" + gim.get(op - 1).getNombre());
						    }
						    
						    batallaActiva = false; 
						    return; // Nos devuelve al menú

						} else if (totalJugador == totalRival) {
							System.out.println("Empate");
							
							batallaActiva = false;
							
							
						} else {
							for (int j = 0; j < limite; j++) {
							    if (jugador.getPokemones().get(j) == pokemonJugador) {
							        jugador.getPokemones().get(j).setEstado("Debilitado");
							        break;
							    }
							}
							
							System.out.println("Ha ganado " + rival.getNombre() + "!");
							
							//Revisar si queda un siguiente pokemon
							Pokemon nextPokemon = null;
	                        Pokedex nextPokedex = null;
							 for (int j = 0; j < limite; j++) {
		                            Pokemon pok = jugador.getPokemones().get(j);

		                            if (pok.getEstado().equalsIgnoreCase("Vivo")) {
		                                Pokedex aux = buscarPoke(pok.getNombre());

		                                if (aux != null) {
		                                    nextPokemon = pok;
		                                    nextPokedex = aux;
		                                    break;
		                                }
		                            }
		                        }
							
							 if (nextPokemon == null) {
						        System.out.println("Te has quedado sin pokemones!");
						        System.out.println("Volviendo al menu...");
						        //batallaActiva = false;
						        return;
						        
							}else {
								pokemonJugador= nextPokemon;
								jugadorPoke = buscarPoke(nextPokemon.getNombre());
								System.out.println(jugador.getNombre()+" saca a " + jugadorPoke.getNombre()+"!");
							}
							
						} 
						

					} else if (opp == 2) {

						int lim = calcularLimite();
						System.out.println("Elige tu pokemon:");
						for (int j = 0; j < lim; j++) {
							Pokemon pok = jugador.getPokemones().get(j);
							System.out.println((j + 1) + ") " + pok.getNombre() + " - " + pok.getEstado());
						}
						
						int eleccion = inputInt("Elige un pokemon: ", sc) - 1;
						Pokemon candidato = jugador.getPokemones().get(eleccion);
						if (candidato.getEstado().equalsIgnoreCase("Debilitado")) {
						    System.out.println("Ese pokemon esta debilitado, elige otro.");
						} else {
						    pokemonJugador = candidato;
						    jugadorPoke = buscarPoke(candidato.getNombre());
						    System.out.println("Cambiaste a " + jugadorPoke.getNombre() + "!");
						}

					} else if (opp == 3) {
						System.out.println("Te has rendido!!, volviendo al munú...");
						System.out.println();
						batallaActiva = false;

					}

					
				}

			}
		} while (op != 9);
	}
	//funcion: calcula el tamaño del equipo
	private static int calcularLimite() {
		int lim;
		if (jugador.getPokemones().size() < 6) {
			lim = jugador.getPokemones().size();
		} else {
			lim = 6;
		}
		return lim;
	}

	// funcion: busca al pokedex por su nombre
	private static Pokedex buscarPoke(String nombrePokemon) {
		for (Pokedex pk : pdxs) {
			if (pk.getNombre().equalsIgnoreCase(nombrePokemon.trim())) {
				return pk;
			}
		}System.out.println("No se encontró: " + nombrePokemon);
		return null;
	}

	// Función 6: Cura al equipo
	private static void curarPokemones() {
		ArrayList<Pokemon> lista = jugador.getPokemones();

		if (lista.isEmpty()) {
			System.out.println("No tienes pokemones para curar.");
			return;
		}

		for (Pokemon p : lista) {
			p.setEstado("Vivo");
		}
		System.out.println();
		System.out.println("Tu equipo se ha recuperado!");
	}

	// Función 7 y 8: Guarda los cambios en "Registros.txt"
	public static void guardarPartida() {
		try {
			// Se usa el false porque queremos que el archivo se reinicie cada vez que
			// alguién guarde
			BufferedWriter bw = new BufferedWriter(new FileWriter("Registros.txt", false));
			// Mantenemos el formato del enunciado -> [nombre;medallas]
			bw.write(jugador.getNombre() + ";" + jugador.getMedallas());
			
			//Simplemente para saber si el usuario es CAMPEON a la hora de leer el archivo
			if (App.campeon) {
			    jugador.setMedallas(jugador.getMedallas() + ";CAMPEON");
			}
			bw.write(jugador.getNombre() + ";" + jugador.getMedallas());
			
			bw.newLine();

			for (Pokemon p : jugador.getPokemones()) { // por cada pokemon del jugador se guardara en el archivo
				bw.write(p.getNombre() + ";" + p.getEstado());
				bw.newLine();
			}

			bw.close();
			BufferedWriter b = new BufferedWriter(new FileWriter("Gimnasios.txt", false));
			for(int i = 0; i < gim.size(); i++) {
				//1;EmmaLaArdillaRabiosa;Sin derrotar;3;Minun;Plusle;Emolga
				Gimnasio g = gim.get(i);
				b.write(g.getNro()+";"+g.getNombre()+";"+g.getEstado()+";"+g.getPk().size()+";");
				
				
				
				int n = g.getPk().size();
				int j = 1;
				for(Pokedex pp: g.getPk()) {
					if(j < n) {
						b.write(pp.getNombre()+";");
					}else {
						b.write(pp.getNombre());
						
					}
					j++;
				}b.newLine();
			}b.close();
			System.out.println("Partida guardada con exito!");
			if(campeon == true) {
				
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al guardar la partida");
			e.printStackTrace();

		}
	}

	// funcion : Facilida preguntar las opciones o algun numero en particular
	public static int inputInt(String mg, Scanner sc) {

		System.out.print(mg);

		while (true) {
			try {
				return Integer.parseInt(sc.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Ingrese un número válido: ");
	        }
			
		}

	
	}

	// funcion : lee el archivo "Pokedex.txt"
	private void leerPokedex() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Pokedex.txt"));
		String linea;

		while ((linea = br.readLine()) != null) {
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String habitat = partes[1];
			double porcentaje = Double.parseDouble(partes[2]);
			int vida = Integer.parseInt(partes[3]);
			int ataque = Integer.parseInt(partes[4]);
			int defensa = Integer.parseInt(partes[5]);
			int ataqueEspecial = Integer.parseInt(partes[6]);
			int defensaEspecial = Integer.parseInt(partes[7]);
			int velocidad = Integer.parseInt(partes[8]);
			String tipo = partes[9];
			pdxs.add(new Pokedex(nombre, habitat, porcentaje, vida, ataque, defensa, ataqueEspecial, defensaEspecial,
					velocidad, tipo));
		}

		br.close();
	}

	// funcion: lectura archivo "Habitat"
	private static void leerHabitat() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Habitats.txt"));
		String linea;
		while ((linea = br.readLine()) != null) {
			zonas.add(linea);// agregamos la zona a nuestra lista
		}
		br.close();
	}
	
	// Función lectura de archivo "Alto Mando"
	private void leerAltoMando() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Alto Mando.txt"));
	    String linea;

	    while ((linea = br.readLine()) != null) {
	        String[] partes = linea.split(";");
	        int nro = Integer.parseInt(partes[0]);
	        String nombre = partes[1];

	        AltoMando am = new AltoMando(nro, nombre);
	        altoMando.add(am);
	        
	        for (int i = 2; i <= 7; i++) {
	            Pokedex poke = buscarPoke(partes[i]);
	            am.agregarP(poke); 
	        }
	    }
	    br.close();
	}
	
	// lee el archivo "Gimnasios
	private void leerGimnasios() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Gimnasios.txt"));
		String linea;

		while ((linea = br.readLine()) != null) {
			String[] partes = linea.split("\\s*;\\s*");
			int nro = Integer.parseInt(partes[0]);
			String nombre = partes[1];
			String estado = partes[2];
			int cant = Integer.parseInt(partes[3]);
			Gimnasio gii = new Gimnasio(nro, nombre, estado);
			gim.add(gii);

			for (int i = 4; i < cant + 4; i++) {
				Pokedex pokee = buscarPoke(partes[i]);
				gii.agregarP(pokee);
			}
		}

		br.close();

	}

}
