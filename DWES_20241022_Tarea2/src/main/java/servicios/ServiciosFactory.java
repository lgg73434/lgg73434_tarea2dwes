package servicios;

import servicios.*;


public class ServiciosFactory {
	
	private static ServiciosFactory servicios;
	
	public static ServiciosFactory getServicios() {
		if (servicios == null)
			servicios = new ServiciosFactory();
		return servicios;
	}
	
	public ServiciosPlanta getServiciosPlanta() {
		return new ServiciosPlanta();
	}

	public ServiciosEjemplar getServiciosEjemplar() {
		return new ServiciosEjemplar();
	}
	
	public ServiciosPersona getServiciosPersona() {
		return new ServiciosPersona();
	}
	
	public ServiciosCredenciales getServiciosCredenciales() {
		return new ServiciosCredenciales();
	}
	
	public ServiciosMensaje getServiciosMensaje() {
		return new ServiciosMensaje();
	}
	
	
}
