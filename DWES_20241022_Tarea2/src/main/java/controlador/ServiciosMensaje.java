package controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import conexionBD.ConexionBD;
import dao.MensajeDAO;
import modelo.Ejemplar;
import modelo.Mensaje;
import modelo.Planta;

public class ServiciosMensaje {
	
	private ConexionBD conexion;
	private MensajeDAO mensajeDao;
	
	public ServiciosMensaje() {
		conexion = ConexionBD.getCon();
		mensajeDao = conexion.getMensajeDao();	
	}
	
	
	public int crearMensaje(Mensaje m) {
		return mensajeDao.insertarMensaje(m);
	}

	
	public ArrayList<Mensaje> getMensajesPorEjemplar(Ejemplar ejemplar) {
		return mensajeDao.getMensajesPorEjemplar(ejemplar);
	}

	
	public ArrayList<Mensaje> getMensajesPorUsuario(Long idUsuario) {
		return mensajeDao.getMensajesPorUsuario(idUsuario);
	}

	
	public ArrayList<Mensaje> getMensajesPorPlanta(Planta planta) {
		return mensajeDao.getMensajesPorPlanta(planta);
	}

	
	public ArrayList<Mensaje> getMensajesFecha(LocalDateTime fechaHora1, LocalDateTime fechaHora2) {
		return mensajeDao.getMensajesPorFecha(fechaHora1, fechaHora2);
	}




}

