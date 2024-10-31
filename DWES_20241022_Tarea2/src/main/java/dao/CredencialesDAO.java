package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.Credenciales;
import modelo.Persona;

public class CredencialesDAO {
	
	private Connection connection;

	public CredencialesDAO(Connection connection) {
		this.connection = connection;
	}
	
	public int registrarCredenciales(Credenciales credenciales) {
		int result = 0;
		
		String query = "INSERT INTO credenciales (usuario, password) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, credenciales.getUsuario());
            ps.setString(2, credenciales.getPassword());
            
            result = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		return result;
        
	}

}
