/*
 * Nombres: Diego Serrano Fuentes, Josselyn Barraza Yáñez
 * Rut(s): 22.105.561-6 | 22.246.539-7
 * Carrera: ICCI
 * Taller: Taller N° 1 POO
 */

package Logica;

import java.io.*;
import java.util.*;

public class App {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		String opcion = "";
		
		
		
		while (true) {
			menuPrincipal();
			opcion = sc.nextLine();
			if (opcion.equals("1")) {
				//Funcion continuar partida
				menuContinuarPartida(sc);
				break;
			} else if (opcion.equals("2")) {
				//Nueva partida
				System.out.println("");
				break;
			} else if (opcion.equals("3")) {
				break;
			} else {
				System.out.println("\nOpción inválida intente de nuevo\n");
			}
		}
		
		
		
		sc.close();
	}
	
	//Función N° 1
	public static void menuPrincipal() {
		System.out.print("1) Continuar."
				+ "\n2) Nueva Partida."
				+ "\n3) Salir."
				+ "\nIngrese Opcion: ");
			
	}
	
	//Función N°2
	public static void menuContinuarPartida(Scanner sc) {
		/*
		 * Está función se encargara de cargar nuestro archivo de nombres, en caso de que no hayan jugadores cargados, se debera mostrar por pantalla
		 * que no hay jugadores cargados, y volveremos al menú inicial.
		 */
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("Registros.txt"));
			String linea;
			
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				
				String nombreCuenta = partes[0];
				String medallas = partes[1];
				
				//Falta crear un ciclo o for hasta completar los N pokemones (No se me ocurrio como hacerlo)
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("No se ha encontrado el archivo");
			e.printStackTrace();
		}
		
		
		
		
		
	}
}
