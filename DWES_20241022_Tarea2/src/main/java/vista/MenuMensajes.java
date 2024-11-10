package vista;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controlador.Controlador;
import controlador.ServiciosCredenciales;
import controlador.ServiciosEjemplar;
import controlador.ServiciosMensaje;
import controlador.ServiciosPersona;
import controlador.ServiciosPlanta;
import controlador.SesionActiva;
import modelo.Ejemplar;
import modelo.Mensaje;
import modelo.Planta;
import utilidades.Validar;

public class MenuMensajes {

	private SesionActiva s;

	public MenuMensajes(SesionActiva sesion) {
		this.s = sesion;
	}

	Scanner scanner = new Scanner(System.in);

	Controlador serviciosControlador = Controlador.getServicios();

	ServiciosPlanta svPlanta = serviciosControlador.getServiciosPlanta();
	ServiciosEjemplar svEjemplar = serviciosControlador.getServiciosEjemplar();
	ServiciosPersona svPersona = serviciosControlador.getServiciosPersona();
	ServiciosCredenciales svCredenciales = serviciosControlador.getServiciosCredenciales();
	ServiciosMensaje svMensaje = serviciosControlador.getServiciosMensaje();

	
	/**
	 * Muestra el menú de opciones para gestionar los mensajes en el sistema.
	 * Permite al administrador crear nuevos mensajes, buscar mensajes por usuario,
	 * fecha o tipo de planta, o volver al menú anterior.
	 * 
	 * Las opciones disponibles en el menú son:
	 * 1. Crear nuevo mensaje: Permite al administrador seleccionar un ejemplar y crear un mensaje
	 *    asociado a dicho ejemplar.
	 * 2. Buscar mensajes por usuario: Permite al administrador buscar y mostrar los mensajes
	 *    creados por un usuario específico.
	 * 3. Buscar mensajes por fecha: Permite al administrador buscar y mostrar los mensajes registrados
	 *    dentro de un rango de fechas.
	 * 4. Buscar mensajes por tipo de planta: Permite al administrador buscar los mensajes asociados
	 *    a un tipo de planta registrado en el sistema.
	 * 5. Volver atrás: Regresa al menú anterior.
	 * 
	 * El método gestiona la entrada del usuario y realiza las acciones correspondientes según
	 * la opción seleccionada. Se validan los datos de entrada como el número de ejemplar, nombre
	 * de usuario y fechas, y se muestran los resultados de las búsquedas de mensajes basadas
	 * en los criterios seleccionados.
	 */
	public void mostrarMenuGestionarMensajes() {

		int opcion = 0;
		do {
			System.out.println("\n------------------------------");
			System.out.println("**    Gestionar mensajes    **");
			System.out.println("------------------------------");
			System.out.println("Seleccione una opción:");
			System.out.println("1.  Nuevo mensaje");
			System.out.println("2.  Buscar mensajes por usuario");
			System.out.println("3.  Buscar mensajes por fecha");
			System.out.println("4.  Buscar mensajes por tipo de planta");
			System.out.println("5.  Volver atrás");

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
				System.out.println("\n------------------------------");
				System.out.println("**   Crear nuevo mensaje    **");
				System.out.println("------------------------------");
				
				ArrayList<Ejemplar> ejemplares = svEjemplar.mostrarEjemplares();
				if (!ejemplares.isEmpty()) {

					System.out.println("____ Ejemplares registrados en el vivero ____");
					for (int i = 0; i < ejemplares.size(); i++) {
						System.out.println(i + 1 + ". " + ejemplares.get(i).getNombre());
					}
					
					int numEjemplar = 0;
					int finEjemplares = ejemplares.size();
					do {
						System.out.println("Introduce el número de ejemplar del que quieres crear un mensaje.");
						try {
							numEjemplar = scanner.nextInt();
							scanner.nextLine();
						} catch (InputMismatchException e) {
							System.err.println("Debes introducir un número.");
							scanner.nextLine();
						}

						if (numEjemplar > finEjemplares || numEjemplar < 0) {
							System.err.println("Debes introducir un número entre 1 y " + finEjemplares);
							continue;
						} else {
							break;
						}

					} while (true);

					Mensaje men = null;

					String mensaje = "";

					do {
						System.out.println("Introduce un mensaje: ");
						mensaje = scanner.nextLine();
						if (!Validar.validarMensaje(mensaje)) {
							System.err.println("Formato de mensaje incorrecto.");
							continue;
						} else {
							men = new Mensaje(LocalDateTime.now(), mensaje,
									svCredenciales.getIdCredenciales(s.getUsuario()),
									ejemplares.get(numEjemplar - 1).getId());
							if (svMensaje.crearMensaje(men) > 0) {
								System.out.println("Mensaje añadido correctamente.");
							} else {
								System.err.println("No se ha podido añadir el mensaje.");
							}
						}

					} while (!Validar.validarMensaje(mensaje));

				} else {
					System.out.println("Aún no hay ejemplares registrados en el vivero");
				}
				break;
								
			case 2:
				System.out.println("\n-------------------------------");
				System.out.println("**   Mensajes por usuario    **");
				System.out.println("-------------------------------");
				
				String nombreUsuario = "";
				do {
					System.out.println("Introduce el nombre de usuario: ");
					nombreUsuario = scanner.nextLine();
					if (!Validar.validarNombreUsuario(nombreUsuario)) {
						System.out.println("Nombre de usuario no válido.");
					} else {
						if (!svCredenciales.existeUsuario(nombreUsuario)) {
							System.out.println("Usuario no registrado en el sistema.");
						} else {
							Long idUsuario = svCredenciales.getIdCredenciales(nombreUsuario);
							ArrayList<Mensaje> mensajesUsuario = svMensaje.getMensajesPorUsuario(idUsuario);
							if (!mensajesUsuario.isEmpty()) {
								System.out.println("\n____ Mensajes registrados por: " + nombreUsuario + " ____");
								for (Mensaje mensaje : mensajesUsuario) {
									System.out.println("\n" + Validar.formatoFecha(mensaje.getFechaHora()) + "\n\t"
											+ mensaje.getMensaje());
								}
							} else {
								System.out.println("No existen mensajes registrados por el usuario " + nombreUsuario + ".");
							}
						}
					}

				} while (!Validar.validarNombreUsuario(nombreUsuario) || !svCredenciales.existeUsuario(nombreUsuario));
				break;

			case 3:
				System.out.println("\n-------------------------------");
				System.out.println("**    Mensajes por fecha     **");
				System.out.println("-------------------------------");
				
				String fecha1 = "";
				String fecha2 = "";
				LocalDateTime fechaHora1 = null;
				LocalDateTime fechaHora2 = null;

				do {
					System.out.print("Introduce una fecha inicial en formato dd/mm/yyyy: ");
					fecha1 = scanner.nextLine();

					fechaHora1 = Validar.validarYConvertirFechaInicio(fecha1);

					if (fechaHora1 == null) {
						System.err.println("La fecha introducida no es válida.");

					} else if (fechaHora1.isAfter(LocalDateTime.now())) {
						System.err.println("La fecha introducida no puede superar la fecha actual.");

					} else {
						break;
					}
				} while (true);

				do {
					System.out.print("Introduce una fecha final en formato dd/mm/yyyy: ");
					fecha2 = scanner.nextLine();

					fechaHora2 = Validar.validarYConvertirFechaFin(fecha2);

					if (fechaHora2 == null) {
						System.err.println("La fecha introducida no es válida.");

					} else if (fechaHora2.isAfter(LocalDateTime.now())) {
						System.err.println("La fecha introducida no puede superar la fecha actual.");
					} else if (!fechaHora1.isBefore(fechaHora2)) {
						System.err.println("La fecha introducida no puede ser anterior a la fecha inicial.");
					} else {
						fechaHora2.plusHours(24);
						break;
					}

				} while (true);

				ArrayList<Mensaje> mensajesFecha = svMensaje.getMensajesFecha(fechaHora1, fechaHora2);
				if (!mensajesFecha.isEmpty()) {
					System.out.println("\n____ Mensajes registrados entre: " + Validar.formatoFecha(fechaHora1) + " y "
							+ Validar.formatoFecha(fechaHora2) + " ____");
					for (Mensaje mensajeFecha : mensajesFecha) {
						System.out.println(Validar.formatoFecha(mensajeFecha.getFechaHora()) + " "
								+ svEjemplar.getEjemplarPorId(mensajeFecha.getIdEjemplar()) + "\n\t"
								+ mensajeFecha.getMensaje()+"\n");
					}
				} else {
					System.out.println("No hay mensajes registrados en ese rango de fechas.");
				}

				break;
				
			case 4:
				System.out.println("\n-----------------------------------");
				System.out.println("**  Mensajes por tipo de planta  **");
				System.out.println("-----------------------------------");
				List<Planta> plantas = svPlanta.mostrarPlantas();
				if (!plantas.isEmpty()) {

					System.out.println("____ Plantas registradas en el vivero ____");
					for (int i = 0; i < plantas.size(); i++) {
						System.out.println(i + 1 + ". " + plantas.get(i).getNombreComun());
					}

					System.out.println("\n");
					int numPlanta = 0;
					int finPlantas = plantas.size();
					do {
						System.out.println("Introduce el número de planta de la que quieres ver los mensajes.");
						try {
							numPlanta = scanner.nextInt();
							scanner.nextLine();
						} catch (InputMismatchException e) {
							System.err.println("Debes introducir un número entero.");
							scanner.nextLine();
						}

						if (numPlanta > finPlantas || numPlanta < 0) {
							System.err.println("Debes introducir un número entre 1 y " + finPlantas);
							continue;
						} else {
							break;
						}

					} while (true);

					ArrayList<Mensaje> mensajesPlanta = svMensaje.getMensajesPorPlanta(plantas.get(numPlanta - 1));
					if (!mensajesPlanta.isEmpty()) {
						System.out.println("\n____ Mensajes registrados para "+plantas.get(numPlanta - 1).getNombreComun() +" ____");
						for (Mensaje mensajePlanta : mensajesPlanta) {
							System.out.println(Validar.formatoFecha(mensajePlanta.getFechaHora()) + "  "
									+ svEjemplar.getEjemplarPorId(mensajePlanta.getIdEjemplar()) + "\n\t"
									+ mensajePlanta.getMensaje()+"\n");
						}
					} else {
						System.out.println("Aún no hay mensajes registrados para la planta: "+plantas.get(numPlanta - 1).getNombreComun());
					}

				} else {
					System.out.println("Aún no hay plantas registradas en el vivero.");
				}
				break;

			case 5:
				return;

			default:
				System.out.println("Opción incorrecta.");
			}
		} while (opcion != 5);

	}

}
