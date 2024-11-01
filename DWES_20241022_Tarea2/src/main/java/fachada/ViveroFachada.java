package fachada;

import java.util.InputMismatchException;
import java.util.Scanner;

import conexionBD.SesionActiva;
import modelo.Planta;
import servicios.*;
import utilidades.Validar;;

public class ViveroFachada {

	private static ViveroFachada portalVivero;
	Scanner scanner = new Scanner(System.in);
	SesionActiva sesion = SesionActiva.getSesionActiva();

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
					boolean valido = false;
					do {
						System.out.print("Introduce tu usuario: ");
						String usuario = scanner.next();
						System.out.print("Introduce tu contraseña: ");
						String contraseña = scanner.next();
						if (svCredenciales.login(usuario, contraseña)) {
							valido = true;
							sesion.setUsuario(usuario);
							if (usuario.equals("admin")) {
								mostrarMenuAdministrador();		
							} else {
								mostrarMenuPersonal();
							}
						} else {
							System.out.println("\nERROR: Usuario o contraseña incorrectos\n");
						}
					}while(!valido);
					break;
					
				case 3:
					System.out.println("¡Adios!");
					break;
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 3);
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
					sesion.setUsuario(null);
					return;
				case 4:
					System.out.println("¡Adios!");
					System.exit(0);
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 4);

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
					boolean valido = false;
					System.out.println("*** Registrar nuevo usuario ***");
					
				    String nombre = "";
				    String email = "";
				    String nombreUsuario = "";
				    String contrasena = "";

				    // Solicitar todos los datos
				    System.out.println("Introduce un nombre: ");
				    nombre = scanner.next();
				    
				    System.out.println("Introduce un email:");
				    email = scanner.next();
				    
				    System.out.println("Introduce nombre de usuario:");
				    nombreUsuario = scanner.next();
				    
				    System.out.println("Introduce una contraseña:");
				    contrasena = scanner.next();

				 // Validar cada campo
				    while (true) {
				        boolean isNombreValido = Validar.validarNombre(nombre);
				        boolean isEmailValido = Validar.validarEmail(email);
				        boolean isEmailUnico = svPersona.existeEmail(email);
				        boolean isNombreUsuarioValido = Validar.validarNombreUsuario(nombreUsuario);
				        boolean isUsuarioUnico = svCredenciales.existeUsuario(nombreUsuario);

				        boolean todosValidos = true;

				        if (!isNombreValido) {
				            System.out.println("Introducidos caracteres no válidos en el nombre. Intenta de nuevo:");
				            nombre = scanner.next();
				            todosValidos = false; 
				        } 
				        if (!isEmailValido) {
				            System.out.println("Formato de email no válido. Intenta de nuevo:");
				            email = scanner.next(); 
				            todosValidos = false;
				        }
				        
				        if (!isNombreUsuarioValido) {
				            System.out.println("El nombre de usuario ya existe o no es válido. Intenta de nuevo:");
				            nombreUsuario = scanner.next(); 
				            todosValidos = false; 
				        } 
				        
				        if (!isNombreUsuarioValido) {
				            System.out.println("El nombre de usuario ya existe o no es válido. Intenta de nuevo:");
				            nombreUsuario = scanner.next(); 
				            todosValidos = false; 
				        } 
				        
				        if (todosValidos) {
				            break;
				        }
				    }
				    
				    
					break;
				case 5:
					sesion.setUsuario(null);
					return;
				case 6:
					System.out.println("¡Adios!");
					System.exit(0);
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 6);
	}

	
	public void mostrarMenuGestionarPlantas() {

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
					//Vuelve al menú anterior que le hizo la llamada
					return;
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 3);

	}

	
	public void mostrarMenuGestionarEjemplares() {

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
					return;
					/*
					// si es usuario personal:
					mostrarMenuPersonal();

					// si es admin
					mostrarMenuAdministrador();
					break;
					*/
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 4);
	}

	
	public void mostrarMenuGestionarMensajes() {

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
					return;
					/*
					// si es usuario personal:
					mostrarMenuPersonal();

					// si es admin
					mostrarMenuAdministrador();
					break;
					*/
				default:
					System.out.println("Opción incorrecta.");
				}
			} while (opcion != 5);

	}

	public void cerrarScanner() {
		if (scanner != null) {
            scanner.close(); // Cierra el Scanner cuando se termina de usar
        }
		
	}

}
