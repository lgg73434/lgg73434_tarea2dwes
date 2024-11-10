package principal;

import vista.ViveroFachada;


public class Principal {

	public static void main(String[] args) {

		ViveroFachada portalVivero = ViveroFachada.getPortal();
		
		portalVivero.iniciarPrograma();
				
        portalVivero.cerrarScanner();

	}

}
