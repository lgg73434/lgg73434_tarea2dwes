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
