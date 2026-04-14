/*
 * Nombres: Diego Serrano Fuentes, Josselyn Barraza Yáñez
 * Rut(s): 22.105.561-6 | 22.246.539-7
 * Carrera: ICCI
 * Taller: Taller N° 1 POO
 */

package Logica;

import java.util.*;

public class App {
	public static void menu1() {
		System.out.print("1) Continuar."
				+ "\n2) Nueva Patida."
				+ "\n3) Salir."
				+ "\nIngrese Opcion: ");
			
	}
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		String opcion = "";
		
		
		
		while (true) {
			menu1();
			opcion = sc.nextLine();
			if (opcion.equals("1")) {
				//Funcion continuar partida
				System.out.println("");
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
}
