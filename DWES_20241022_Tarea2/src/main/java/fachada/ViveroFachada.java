package fachada;

import java.util.InputMismatchException;
import java.util.Scanner;

import modelo.Planta;
import servicios.*;;

public class ViveroFachada {

	private static ViveroFachada portalVivero;

	ServiciosFactory svFactory = ServiciosFactory.getServicios();

	ServiciosPlanta svPlanta = svFactory.getServiciosPlanta();
	ServiciosEjemplar svEjemplar = svFactory.getServiciosEjemplar();
	ServiciosPersona svPersona = svFactory.getServiciosPersona();
	ServiciosCredenciales svCredenciales = svFactory.getServiciosCredenciales();
	ServiciosMensaje svMensaje = svFactory.getServiciosMensaje();

	public static ViveroFachada getPortal() {
		if (portalVivero == null)
			portalVivero = new ViveroFachada();
		return portalVivero;
	}

	public void iniciarPrograma() {
		System.out.println("*** ¡¡Bienvenido a Vivero Gestión!! ***");
		mostrarMenuPrincipal();
	}

	
	public void mostrarMenuPrincipal() {

		try (Scanner scanner = new Scanner(System.in)) {

			int opcion = 0;
			do {
				System.out.println("\nSeleccione una opción:");
				System.out.println("1.  Ver plantas (Modo invitado)");
				System.out.println("2.  Login");
				System.out.println("3.  Salir");

				try {
					opcion = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Opción no válida. Por favor, introduce un número entero.");
					scanner.next(); // Limpiar el buffer de entrada
					continue; // Volver a pedir la opción
				}

				switch (opcion) {
				case 1:
					if (svPlanta.mostrarPlantas().size() == 0) {
						System.out.println("No hay plantas registradas.");
					} else {
						for (int i = 0; i < svPlanta.mostrarPlantas().size(); i++) {
							System.out.println(i + 1 + ". " + svPlanta.mostrarPlantas().get(i));
						}
					}
					break;
				case 2:
					// Login
					break;
				case 3:
					System.out.println("¡Hasta pronto!");
					break;
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 3);
		}
	}

	
	public void mostrarMenuPersonal() {
		try (Scanner scanner = new Scanner(System.in)) {

			int opcion = 0;
			do {
				System.out.println("\nSeleccione una opción:");
				System.out.println("1.  Gestionar ejemplares");
				System.out.println("2.  Gestionar mensajes");
				System.out.println("3.  Cerrar sesión");
				System.out.println("4.  Cerrar sesión y salir");

				try {
					opcion = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Opción no válida. Por favor, introduce un número entero.");
					scanner.next(); // Limpiar el buffer de entrada
					continue; // Volver a pedir la opción
				}

				switch (opcion) {
				case 1:
					mostrarMenuGestionarEjemplares();
					break;
				case 2:
					mostrarMenuGestionarMensajes();
					break;
				case 3:
					// AQUI FALTA HACER EL CIERRE DE SESIÓN
					mostrarMenuPrincipal();
					break;
				case 4:
					// AQUI FALTA HACER EL CIERRE DE SESIÓN
					System.out.println("¡Hasta pronto!");
					break;
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 4);
		}

	}

	
	public void mostrarMenuAdministrador() {
		try (Scanner scanner = new Scanner(System.in)) {

			int opcion = 0;
			do {
				System.out.println("\nSeleccione una opción:");
				System.out.println("1.  Gestionar plantas");
				System.out.println("2.  Gestionar ejemplares");
				System.out.println("3.  Gestionar mensajes");
				System.out.println("4.  Registrar persona");
				System.out.println("5.  Cerrar sesión");
				System.out.println("6.  Cerrar sesión y salir");

				try {
					opcion = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Opción no válida. Por favor, introduce un número entero.");
					scanner.next(); // Limpiar el buffer de entrada
					continue; // Volver a pedir la opción
				}

				switch (opcion) {
				case 1:
					mostrarMenuGestionarPlantas();
					break;
				case 2:
					mostrarMenuGestionarEjemplares();
					break;
				case 3:
					mostrarMenuGestionarMensajes();
					break;
				case 4:
					// PEDIR DATOS PARA REGISTRAR PERSONA
					break;
				case 5:
					// AQUI FALTA HACER EL CIERRE DE SESIÓN
					mostrarMenuPrincipal();
					break;
				case 6:
					// AQUI FALTA HACER EL CIERRE DE SESIÓN
					System.out.println("¡Hasta pronto!");
					break;
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 6);
		}
	}

	
	public void mostrarMenuGestionarPlantas() {
		try (Scanner scanner = new Scanner(System.in)) {

			int opcion = 0;
			do {
				System.out.println("\nSeleccione una opción:");
				System.out.println("1.  Registrar planta");
				System.out.println("2.  Modificar planta");
				System.out.println("3.  Volver atrás");

				try {
					opcion = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Opción no válida. Por favor, introduce un número entero.");
					scanner.next(); // Limpiar el buffer de entrada
					continue; // Volver a pedir la opción
				}

				switch (opcion) {
				case 1:
					// Pedir datos para registrar una planta
					break;
				case 2:
					// modificar una planta
					break;
				case 3:
					mostrarMenuAdministrador();
					break;
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 3);
		}

	}

	
	public void mostrarMenuGestionarEjemplares() {
		try (Scanner scanner = new Scanner(System.in)) {

			int opcion = 0;
			do {
				System.out.println("\nSeleccione una opción:");
				System.out.println("1.  Registrar un nuevo ejemplar");
				System.out.println("2.  Ver ejemplares de una planta");
				System.out.println("3.  Ver mensajes de seguimiento de un ejemplar");
				System.out.println("4.  Volver atrás");

				try {
					opcion = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Opción no válida. Por favor, introduce un número entero.");
					scanner.next(); // Limpiar el buffer de entrada
					continue; // Volver a pedir la opción
				}

				switch (opcion) {
				case 1:
					// Pedir datos para registrar un ejemplar
					break;
				case 2:
					// Ver ejemplares de una planta
					break;
				case 3:
					// Ver mensajes de seguimiento de un ejemplar
					break;
				case 4:
					// si es usuario personal:
					mostrarMenuPersonal();

					// si es admin
					mostrarMenuAdministrador();
					break;
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 4);
		}
	}

	
	public void mostrarMenuGestionarMensajes() {
		try (Scanner scanner = new Scanner(System.in)) {

			int opcion = 0;
			do {
				System.out.println("\nSeleccione una opción:");
				System.out.println("1.  Nuevo mensaje");
				System.out.println("2.  Buscar mensajes por usuario");
				System.out.println("3.  Buscar mensajes por fecha");
				System.out.println("4.  Buscar mensajes por tipo de planta");
				System.out.println("5.  Volver atrás");

				try {
					opcion = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Opción no válida. Por favor, introduce un número entero.");
					scanner.next(); // Limpiar el buffer de entrada
					continue; // Volver a pedir la opción
				}

				switch (opcion) {
				case 1:
					// Pedir datos para registrar un mensaje
					break;
				case 2:
					// buscar mensajes por usuario
					break;
				case 3:
					// buscar mensajes por fecha
					break;
				case 4:
					// buscar mensajes por tipo planta
					break;
				case 5:
					// si es usuario personal:
					mostrarMenuPersonal();

					// si es admin
					mostrarMenuAdministrador();
					break;
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 5);
		}

	}

}
