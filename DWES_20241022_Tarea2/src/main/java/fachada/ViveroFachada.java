package fachada;

public class ViveroFachada {
	
	private static ViveroFachada portalVivero;
	
	public static ViveroFachada getPortal() {
		if (portalVivero==null)
			portalVivero=new ViveroFachada();
		return portalVivero;
	}
	
	public void mostrarMenuPrincipal() {
		System.out.println("\nSeleccione una opción:");
		System.out.println("1.  Ver plantas (Modo invitado)");
		System.out.println("2.  Login");
		System.out.println("3.  Salir");
	}
	
	public void mostrarMenuPersonal() {
		System.out.println("\nSeleccione una opción:");
		System.out.println("1.  Gestionar ejemplares");
		System.out.println("2.  Gestionar mensajes");
		System.out.println("3.  Cerrar sesión");
	}
	
	public void mostrarMenuAdministrador(){
		System.out.println("\nSeleccione una opción:");
		System.out.println("1.  Gestionar plantas");
		System.out.println("2.  Gestionar ejemplares");
		System.out.println("3.  Gestionar mensajes");
		System.out.println("4.  Registrar persona");
		System.out.println("5.  Cerrar sesión");
	}
	
	public void mostrarMenuGestionarPlantas() {
		System.out.println("\nSeleccione una opción:");
		System.out.println("1.  Registrar planta");
		System.out.println("2.  Modificar planta");
		System.out.println("3.  Volver atrás");
	}
	
	public void mostrarMenuGestionarEjemplares() {
		System.out.println("\nSeleccione una opción:");
		System.out.println("1.  Registrar un nuevo ejemplar");
		System.out.println("2.  Ver ejemplares de una planta");
		System.out.println("3.  Ver mensajes de seguimiento de un ejemplar");
		System.out.println("4.  Volver atrás");
	}
	
	public void mostrarMenuGestionarMensajes() {
		System.out.println("\nSeleccione una opción:");
		System.out.println("1.  Nuevo mensaje");
		System.out.println("2.  Buscar mensajes por usuario");
		System.out.println("3.  Buscar mensajes por fecha");
		System.out.println("4.  Buscar mensajes por tipo de planta");
		System.out.println("5.  Volver atrás");
	}
	

	

}
