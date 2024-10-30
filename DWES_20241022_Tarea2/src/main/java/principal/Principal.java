package principal;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionBD.ConexionBD;
import fachada.ViveroFachada;
import servicios.ServiciosPlanta;

public class Principal {

	public static void main(String[] args) {
		// Obtener la instancia de ConexionBD e intentar obtener la conexión:
		ConexionBD conexionBD = ConexionBD.getCon();
		Connection connection = conexionBD.getConnection();

		if (connection == null) {
			System.out.println("Error: No se pudo establecer la conexión a la base de datos.");
			return;
		}

		ViveroFachada portalVivero = ViveroFachada.getPortal();
		

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("*** ¡¡Bienvenido a Vivero Gestión!! ***\n");

			int opcion = 0;
			do {
				portalVivero.mostrarMenuPrincipal();
				
				 try {
                     opcion = scanner.nextInt();
                 } catch (InputMismatchException e) {
                     System.out.println("Opción no válida. Por favor, introduce un número entero.");
                     scanner.next(); // Limpiar el buffer de entrada
                     continue; // Volver a pedir la opción
                 }

				switch (opcion) {
				case 1:
					
					break;
				case 2:
					
					break;
				case 3:
					System.out.println("¡Hasta pronto!");
					break;
				default:
					System.out.println("Opcion incorrecta.");
				}
			} while (opcion != 3);
		}

	}

}
