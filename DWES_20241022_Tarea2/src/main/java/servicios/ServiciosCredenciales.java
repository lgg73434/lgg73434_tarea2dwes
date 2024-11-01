package servicios;

import java.io.FileNotFoundException;
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
		if (!credencialesDao.login(usuario, contrasena)) {
			
			
			try (InputStream inputStream = ConexionBD.class.getClassLoader().getResourceAsStream("db.properties")) {
				if (inputStream == null) {
					System.err.println("Archivo de configuración no encontrado");
				}
				
				propiedades.load(inputStream);
				
				if (usuario.equals(propiedades.getProperty("usuario")) && contrasena.equals(propiedades.getProperty("passAdmin"))) {
					valido = true;
				}
				
			} catch (FileNotFoundException e) {
				System.err.println("Error al acceder al fichero properties " + e.getMessage());
			} catch (IOException e) {
				System.err.println("Error al leer usuario y contraseña del fichero properties" + e.getMessage());
			}
		}else {
			valido = true;
		}
		return valido;
	}
	
	public boolean registrarCredenciales(String usuario, String contrasena) {
		Credenciales credenciales = new Credenciales(usuario, contrasena);
		if(credencialesDao.registrarCredenciales(credenciales) == 1) {
			return true;
		}
		return false;
		
	}
	
	public Long getIdCredenciales(String usuario) {
		Credenciales credenciales = credencialesDao.getCredencialesPorUsuario(usuario);
		if (credenciales != null) {
			return credenciales.getId();
		}
		
		return 0L;
		
	}







}
