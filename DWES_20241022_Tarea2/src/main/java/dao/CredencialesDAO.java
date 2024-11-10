package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Credenciales;


public class CredencialesDAO {

	private Connection connection;

	public CredencialesDAO(Connection connection) {
		this.connection = connection;
	}

	
	/**
	 * Registra las credenciales de un usuario en la base de datos.
	 * 
	 * @param Credenciales : credenciales = objeto que contiene el usuario y contraseña a registrar
	 * @return un valor entero que indica el resultado de la operación: 1 si la inserción fue exitosa, 
	 *         0 en caso contrario.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public int registrarCredenciales(Credenciales credenciales) {
		int result = 0;

		String query = "INSERT INTO credenciales (usuario, password) VALUES (?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, credenciales.getUsuario().toLowerCase());
			ps.setString(2, credenciales.getPassword());

			result = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}
	
	
	/**
	 * Obtiene las credenciales de un usuario a partir de su nombre de usuario.
	 * 
	 * @param String : nombreUsuario = el nombre de usuario para buscar las credenciales en la base de datos
	 * @return un objeto Credenciales que contiene los datos del usuario, 
	 * 		   o null si no se encuentra el usuario en la base de datos.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public Credenciales getCredencialesPorUsuario(String nombreUsuario) {
		String query = "SELECT * FROM credenciales WHERE usuario = LOWER(?)";
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, nombreUsuario);
	        try (ResultSet rs = ps.executeQuery()) {
	        	
	            if (rs.next()) {
	            	Credenciales credenciales = new Credenciales(rs.getLong("id"), rs.getString("usuario"), rs.getString("password"));
	                return credenciales;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}

	
	/**
	 * Verifica si las credenciales proporcionadas son correctas para iniciar sesión.
	 * 
	 * @param String : usuario = nombre de usuario a verificar en la base de datos.
	 * @param String : contrasena = contraseña a comparar con la almacenada en la base de datos.
	 * @return true si las credenciales coinciden con las almacenadas, false en caso contrario.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public boolean login(String usuario, String contrasena) {
		String query = "SELECT password FROM credenciales WHERE usuario = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, usuario);
			
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String pass = rs.getString("password");
				return pass.equals(contrasena);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false; 
	}

	
	/**
	 * Verifica si un usuario está registrado en la base de datos.
	 * 
	 * @param String : nombreUsuario = nombre de usuario a verificar en la base de datos.
	 * @return true si el usuario está registrado, false en caso contrario.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public boolean isUsuarioRegistrado(String nombreUsuario) {
		String query = "SELECT usuario FROM credenciales WHERE LOWER(usuario) = LOWER(?)";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, nombreUsuario);

	        try (ResultSet rs = ps.executeQuery()) {
	            return rs.next(); // Si hay resultados, el usuario está registrado
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return false;
	}

	
	/**
	 * Inserta nuevas credenciales para un usuario en la base de datos.
	 * 
	 * @param int : idPersona = ID de la persona a la que se asociarán las credenciales.
	 * @param String : nombreUsuario = nombre de usuario a insertar.
	 * @param String : contrasena = contraseña a insertar.
	 * @return true si la inserción fue exitosa, false en caso contrario.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public boolean insertarCredenciales(int idPersona, String nombreUsuario, String contrasena) {
		 String sql = "INSERT INTO credenciales (id, usuario, password) VALUES (?, ?, ?)";
		 
		    try (PreparedStatement ps = connection.prepareStatement(sql)) {
		        ps.setInt(1, idPersona);
		        ps.setString(2, nombreUsuario);
		        ps.setString(3, contrasena);
		        
		        return ps.executeUpdate() > 0;
		        
		    } catch (SQLException e) {
				e.printStackTrace();
			}
		 return false;
	}

}
