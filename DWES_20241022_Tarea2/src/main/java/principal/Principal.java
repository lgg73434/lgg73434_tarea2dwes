package principal;

import fachada.ViveroFachada;


public class Principal {

	public static void main(String[] args) {

		ViveroFachada portalVivero = ViveroFachada.getPortal();
		
		portalVivero.iniciarPrograma();
				
		// Cierra el Scanner después de que el programa termine
        portalVivero.cerrarScanner(); // Método que cierra el Scanner

	}

}
