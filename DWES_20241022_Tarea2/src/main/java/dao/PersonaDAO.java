package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Persona;



public class PersonaDAO {

	private Connection connection;
	
	public PersonaDAO(Connection connection) {
		this.connection = connection;
	}

	
	/**
	 * Verifica si un correo electrónico ya está registrado en la base de datos.
	 * 
	 * @param String : email = dirección de correo electrónico a verificar.
	 * @return true si el correo electrónico está registrado, false en caso contrario.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public boolean isEmailRegistrado(String email) {
	    String query = "SELECT email FROM personas WHERE LOWER(email) = LOWER(?)";
	    
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, email);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            return rs.next(); // Retorna true si el email ya está registrado
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return false; 
	}

	
	/**
	 * Inserta una nueva persona en la base de datos y retorna su ID generado automáticamente.
	 * 
	 * @param String : nombre = nombre de la persona a insertar.
	 * @param String : email = dirección de correo electrónico de la persona, que será almacenada en minúsculas.
	 * @param String : nombreUsuario = nombre de usuario asociado a la persona.
	 * @param String : contrasena = contraseña de la persona.
	 * @return El ID generado para la nueva persona, o -1 si ocurrió un error durante la inserción.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public int insertarPersona(String nombre, String email, String nombreUsuario, String contrasena) {
		String sql = "INSERT INTO personas (nombre, email) VALUES (?, LOWER(?))";
		try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	        ps.setString(1, nombre);
	        ps.setString(2, email);
	        ps.executeUpdate();
	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next())
	        return	rs.getInt(1);
	    } catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	/**
	 * Obtiene los datos de una persona de la base de datos utilizando su ID.
	 * 
	 * @param Long : id = ID de la persona a obtener.
	 * @return un objeto Persona con los datos de la persona correspondiente al ID, o null si no se encuentra.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public Persona obtenerPersonaPorId(Long id) {
		String sqlString = "SELECT id, nombre, email FROM personas WHERE id = ?";
		Persona persona = null;

		try (PreparedStatement pstmt = connection.prepareStatement(sqlString)) {
			pstmt.setLong(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					persona = new Persona();
					persona.setId(rs.getLong("id"));
					persona.setNombre(rs.getString("nombre"));
					persona.setEmail(rs.getString("email"));
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persona;
	}
	


}
