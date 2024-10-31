package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.Credenciales;
import modelo.Persona;


public class PersonaDAO {

	private Connection connection;
	
	public PersonaDAO(Connection connection) {
		this.connection = connection;
	}

	
	public int registrarPersona(Persona persona, Credenciales credenciales) {
		int result = 0;
		
		String query = "INSERT INTO personas (nombre, email, credenciales) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getEmail());
            ps.setLong(3, credenciales.getId());
            
            result = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		return result;
        
	}
}
