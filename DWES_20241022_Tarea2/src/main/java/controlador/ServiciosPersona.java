package controlador;

import java.sql.Connection;
import java.sql.SQLException;

import conexionBD.ConexionBD;
import dao.CredencialesDAO;
import dao.PersonaDAO;
import modelo.Credenciales;
import modelo.Persona;

public class ServiciosPersona {

	private ConexionBD conexion;
	private PersonaDAO personaDao;
	private CredencialesDAO credencialesDao;
	
	public ServiciosPersona() {
		conexion = ConexionBD.getCon();
		personaDao = conexion.getPersonaDao();
		credencialesDao = conexion.getCredencialesDao();
	}
	
	

	public boolean isEmailRegistrado(String email) {
		if(personaDao.isEmailRegistrado(email)) {
			return true;
		}
		return false;
	}

	public boolean registrarPersona(String nombre, String email, String nombreUsuario, String contrasena) {
			int idPersona = personaDao.insertarPersona(nombre, email, nombreUsuario, contrasena);
			if(idPersona != -1) {
				boolean todoRegistrado = credencialesDao.insertarCredenciales(idPersona, nombreUsuario, contrasena);
				
				if(todoRegistrado) {
					return true;
				}
			} 
	        return false; 
	       
	}
	

}
