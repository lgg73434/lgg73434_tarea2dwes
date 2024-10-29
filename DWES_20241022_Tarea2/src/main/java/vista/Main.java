package vista;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import conexionBD.ConexionBD;
import dao.PlantaDAO;

public class Main {

	public static void main(String[] args) {
		
		 // Obtener la instancia de ConexionBD e intentar obtener la conexión:
        ConexionBD conexionBD = ConexionBD.getCon();
        Connection connection = conexionBD.getConnection();

        if (connection == null) {
            System.out.println("Error: No se pudo establecer la conexión a la base de datos.");
            return;
        }
        
        PlantaDAO plantaDao = new PlantaDAO();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\n===== Bienvenido al Sistema del Vivero =====");
            System.out.println("1. Ver plantas (Invitado)");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la línea

            switch (opcion) {
                case 1:
                    // CU1: Ver listado de plantas
				try {
					ArrayList plantas = (ArrayList) plantaDao.getAll(connection);
					for (int i = 0; i<plantas.size(); i++){
						System.out.println(plantas.get(i));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
                    break;

                case 2:
                    // CU2: Login 
                    
                    break;

                case 3:
                    System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        // Cerrar la conexión y scanner
        try {
            connection.close();
            scanner.close();
        } catch (Exception e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
        


	

}
