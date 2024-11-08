package vista;

import java.util.InputMismatchException;
import java.util.Scanner;

import controlador.Controlador;
import controlador.ServiciosPlanta;
import modelo.Planta;
import utilidades.Validar;

public class MenuPlantas {

	Scanner scanner = new Scanner(System.in);
	Controlador serviciosControlador = Controlador.getServicios();
	ServiciosPlanta svPlanta = serviciosControlador.getServiciosPlanta();

	public void mostrarMenuGestionarPlantas() {

		int opcion = 0;
		do {
			System.out.println("\n-----------------------------");
			System.out.println("**    Gestionar plantas    **");
			System.out.println("-----------------------------");
			System.out.println("Seleccione una opción:");
			System.out.println("1.  Registrar planta");
			System.out.println("2.  Modificar planta");
			System.out.println("3.  Volver atrás");

			try {
				opcion = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.err.println("Opción no válida. Debes introducir un número entero.");
				scanner.nextLine();
				continue; // Volver a pedir la opción
			}

			switch (opcion) {
			case 1:
				System.out.println("\n------------------------------");
				System.out.println("**   Registrar una planta   **");
				System.out.println("------------------------------");

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

				} while (true);

				do {
					System.out.println("Introduce el nombre común de la planta: ");
					nombreComun = scanner.nextLine();

					if (!Validar.validarNombre(nombreComun)) {
						System.err.println("Introducidos caracteres no válidos en el nombre común.");
					}

				} while (!Validar.validarNombre(nombreComun));

				do {
					System.out.println("Introduce el nombre científico de la planta: ");
					nombreCientifico = scanner.nextLine();

					if (!Validar.validarNombre(nombreCientifico)) {
						System.err.println("Introducidos caracteres no válidos en el nombre científico.");
					}

				} while (!Validar.validarNombre(nombreCientifico));

				Planta plantaNueva = new Planta(codigo.toUpperCase(), nombreComun, nombreCientifico);

				if (svPlanta.registrarPlanta(plantaNueva) > 0) {
					System.out.println("Nueva planta registrada con éxito");
				} else {
					System.err.println("Error al registrar la nueva planta.");
				}

				break;

			case 2:

				System.out.println("\n------------------------------");
				System.out.println("**   Modificar una planta   **");
				System.out.println("------------------------------");

				if (!svPlanta.mostrarPlantas().isEmpty()) {
					System.out.println("______ Plantas registradas en el vivero ______");

					for (int i = 0; i < svPlanta.mostrarPlantas().size() && !svPlanta.mostrarPlantas().isEmpty(); i++) {
						System.out.println(i + 1 + ". " + svPlanta.mostrarPlantas().get(i).getNombreComun());
					}

					System.out.println("\n");
					
					int numFinalLista = svPlanta.mostrarPlantas().size();
					int numPlanta = 0;
					do {
						try {
							System.out.println("Introduce el número de la planta que quieres modificar: ");
							numPlanta = scanner.nextInt();
							scanner.nextLine();

							if (numPlanta < 1 || numPlanta > numFinalLista) {
								System.err.println("Debes introducir un número entre 1 y " + numFinalLista);

							} else {
								Planta planta = svPlanta.mostrarPlantas().get(numPlanta - 1);

								do {
									System.out.println("Introduce el nuevo nombre común de la planta: ");
									nombreComun = scanner.nextLine();

									if (!Validar.validarNombre(nombreComun)) {
										System.err.println("Introducidos caracteres no válidos en el nombre común.");
									}
								} while (!Validar.validarNombre(nombreComun));

								do {
									System.out.println("Introduce el nuevo nombre científico de la planta:");
									nombreCientifico = scanner.nextLine();

									if (!Validar.validarNombre(nombreCientifico)) {
										System.err.println("Introducidos caracteres no válidos en el nombre científico.");
									}
								} while (!Validar.validarNombre(nombreCientifico));

								planta.setNombrecientifico(nombreCientifico);
								planta.setNombreComun(nombreComun);

								if (svPlanta.actualizarPlanta(planta) > 0) {
									System.out.println("Planta actualizada correctamente.");
								} else {
									System.err.println("Error al actualizar los datos de la planta.");
								}
							}
						} catch (InputMismatchException e) {
							System.err.println("Debes introducir un número entero.");
							scanner.nextLine();
						}
					} while (numPlanta < 1 || numPlanta > numFinalLista);

				} else {
					System.out.println("Aún no hay plantas registradas en el vivero.");
				}
				break;

			case 3:
				return;

			default:
				System.err.println("Opción incorrecta.");
			}
		} while (opcion != 3);

	}
}
