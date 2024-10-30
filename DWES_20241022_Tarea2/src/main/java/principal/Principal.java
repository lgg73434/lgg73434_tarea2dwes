package principal;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionBD.ConexionBD;
import fachada.ViveroFachada;
import servicios.ServiciosPlanta;

public class Principal {

	public static void main(String[] args) {

		ViveroFachada portalVivero = ViveroFachada.getPortal();
		
		portalVivero.mostrarMenuPrincipal();
				


	}

}
