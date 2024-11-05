package vista;

import java.sql.SQLSyntaxErrorException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controlador.*;
import modelo.Ejemplar;
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
					String usuario = scanner.nextLine();
					System.out.print("Introduce tu contraseña: ");
					String contrasena = scanner.nextLine();
					
					if (svCredenciales.login(usuario, contrasena)) {
						valido = true;
						sesion.setUsuario(usuario);
						
						if (usuario.equalsIgnoreCase("admin")) {
							mostrarMenuAdministrador(sesion);
						} else {
							mostrarMenuPersonal(sesion);
						}
						
					} else {
						System.err.println("\nUsuario o contraseña incorrectos\n");
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
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.err.println("Opción no válida. Por favor, introduce un número entero.");
				scanner.nextLine(); // Limpiar el buffer de entrada
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
				System.out.println("¡Adios!");
				System.exit(0);
				
			default:
				System.err.println("Opción incorrecta.");
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
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.err.println("Opción no válida. Por favor, introduce un número entero.");
				scanner.nextLine(); // Limpiar el buffer de entrada
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
				
				do {
					System.out.println("Introduce un nombre:");
					nombre = scanner.nextLine();
					
					if (!Validar.validarNombre(nombre)) {
						System.err.println("Caracteres no válidos en el nombre.");
					} else {
						break;
					}
					
				}while(true);
				
				do {
					System.out.println("Introduce un email:");
					email = scanner.nextLine();
					
					if (!Validar.validarEmail(email)) {
						System.err.println("Formato de email no válido.");
					} 
					
					if (svPersona.isEmailRegistrado(email)) {
						System.err.println("Email ya registrado en el sistema.");
					} 
					
					if(Validar.validarEmail(email) && !svPersona.isEmailRegistrado(email)) {
						break;
					}	
					
				}while(true);
				
				do {
					System.out.println("Introduce nombre de usuario:");
					nombreUsuario = scanner.nextLine();
					
					if (!Validar.validarNombreUsuario(nombreUsuario)) {
						System.err.println("El nombre de usuario no puede contener espacios.");
					} 
					
					if(svCredenciales.existeUsuario(nombreUsuario)) {
						System.err.println("El nombre de usuario ya existe.");
					}
					
					if(Validar.validarNombreUsuario(nombreUsuario) && !svCredenciales.existeUsuario(nombreUsuario)) {
						break;
					}
					
				}while(true);
				
				do {
					System.out.println("Introduce una contraseña:");
					contrasena = scanner.nextLine();
					
					if (!Validar.validarContrasena(contrasena)) {
						System.err.println("La contraseña debe tener entre 6 y 10 caracteres y no puede contener espacios.");
					} else {
						break;
					}
					
				}while(true);

				if (svPersona.registrarPersona(nombre, email, nombreUsuario, contrasena)) {
					System.out.println("Nuevo usuario registrado con éxito");
				} else {
					System.err.println("Error al registrar al nuevo usuario.");
				}
				break;
				
			case 5:
				sesion.setUsuario(null);
				return;
				
			case 6:
				System.out.println("¡Adios!");
				System.exit(0);
				
			default:
				System.err.println("Opción incorrecta.");
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
				System.err.println("Opción no válida. Por favor, introduce un número entero.");
				scanner.nextLine(); // Limpiar el buffer de entrada
				continue; // Volver a pedir la opción
			}

			switch (opcion) {
			case 1:
				System.out.println("*** Registrar nueva planta ***\n");

				String codigo = "";
				String nombreComun = "";
				String nombreCientifico = "";
				
				do {
					System.out.println("Introduce el código de la planta: ");
					codigo = scanner.nextLine();
					
					if (!Validar.validarCodigo(codigo)) {
						System.err.println("Introducidos caracteres no válidos en el código.");
					}
					
					if (svPlanta.existeCodigo(codigo)) {
						System.err.println("Código ya registrado en el sistema.");
					}
					
					if (Validar.validarCodigo(codigo) && !svPlanta.existeCodigo(codigo)) {
						break;
					}
					
				}while(true);
				
				do {
					System.out.println("Introduce el nombre común de la planta: ");
					nombreComun = scanner.nextLine();
					
					if (!Validar.validarNombre(nombreComun)) {
						System.err.println("Introducidos caracteres no válidos en el nombre común.");
					}
					
				}while (!Validar.validarNombre(nombreComun));
				
				do {
					System.out.println("Introduce el nombre científico de la planta: ");
					nombreCientifico = scanner.nextLine();
					
					if (!Validar.validarNombre(nombreCientifico)) {
						System.err.println("Introducidos caracteres no válidos en el nombre común.");
					}
					
				}while (!Validar.validarNombre(nombreCientifico));

				Planta plantaNueva = new Planta (codigo.toUpperCase(), nombreComun, nombreCientifico);

				if (svPlanta.registrarPlanta(plantaNueva)>0) {
					System.out.println("Nueva planta registrada con éxito");
				} else {
					System.err.println("Error al registrar la nueva planta.");
				}

				break;
				
			case 2:
				
				System.out.println("*** Modificar datos de una planta ***\n");
				
				if(!svPlanta.mostrarPlantas().isEmpty()) {
					System.out.println("Plantas registradas en el vivero:");
					
					for (int i=0; i<svPlanta.mostrarPlantas().size() && !svPlanta.mostrarPlantas().isEmpty(); i++) {
						System.out.println(i+1+". "+svPlanta.mostrarPlantas().get(i).getNombreComun());
					}
					
					int numFinalLista = svPlanta.mostrarPlantas().size();
					int numPlanta = 0;
					do {
						try {
							System.out.println("Introduce el número de la planta que quieres modificar: ");
							numPlanta = scanner.nextInt();
							scanner.nextLine();
							
							if (numPlanta < 1 || numPlanta > numFinalLista) {
								System.err.println("Debes introducir un número entre 1 y "+ numFinalLista);
								
							} else {								
								Planta planta = svPlanta.mostrarPlantas().get(numPlanta-1);
								
								do {
									System.out.println("Introduce el nombre común de la planta: ");
									nombreComun = scanner.nextLine();
									
									if (!Validar.validarNombre(nombreComun)) {
										System.err.println("Introducidos caracteres no válidos en el nombre común.");
									}								
								}while (!Validar.validarNombre(nombreComun));
								
								do {
									System.out.println("Introduce el nombre científico de la planta:");
									nombreCientifico = scanner.nextLine();
									
									if (!Validar.validarNombre(nombreCientifico)) {
										System.err.println("Introducidos caracteres no válidos en el nombre común.");
									}	
								}while (!Validar.validarNombre(nombreCientifico));
								
								planta.setNombrecientifico(nombreCientifico);
								planta.setNombreComun(nombreComun);
								
								if (svPlanta.actualizarPlanta(planta)>0) {
									System.out.println("Planta actualizada correctamente.");
								}else {
									System.err.println("Error al actualizar la planta.");
								}
							}
						} catch (InputMismatchException e) {
							System.err.println("Debes introducir un número");
							scanner.nextLine(); 
						}
					}while (numPlanta < 1 || numPlanta > numFinalLista);

				} else {
					System.out.println("Aún no hay plantas registradas en el vivero.");
				}
				break;
				
			case 3:
				// Vuelve al menú anterior que le hizo la llamada
				return;
				
			default:
				System.err.println("Opción incorrecta.");
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
				scanner.nextLine();
				
			} catch (InputMismatchException e) {
				System.out.println("Opción no válida. Por favor, introduce un número entero.");
				scanner.nextLine(); // Limpiar el buffer de entrada
				continue; // Volver a pedir la opción
			}

			switch (opcion) {
			case 1:
				System.out.println("*** Registrar un nuevo ejemplar ***");
				
				if(!svPlanta.mostrarPlantas().isEmpty()) {
					
					System.out.println("Plantas registradas en el vivero:");
					
					for (int i=0; i<svPlanta.mostrarPlantas().size(); i++) {
						System.out.println(i+1+". "+svPlanta.mostrarPlantas().get(i).getNombreComun());
					}
					
					int numFinalLista = svPlanta.mostrarPlantas().size();
					int numPlanta = 0;
					do {
						try {
							System.out.println("Introduce el numero de la planta de la que quieres registrar un ejemplar");
							numPlanta = scanner.nextInt();
							scanner.nextLine();
							
							if (numPlanta < 1 || numPlanta > numFinalLista) {
								System.err.println("Debes introducir un número entre 1 y "+ numFinalLista);
							} else {
								
								if (svEjemplar.crearEjemplar(svPlanta.mostrarPlantas().get(numPlanta-1))!=null) {
									String mensaje = "Ejemplar registrado por: "+s.getUsuario()+" a las "+LocalDateTime.now();
									Mensaje m = new Mensaje(LocalDateTime.now(), mensaje, svCredenciales.getIdCredenciales(s.getUsuario()), svEjemplar.crearEjemplar(svPlanta.mostrarPlantas().get(numPlanta-1)).getId());
									
									if (svMensaje.crearMensaje(m)>0) {
										System.out.println("Ejemplar y mensaje inicial registrados con exito");
									}else {
										System.out.println("Ejemplar registrado, pero no se ha podido añadir el mensaje inicial");
									}
									
								}else {
									System.err.println("Error al registrar el ejemplar");
								}
							}
						} catch (InputMismatchException e) {
							System.err.println("Debes introducir un número");
							scanner.nextLine(); 
						}
					}while (numPlanta < 1 || numPlanta > numFinalLista);
				} else {
					System.out.println("Aún no hay plantas registradas en el vivero:");
				}
				
				break;
				
			case 2:
				System.out.println("*** Ejemplares de una planta ***");
				
				List<Planta> plantas = svPlanta.mostrarPlantas();
				if(!plantas.isEmpty()) {
					
					System.out.println("Plantas existentes en el vivero:");
					for (int i=0; i<plantas.size(); i++) {
						System.out.println(i+1+". "+plantas.get(i).getNombreComun());
					}
					
					int numeroFinalLista = plantas.size();
					int numeroPlanta = 0;
					List<Planta> plantasElegidas = new ArrayList<Planta>();
					
					do {
						System.out.println("Introduce el numero de la/s planta/s de la/s que quieres ver los ejemplares o introduce 0 para salir");
						try {
							do {
								numeroPlanta = scanner.nextInt();
								scanner.nextLine();	
								
								if (numeroPlanta > numeroFinalLista) {
									System.err.println("Debes introducir un número entre 0 y "+ numeroFinalLista);
								} 
								
								if(numeroPlanta != 0) {
									plantasElegidas.add(plantas.get(numeroPlanta));
								}
								
							}while (numeroPlanta!=0);
							
							if(!plantasElegidas.isEmpty()) {
								for(int a = 0; a<plantasElegidas.size(); a++) {
									System.out.println("- Ejemplares de "+plantasElegidas.get(a).getNombreComun()+":");
									ArrayList<Ejemplar> ejemplares = svEjemplar.mostrarEjemplaresPlanta(plantasElegidas.get(a));
									
									if(!ejemplares.isEmpty()) {
										for (int b = 0; b<ejemplares.size(); b++) {
											// recorrer la lista de los ejemplares
											//implementar método en dao Ejemplar
										}
									} else {
										System.out.println("No hay ejemplares existentes de ese tipo de planta.");
									}
									
									
								}
								
							}else {
								System.out.println("No has elegido ninguna planta de la que ver sus ejemplares.");
							}
								 
								
							
		
						} catch (InputMismatchException e) {
							System.err.println("Debes introducir un número");
							scanner.nextLine(); 
						}
					}while (numeroPlanta < 1 || numeroPlanta > numeroFinalLista);
					
				} else {
					System.out.println("Aún no hay plantas registradas en el vivero:");
				}
				break;
				
			case 3:
				// Ver mensajes de seguimiento de un ejemplar
				break;
				
			case 4:
				return;
	
			default:
				System.err.println("Opción incorrecta.");
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
