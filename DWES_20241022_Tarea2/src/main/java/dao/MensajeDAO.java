package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.Mensaje;
import java.sql.Timestamp;

public class MensajeDAO {
	
	private Connection connection;

	public MensajeDAO(Connection connection) {
		this.connection = connection;
	}
	
	public int insertarMensaje(Mensaje mensaje) {
		int result = 0;
		
		String query = "INSERT INTO mensajes (fechaHora, mensaje, idPersona, idEjemplar) VALUES (?, ?, ?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
        	
        	ps.setTimestamp(1, Timestamp.valueOf(mensaje.getFechaHora()));
        	ps.setString(2, mensaje.getMensaje()); 
        	ps.setLong(4, mensaje.getIdEjemplar()); 
        	ps.setLong(3, mensaje.getIdPersona()); 
            
            result = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		return result;
        
	}
	

}
