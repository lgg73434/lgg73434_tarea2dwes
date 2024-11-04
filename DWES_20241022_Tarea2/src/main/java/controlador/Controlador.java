package controlador;

import controlador.*;


public class Controlador {
	
	private static Controlador servicios;
	
	public static Controlador getServicios() {
		if (servicios == null)
			servicios = new Controlador();
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
