package fachada;

public class ViveroFachada {
	
	private static ViveroFachada portalVivero;
	
	public static ViveroFachada getPortal() {
		if (portalVivero==null)
			portalVivero=new ViveroFachada();
		return portalVivero;
	}
	
	public void mostrarMenuPrincipal() {
		System.out.println("Seleccione una opcion:");
		System.out.println("1.  Ver plantas");
		System.out.println("2.  Login");
		System.out.println("3.  Salir");
	}

}
