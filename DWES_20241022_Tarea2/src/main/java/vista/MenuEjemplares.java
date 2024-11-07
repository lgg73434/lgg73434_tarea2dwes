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

public class MenuEjemplares {

	private SesionActiva s;

	public MenuEjemplares(SesionActiva sesion) {
		this.s = sesion;
	}

	Scanner scanner = new Scanner(System.in);

	Controlador serviciosControlador = Controlador.getServicios();

	ServiciosPlanta svPlanta = serviciosControlador.getServiciosPlanta();
	ServiciosEjemplar svEjemplar = serviciosControlador.getServiciosEjemplar();
	ServiciosCredenciales svCredenciales = serviciosControlador.getServiciosCredenciales();
	ServiciosMensaje svMensaje = serviciosControlador.getServiciosMensaje();
	ServiciosPersona svPersona = serviciosControlador.getServiciosPersona();

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
				scanner.nextLine();

			} catch (InputMismatchException e) {
				System.out.println("Opción no válida. Por favor, introduce un número entero.");
				scanner.nextLine(); // Limpiar el buffer de entrada
				continue; // Volver a pedir la opción
			}

			switch (opcion) {
			case 1:
				System.out.println("*** Registrar un nuevo ejemplar ***");

				if (!svPlanta.mostrarPlantas().isEmpty()) {

					System.out.println("Plantas registradas en el vivero:");

					for (int i = 0; i < svPlanta.mostrarPlantas().size(); i++) {
						System.out.println(i + 1 + ". " + svPlanta.mostrarPlantas().get(i).getNombreComun());
					}

					int numFinalLista = svPlanta.mostrarPlantas().size();
					int numPlanta = 0;
					do {
						try {
							System.out.println(
									"Introduce el numero de la planta de la que quieres registrar un ejemplar");
							numPlanta = scanner.nextInt();
							scanner.nextLine();

							if (numPlanta < 1 || numPlanta > numFinalLista) {
								System.err.println("Debes introducir un número entre 1 y " + numFinalLista);
							} else {
								Ejemplar e = svEjemplar.crearEjemplar(svPlanta.mostrarPlantas().get(numPlanta - 1));
								if (e != null) {
									String mensaje = "Ejemplar registrado por: " + s.getUsuario() + " a las "
											+ Validar.formatoFecha(LocalDateTime.now());
									Mensaje m = new Mensaje(LocalDateTime.now(), mensaje,
											svCredenciales.getIdCredenciales(s.getUsuario()), e.getId());

									if (svMensaje.crearMensaje(m) > 0) {
										System.out.println("Ejemplar y mensaje inicial registrados con exito");
									} else {
										System.out.println(
												"Ejemplar registrado, pero no se ha podido añadir el mensaje inicial");
									}

								} else {
									System.err.println("Error al registrar el ejemplar");
								}
							}
						} catch (InputMismatchException e) {
							System.err.println("Debes introducir un número");
							scanner.nextLine();
						}
					} while (numPlanta < 1 || numPlanta > numFinalLista);
				} else {
					System.out.println("Aún no hay plantas registradas en el vivero:");
				}

				break;

			case 2:
				System.out.println("*** Ejemplares de planta/s ***");

				List<Planta> plantas = svPlanta.mostrarPlantas();
				if (!plantas.isEmpty()) {

					System.out.println("Plantas existentes en el vivero:");
					for (int i = 0; i < plantas.size(); i++) {
						System.out.println(i + 1 + ". " + plantas.get(i).getNombreComun());
					}

					int numeroFinalLista = plantas.size();
					int numeroPlanta = 0;
					List<Planta> plantasElegidas = new ArrayList<Planta>();

					do {
						System.out.println(
								"Introduce el número de la planta de la que quieres ver los ejemplares o introduce 0 para salir");
						try {
							numeroPlanta = scanner.nextInt();
							scanner.nextLine();
						} catch (InputMismatchException e) {
							System.err.println("Debes introducir un número");
							scanner.nextLine();
						}

						if (numeroPlanta > numeroFinalLista || numeroPlanta < 0) {
							System.err.println("Debes introducir un número entre 0 y " + numeroFinalLista);
							continue;
						}

						if (numeroPlanta != 0) {
							plantasElegidas.add(plantas.get(numeroPlanta - 1));
						}

					} while (numeroPlanta != 0);

					if (!plantasElegidas.isEmpty()) {
						for (int a = 0; a < plantasElegidas.size(); a++) {
							System.out.println(
									"\n\t\t*** Ejemplares de " + plantasElegidas.get(a).getNombreComun() + " ***");
							ArrayList<Ejemplar> ejemplares = svEjemplar.mostrarEjemplaresPlanta(plantasElegidas.get(a));

							if (!ejemplares.isEmpty()) {
								System.out.printf("%-20s %-20s %-20s%n", "NOMBRE", "Nº MENSAJES", "ULTIMO MENSAJE");
								System.out.println("---------------------------------------------------------------");
								for (int b = 0; b < ejemplares.size(); b++) {
									ArrayList<Mensaje> mensajes = svMensaje.getMensajesPorEjemplar(ejemplares.get(b));
									System.out.printf("%-20s %-20d %-20s%n", ejemplares.get(b).getNombre(),
											mensajes.size(), Validar.formatoFecha(mensajes.get(0).getFechaHora()));

								}
							} else {
								System.out.println("No existen ejemplares registrados de "
										+ plantasElegidas.get(a).getNombreComun() + ".");
							}

						}

					} else {
						System.out.println("No has seleccionado ninguna planta para ver sus ejemplares.");
					}

				} else {
					System.out.println("Aún no hay plantas registradas en el vivero:");
				}
				break;

			case 3:
				// Ver mensajes de seguimiento de un ejemplar
				System.out.println("*** Ver mensajes de seguimiento ***");

				ArrayList<Ejemplar> ejemplares = svEjemplar.mostrarEjemplares();
				if (!ejemplares.isEmpty()) {

					System.out.println("Ejemplares existentes en el vivero:");
					for (int i = 0; i < ejemplares.size(); i++) {
						System.out.println(i + 1 + ". " + ejemplares.get(i).getNombre());
					}

					int numEjemplar = 0;
					int finEjemplares = ejemplares.size();
					do {
						System.out.println("Introduce el número de ejemplar del que quieres ver su seguimiento.");
						try {
							numEjemplar = scanner.nextInt();
							scanner.nextLine();
						} catch (InputMismatchException e) {
							System.err.println("Debes introducir un número");
							scanner.nextLine();
						}

						if (numEjemplar > finEjemplares || numEjemplar < 0) {
							System.err.println("Debes introducir un número entre 1 y " + finEjemplares);
							continue;
						} else {
							break;
						}

					} while (true);

					if (svMensaje.getMensajesPorEjemplar(ejemplares.get(numEjemplar - 1)).isEmpty()) {
						System.out.println("El ejemplar no tiene mensajes");
					} else {
						System.out.println(
								"Lista de mensajes del ejemplar: " + ejemplares.get(numEjemplar - 1).getNombre());
						for (Mensaje m : svMensaje.getMensajesPorEjemplar(ejemplares.get(numEjemplar - 1))) {
							System.out.println(Validar.formatoFecha(m.getFechaHora()) + "\t"
									+ svPersona.getPersonaporID(m.getIdPersona()).getNombre() + "\n\t" + m.getMensaje()
									+ "\n");
						}
					}

				} else {
					System.out.println("Aún no hay ejemplares registrados en el vivero:");
				}

				break;

			case 4:
				return;

			default:
				System.err.println("Opción incorrecta.");
			}
		} while (opcion != 4);
	}

}
