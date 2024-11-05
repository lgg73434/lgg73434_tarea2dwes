package controlador;

import java.util.ArrayList;

import conexionBD.ConexionBD;
import dao.MensajeDAO;
import modelo.Ejemplar;
import modelo.Mensaje;

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

}

