package controlador;

import conexionBD.ConexionBD;
import dao.MensajeDAO;
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

}

