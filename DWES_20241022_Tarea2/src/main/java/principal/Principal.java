package principal;

import java.sql.Connection;
import java.util.Scanner;

import conexionBD.ConexionBD;
import fachada.ViveroFachada;

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

		try (Scanner in = new Scanner(System.in)) {
			System.out.println("Programa de gestion de un invernadero");

			int opcion = 0;
			do {
				portalVivero.mostrarMenuPrincipal();
				opcion = in.nextInt();

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
