package fachada;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import controlador.*;
import modelo.Mensaje;
import modelo.Planta;
import utilidades.Validar;;

public class ViveroFachada {

	private static ViveroFachada portalVivero;
	Scanner scanner = new Scanner(System.in);
	SesionActiva sesion = SesionActiva.getSesionActiva();

	Controlador serviciosControlador = Controlador.getServicios();

	ServiciosPlanta svPlanta = serviciosControlador.getServiciosPlanta();
	ServiciosEjemplar svEjemplar = serviciosControlador.getServiciosEjemplar();
	ServiciosPersona svPersona = serviciosControlador.getServiciosPersona();
	ServiciosCredenciales svCredenciales = serviciosControlador.getServiciosCredenciales();
	ServiciosMensaje svMensaje = serviciosControlador.getServiciosMensaje();

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
					String contrasena = scanner.next();
					if (svCredenciales.login(usuario, contrasena)) {
						valido = true;
						sesion.setUsuario(usuario);
						if (usuario.equalsIgnoreCase("admin")) {
							mostrarMenuAdministrador(sesion);
						} else {
							mostrarMenuPersonal(sesion);
						}
					} else {
						System.out.println("\nERROR: Usuario o contraseña incorrectos\n");
					}
				} while (!valido);
				break;

			case 3:
				System.out.println("¡Adios!");
				break;
			default:
				System.out.println("Opción incorrecta.");
			}
		} while (opcion != 3);
	}

	public void mostrarMenuPersonal(SesionActiva s) {

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
				mostrarMenuGestionarEjemplares(s);
				break;
			case 2:
				mostrarMenuGestionarMensajes(s);
				break;
			case 3:
				sesion.setUsuario(null);
				return;
			case 4:
				sesion.setUsuario(null);
				System.out.println("¡Adios!");
				System.exit(0);
			default:
				System.out.println("Opción incorrecta.");
			}
		} while (opcion != 4);

	}

	public void mostrarMenuAdministrador(SesionActiva s) {

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
				mostrarMenuGestionarEjemplares(s);
				break;

			case 3:
				mostrarMenuGestionarMensajes(s);
				break;

			case 4:
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
					boolean isEmailRegistrado = svPersona.isEmailRegistrado(email);
					boolean isNombreUsuarioValido = Validar.validarNombreUsuario(nombreUsuario);
					boolean isUsuarioRegistrado = svCredenciales.existeUsuario(nombreUsuario);
					boolean isContrasenaValida = Validar.validarContrasena(contrasena);

					boolean todosValidos = true;

					if (!isNombreValido) {
						System.out.println("Introducidos caracteres no válidos en el nombre.\nIntenta de nuevo:");
						nombre = scanner.next();
						todosValidos = false;
					}

					if (!isEmailValido) {
						System.out.println("Formato de email no válido.\nIntenta de nuevo:");
						email = scanner.next();
						todosValidos = false;
					}

					if (isEmailRegistrado) {
						System.out.println("Email ya registrado en el sistema.\nIntenta de nuevo:");
						email = scanner.next();
						todosValidos = false;
					}

					if (!isNombreUsuarioValido) {
						System.out.println("El nombre de usuario no puede contener espacios.\nIntenta de nuevo:");
						nombreUsuario = scanner.next();
						todosValidos = false;
					}

					if (isUsuarioRegistrado) {
						System.out.println("El nombre de usuario ya existe.\nIntenta de nuevo:");
						nombreUsuario = scanner.next();
						todosValidos = false;
					}

					if (!isContrasenaValida) {
						System.out.println(
								"La contraseña debe tener entre 6 y 10 caracteres y no puede contener espacios.\nIntenta de nuevo:");
						nombreUsuario = scanner.next();
						todosValidos = false;
					}

					if (todosValidos) {
						break;
					}

				}

				if (svPersona.registrarPersona(nombre, email, nombreUsuario, contrasena)) {
					System.out.println("Nuevo usuario registrado con éxito");
				} else {
					System.out.println("Error al registrar al nuevo usuario.");
				}

				break;
			case 5:
				sesion.setUsuario(null);
				return;
			case 6:
				sesion.setUsuario(null);
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
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Opción no válida. Por favor, introduce un número entero.");
				scanner.next(); // Limpiar el buffer de entrada
				continue; // Volver a pedir la opción
			}

			switch (opcion) {
			case 1:
				System.out.println("*** Registrar nueva planta ***");

				String codigo = "";
				String nombreComun = "";
				String nombreCientifico = "";
				
				do {
					System.out.println("Introduce el código de la planta: ");
					codigo = scanner.nextLine();
					
					if (!Validar.validarCodigo(codigo)) {
						System.out.println("Introducidos caracteres no válidos en el código.");
					}
					
					if (svPlanta.existeCodigo(codigo)) {
						System.out.println("El código ya existe en el sistema.");
					}
					
					if (Validar.validarCodigo(codigo) && !svPlanta.existeCodigo(codigo)) {
						break;
					}
					
				}while(true);
				
				do {
					System.out.println("Introduce el nombre común de la planta: ");
					nombreComun = scanner.nextLine();
					if (!Validar.validarNombre(nombreComun)) {
						System.out.println("Introducidos caracteres no válidos en el nombre común.");
					}
				}while (!Validar.validarNombre(nombreComun));
				
				do {
					System.out.println("Introduce el nombre científico de la planta: ");
					nombreCientifico = scanner.nextLine();
					if (!Validar.validarNombre(nombreCientifico)) {
						System.out.println("Introducidos caracteres no válidos en el nombre común.");
					}
				}while (!Validar.validarNombre(nombreCientifico));

				Planta nueva = new Planta (codigo.toUpperCase(), nombreComun, nombreCientifico);

				if (svPlanta.registrarPlanta(nueva)>0) {
					System.out.println("Nueva planta registrada con éxito");
				} else {
					System.out.println("Error al registrar la nueva planta.");
				}

				break;
			case 2:
				
				System.out.println("Listado de plantas");
				for (int i=0; i<svPlanta.mostrarPlantas().size(); i++) {
					System.out.println(i+1+". "+svPlanta.mostrarPlantas().get(i).getNombreComun());
				}
				int numFinal = svPlanta.mostrarPlantas().size();
				int num = 0;
				do {
					try {
						System.out.println("Introduce el numero de la planta que quieres modificar: ");
						num = scanner.nextInt();
						scanner.nextLine();
						if (num < 1 || num > numFinal) {
							System.out.println("Numero incorrecto. Introduce un número entre el 1 y el "
									+ numFinal);
						} else {
							Planta modificar = svPlanta.mostrarPlantas().get(num-1);
							
							do {
								System.out.println("Introduce el nombre común de la planta: ");
								nombreComun = scanner.nextLine();
								if (!Validar.validarNombre(nombreComun)) {
									System.out.println("Introducidos caracteres no válidos en el nombre común.");
								}
							}while (!Validar.validarNombre(nombreComun));
							
							do {
								System.out.println("Introduce el nombre científico de la planta: ");
								nombreCientifico = scanner.nextLine();
								if (!Validar.validarNombre(nombreCientifico)) {
									System.out.println("Introducidos caracteres no válidos en el nombre común.");
								}
							}while (!Validar.validarNombre(nombreCientifico));
							
							modificar.setNombrecientifico(nombreCientifico);
							modificar.setNombreComun(nombreComun);
							
							if (svPlanta.actualizarPlanta(modificar)>0) {
								System.out.println("Planta actualizada correctamente");
							}else {
								System.out.println("Error a actualizar la planta");
							}
						}
					} catch (InputMismatchException e) {
						System.out.println("Error. Debes introducir un número");
						scanner.nextLine(); 
					}
				}while (num < 1 || num > numFinal);

				break;
			case 3:
				// Vuelve al menú anterior que le hizo la llamada
				return;
			default:
				System.out.println("Opción incorrecta.");
			}
		} while (opcion != 3);

	}

	public void mostrarMenuGestionarEjemplares(SesionActiva s) {

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
				System.out.println("Listado de plantas");
				for (int i=0; i<svPlanta.mostrarPlantas().size(); i++) {
					System.out.println(i+1+". "+svPlanta.mostrarPlantas().get(i).getNombreComun());
				}
				int numFinal = svPlanta.mostrarPlantas().size();
				int num = 0;
				do {
					try {
						System.out.println("Introduce el numero de planta de la que quieres registrar un ejemplar");
						num = scanner.nextInt();
						scanner.nextLine();
						if (num < 1 || num > numFinal) {
							System.out.println("Numero incorrecto. Introduce un número entre el 1 y el "
									+ numFinal);
						} else {
							if (svEjemplar.crearEjemplar(svPlanta.mostrarPlantas().get(num-1))!=null) {
								String mensaje = "Ejemplar registrado por: "+s.getUsuario()+" a las "+LocalDateTime.now();
								Mensaje m = new Mensaje(LocalDateTime.now(), mensaje, svCredenciales.getIdCredenciales(s.getUsuario()), svEjemplar.crearEjemplar(svPlanta.mostrarPlantas().get(num-1)).getId());
								if (svMensaje.crearMensaje(m)>0) {
									System.out.println("Ejemplar y mensaje inicial registrados con exito");
								}else {
									System.out.println("Ejemplar registrado, pero no se ha podido añadir el mensaje inicial");
								}
								
							}else {
								System.out.println("Error al registrar el ejemplar");
							}
						}
					} catch (InputMismatchException e) {
						System.out.println("Error. Debes introducir un número");
						scanner.nextLine(); 
					}
				}while (num < 1 || num > numFinal);
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
			 * // si es usuario personal: mostrarMenuPersonal();
			 * 
			 * // si es admin mostrarMenuAdministrador(); break;
			 */
			default:
				System.out.println("Opción incorrecta.");
			}
		} while (opcion != 4);
	}

	public void mostrarMenuGestionarMensajes(SesionActiva s) {

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
			 * // si es usuario personal: mostrarMenuPersonal();
			 * 
			 * // si es admin mostrarMenuAdministrador(); break;
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
