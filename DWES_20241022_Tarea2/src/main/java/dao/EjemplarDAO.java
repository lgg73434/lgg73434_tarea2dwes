package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.Ejemplar;



public class EjemplarDAO {

	private Connection connection;
	
	public EjemplarDAO(Connection connection) {
		this.connection = connection;
	}

	public int registrarEjemplar(String nombre, String idPlanta) {
		int result = 0;
		
		String query = "INSERT INTO ejemplares (nombre, idPlanta) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nombre);
            ps.setString(2, idPlanta);
            
            result = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		return result;
        
	}
}
