package servicios;

import conexionBD.ConexionBD;
import dao.PersonaDAO;

public class ServiciosPersona {

	private ConexionBD conexion;
	private PersonaDAO personaDao;
	
	public ServiciosPersona() {
		conexion = ConexionBD.getCon();
		personaDao = conexion.getPersonaDao();
	}

	public boolean isEmailRegistrado(String email) {
		if(personaDao.isEmailRegistrado(email)) {
			return true;
		}
		return false;
	}
	

}
