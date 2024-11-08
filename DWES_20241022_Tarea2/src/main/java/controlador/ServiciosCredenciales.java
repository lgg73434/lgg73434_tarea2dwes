package controlador;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import conexionBD.ConexionBD;
import dao.CredencialesDAO;
import modelo.Credenciales;


public class ServiciosCredenciales {
	
	private ConexionBD conexion;
	private CredencialesDAO credencialesDao;
	
	public ServiciosCredenciales() {
		conexion = ConexionBD.getCon();
		credencialesDao = conexion.getCredencialesDao();
	}
	
	
	public boolean login(String usuario, String contrasena) {
	    boolean valido = false;
	    Properties propiedades = new Properties();

	    // Primero verificamos en la base de datos
	    if (credencialesDao.login(usuario, contrasena)) {
	        return true; // Credenciales válidas en la base de datos
	    }
	    
	    // Si falla, intentamos verificar en el archivo de propiedades
	    try (InputStream inputStream = ConexionBD.class.getClassLoader().getResourceAsStream("db.properties")) {
	        if (inputStream == null) {
	            System.err.println("Archivo de configuración no encontrado");
	            return false; // Retornamos false si no se encontró el archivo
	        }

	        propiedades.load(inputStream);
	        
	        // Comparamos con las credenciales del archivo de propiedades
	        if (usuario.equalsIgnoreCase(propiedades.getProperty("usuario")) && contrasena.equalsIgnoreCase(propiedades.getProperty("passAdmin"))) {
	            valido = true;
	        }
	        
	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo properties: " + e.getMessage());
	    }

	    return valido;
	}
	
	
	public Long getIdCredenciales(String usuario) {
		Credenciales credenciales = credencialesDao.getCredencialesPorUsuario(usuario);
		if (credenciales != null) {
			return credenciales.getId();
		}
		
		return 0L;
		
	}

	public boolean existeUsuario(String nombreUsuario) {
		if (credencialesDao.isUsuarioRegistrado(nombreUsuario)) {
			return true;
		}
		return false;
	}







}
