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

	
	/**
	 * Inicia el programa mostrando un mensaje de bienvenida y luego muestra el menú principal.
	 * Después de ejecutar las acciones, cierra el scanner.
	 */
	public void iniciarPrograma() {
		System.out.println("*** ¡¡Bienvenido a Vivero GestionApp!! ***");
		mostrarMenuPrincipal();
		scanner.close();
	}

	
	/**
	 * Muestra el menú principal del programa y gestiona las opciones seleccionadas por el usuario.
	 * Ofrece tres opciones:
	 * 1. Ver plantas (Modo invitado).
	 * 2. Iniciar sesión con usuario y contraseña.
	 * 3. Salir del programa.
	 * 
	 * El método permite al usuario interactuar con el programa mediante un ciclo que valida la entrada
	 * y ejecuta acciones correspondientes a cada opción. Si se selecciona la opción de inicio de sesión,
	 * se valida el usuario y la contraseña, y en caso de éxito, muestra el menú adecuado según el rol del usuario.
	 * 
	 * Si el usuario selecciona la opción 1, se muestran las plantas registradas en el vivero.
	 * En caso de seleccionar una opción no válida, el programa muestra un mensaje de error.
	 */
	public void mostrarMenuPrincipal() {

		int opcion = 0;
		do {
			System.out.println("\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
			System.out.println("-------------------------------");
			System.out.println("      ¿Qué quieres hacer?");
			System.out.println("-------------------------------");
			System.out.println("Selecciona una opción:");
			System.out.println("1.  Ver plantas (Modo invitado)");
			System.out.println("2.  Login");
			System.out.println("3.  Salir");

			try {
				opcion = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.err.println("Opción no válida. Debes introducir un número entero.");
				scanner.next(); // Limpiar el buffer de entrada
				continue; // Volver a pedir la opción
			}

			switch (opcion) {
			case 1:
				List<Planta> plantas = svPlanta.mostrarPlantas();
				if (plantas.isEmpty()) {
					System.out.println("Aún o hay plantas registradas en el vivero.");
				} else {
					System.out.println("\n______ Plantas registradas en el vivero ______");
					for (int i = 0; i < plantas.size(); i++) {
						System.out.println((i + 1) + ". "+ plantas.get(i).getNombreComun() +" - "+ plantas.get(i).getNombreCientifico());
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
						sesion.setUsuario(usuario.toLowerCase());
						System.out.println("\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
						System.out.println("\t¡Hola "+sesion.getUsuario()+"!");
						System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
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


	/**
	 * Muestra el menú de opciones para el administrador del sistema, permitiéndole gestionar diversas áreas
	 * del sistema, tales como plantas, ejemplares, mensajes y usuarios.
	 * 
	 * Las opciones disponibles en el menú son:
	 * 1. Gestionar plantas: Permite al administrador gestionar las plantas registradas.
	 * 2. Gestionar ejemplares: Permite al administrador gestionar los ejemplares de plantas.
	 * 3. Gestionar mensajes: Permite al administrador gestionar los mensajes relacionados con las plantas y ejemplares.
	 * 4. Registrar nuevo usuario: Permite al administrador registrar un nuevo usuario en el sistema, validando los campos
	 *    como nombre, email, nombre de usuario y contraseña antes de realizar el registro.
	 * 5. Cerrar sesión: Permite al administrador cerrar sesión en el sistema.
	 * 6. Cerrar sesión y salir: Permite al administrador cerrar sesión y salir del programa.
	 * 
	 * El método controla las opciones seleccionadas por el administrador, validando la entrada y realizando las
	 * acciones correspondientes. Si se selecciona la opción 4 para registrar un usuario, se solicita la información necesaria
	 * al administrador y se validan los datos antes de intentar registrar al nuevo usuario en el sistema.
	 */
	public void mostrarMenuAdministrador() {

		int opcion = 0;

		do {
			System.out.println("\n-----------------------------");
			System.out.println("     ¿Qué quieres hacer?");
			System.out.println("-----------------------------");
			System.out.println("Selecciona una opción:");
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
				System.out.println("\n-----------------------------");
				System.out.println("** Registrar nuevo usuario **");
				System.out.println("-----------------------------");

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
					System.out.println("Introduce un nombre de usuario:");
					nombreUsuario = scanner.nextLine();

					if (!Validar.validarNombreUsuario(nombreUsuario)) {
						System.err.println("El nombre de usuario solo puede contener letras y/o números.");
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
								"La contraseña debe tener entre 6 y 10 caracteres (letras y/o números).");
					} else {
						break;
					}

				} while (true);

				if (svPersona.registrarPersona(nombre, email, nombreUsuario, contrasena)) {
					System.out.println("Nuevo usuario registrado con éxito\n");
					
				} else {
					System.err.println("Error al registrar al nuevo usuario.\n");
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
	
	
	/**
	 * Muestra el menú de opciones para un usuario personal del sistema, permitiéndole gestionar diversas áreas
	 * relacionadas con su cuenta y acciones asociadas.
	 * 
	 * Las opciones disponibles en el menú son:
	 * 1. Gestionar ejemplares: Permite al usuario gestionar los ejemplares de plantas.
	 * 2. Gestionar mensajes: Permite al usuario gestionar los mensajes relacionados con las plantas o ejemplares.
	 * 3. Cerrar sesión: Permite al usuario cerrar sesión en el sistema y regresar al menú principal.
	 * 4. Cerrar sesión y salir: Permite al usuario cerrar sesión y salir del programa.
	 * 
	 * El método gestiona la entrada del usuario, realizando la acción correspondiente según la opción seleccionada. 
	 * Si se selecciona la opción 3 o 4, se cerrará la sesión del usuario. La opción 4 también finalizará la ejecución
	 * del programa.
	 */
	public void mostrarMenuPersonal() {

		int opcion = 0;
		do {
			System.out.println("\n-----------------------------");
			System.out.println("     ¿Qué quieres hacer?");
			System.out.println("-----------------------------");
			System.out.println("Seleccione una opción:");
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
