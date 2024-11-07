package vista;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controlador.*;

import modelo.Planta;
import utilidades.Validar;


public class ViveroFachada {

	private static ViveroFachada portalVivero;

	Scanner scanner = new Scanner(System.in);

	SesionActiva sesion = new SesionActiva ("");
	


	Controlador serviciosControlador = Controlador.getServicios();

	ServiciosPlanta svPlanta = serviciosControlador.getServiciosPlanta();
	ServiciosEjemplar svEjemplar = serviciosControlador.getServiciosEjemplar();
	ServiciosPersona svPersona = serviciosControlador.getServiciosPersona();
	ServiciosCredenciales svCredenciales = serviciosControlador.getServiciosCredenciales();
	ServiciosMensaje svMensaje = serviciosControlador.getServiciosMensaje();
	
	MenuEjemplares menuEjemplares = new MenuEjemplares (sesion);
	MenuPlantas menuPlantas = new MenuPlantas ();
	MenuMensajes menuMensajes = new MenuMensajes (sesion);
	


	public static ViveroFachada getPortal() {
		if (portalVivero == null)
			portalVivero = new ViveroFachada();
		return portalVivero;
	}

	public void iniciarPrograma() {
		System.out.println("*** ¡¡Bienvenido a Vivero GestionApp!! ***");
		mostrarMenuPrincipal();
		scanner.close();
	}

	public void mostrarMenuPrincipal() {

		int opcion = 0;
		do {
			System.out.println("\nSeleccione una opción:");
			System.out.println("1.  Ver plantas (Modo invitado)");
			System.out.println("2.  Login");
			System.out.println("3.  Salir");

			try {
				opcion = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.err.println("Opción no válida. Se debe introducir un número entero.");
				scanner.next(); // Limpiar el buffer de entrada
				continue; // Volver a pedir la opción
			}

			switch (opcion) {
			case 1:
				List<Planta> plantas = svPlanta.mostrarPlantas();
				if (plantas.isEmpty()) {
					System.out.println("No hay plantas registradas.");
				} else {
					System.out.println("Plantas registradas");
					for (int i = 0; i < plantas.size(); i++) {
						System.out.println((i + 1) + ". "+ plantas.get(i).getNombreComun() +"\tNombre científico: "+ plantas.get(i).getNombreCientifico());
					}
				}
				break;

			case 2:
				boolean valido = false;
				do {
					System.out.print("\nIntroduce tu usuario: ");
					String usuario = scanner.nextLine();
					System.out.print("Introduce tu contraseña: ");
					String contrasena = scanner.nextLine();

					if (svCredenciales.login(usuario, contrasena)) {
						valido = true;
						sesion.setUsuario(usuario);
						System.out.println("\n¡Hola "+sesion.getUsuario()+"!");
						if (usuario.equalsIgnoreCase("admin")) {
							mostrarMenuAdministrador();
						} else {
							mostrarMenuPersonal();
						}

					} else {
						System.err.println("\nUsuario o contraseña incorrectos.");
					}
				} while (!valido);

				break;

			case 3:
				System.out.println("¡Adios!");
				break;

			default:
				System.err.println("Opción incorrecta.");
			}

		} while (opcion != 3);
	}


	public void mostrarMenuAdministrador() {

		int opcion = 0;

		do {
			System.out.println("\nSeleccione una opción:");
			System.out.println("1.  Gestionar plantas");
			System.out.println("2.  Gestionar ejemplares");
			System.out.println("3.  Gestionar mensajes");
			System.out.println("4.  Registrar usuario");
			System.out.println("5.  Cerrar sesión");
			System.out.println("6.  Cerrar sesión y salir");

			try {
				opcion = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.err.println("Opción no válida. Por favor, introduce un número entero.");
				scanner.nextLine(); // Limpiar el buffer de entrada
				continue; // Volver a pedir la opción
			}

			switch (opcion) {
			case 1:
				menuPlantas.mostrarMenuGestionarPlantas();
				break;

			case 2:
			
				menuEjemplares.mostrarMenuGestionarEjemplares();
				break;

			case 3:
				menuMensajes.mostrarMenuGestionarMensajes();
				break;

			case 4:

				System.out.println("*** Registrar nuevo usuario ***");

				String nombre = "";
				String email = "";
				String nombreUsuario = "";
				String contrasena = "";

				do {
					System.out.println("Introduce un nombre:");
					nombre = scanner.nextLine();

					if (!Validar.validarNombre(nombre)) {
						System.err.println("Caracteres no válidos en el nombre.");
					} else {
						break;
					}

				} while (true);

				do {
					System.out.println("Introduce un email:");
					email = scanner.nextLine();

					if (!Validar.validarEmail(email)) {
						System.err.println("Formato de email no válido.");
					}

					if (svPersona.isEmailRegistrado(email)) {
						System.err.println("Email ya registrado en el sistema.");
					}

					if (Validar.validarEmail(email) && !svPersona.isEmailRegistrado(email)) {
						break;
					}

				} while (true);

				do {
					System.out.println("Introduce nombre de usuario:");
					nombreUsuario = scanner.nextLine();

					if (!Validar.validarNombreUsuario(nombreUsuario)) {
						System.err.println("El nombre de usuario no puede contener espacios.");
					}

					if (svCredenciales.existeUsuario(nombreUsuario)) {
						System.err.println("El nombre de usuario ya existe.");
					}

					if (Validar.validarNombreUsuario(nombreUsuario) && !svCredenciales.existeUsuario(nombreUsuario)) {
						break;
					}

				} while (true);

				do {
					System.out.println("Introduce una contraseña:");
					contrasena = scanner.nextLine();

					if (!Validar.validarContrasena(contrasena)) {
						System.err.println(
								"La contraseña debe tener entre 6 y 10 caracteres y no puede contener espacios.");
					} else {
						break;
					}

				} while (true);

				if (svPersona.registrarPersona(nombre, email, nombreUsuario, contrasena)) {
					System.out.println("Nuevo usuario registrado con éxito");
				} else {
					System.err.println("Error al registrar al nuevo usuario.");
				}
				break;

			case 5:
				sesion.setUsuario("");
				return;

			case 6:
				System.out.println("¡Adios!");
				System.exit(0);

			default:
				System.err.println("Opción incorrecta.");
			}

		} while (opcion != 6);
	}
	
	
	public void mostrarMenuPersonal() {

		int opcion = 0;
		do {
			System.out.println("\nSeleccione una opción:");
			System.out.println("1.  Gestionar ejemplares");
			System.out.println("2.  Gestionar mensajes");
			System.out.println("3.  Cerrar sesión");
			System.out.println("4.  Cerrar sesión y salir");

			try {
				opcion = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.err.println("Opción no válida. Por favor, introduce un número entero.");
				scanner.nextLine(); // Limpiar el buffer de entrada
				continue; // Volver a pedir la opción
			}

			switch (opcion) {
			case 1:
				menuEjemplares.mostrarMenuGestionarEjemplares();
				break;

			case 2:
				menuMensajes.mostrarMenuGestionarMensajes();
				break;

			case 3:
				sesion.setUsuario("");
				return;

			case 4:
				System.out.println("¡Adios!");
				System.exit(0);

			default:
				System.err.println("Opción incorrecta.");
			}

		} while (opcion != 4);

	}

	
	public void cerrarScanner() {
		if (scanner != null) {
			scanner.close(); // Cierra el Scanner cuando se termina de usar
		}

	}

}
