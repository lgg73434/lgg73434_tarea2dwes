package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Credenciales;
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
	
	


}
