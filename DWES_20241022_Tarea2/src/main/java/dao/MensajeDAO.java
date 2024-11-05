package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Ejemplar;
import modelo.Mensaje;
import modelo.Planta;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

	public ArrayList<Mensaje> getMensajesPorEjemplar(Ejemplar ejemplar) {
		String sql = "SELECT * FROM mensajes WHERE idEjemplar = ? ORDER BY fechaHora ASC";
		ArrayList<Mensaje> resul = new ArrayList<Mensaje>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, ejemplar.getId());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Mensaje mensaje = new Mensaje();
				mensaje.setId(rs.getLong(1));
				mensaje.setFechaHora(rs.getTimestamp(2).toLocalDateTime());
				mensaje.setMensaje(rs.getString(3));
				mensaje.setIdPersona(rs.getLong(4));
				mensaje.setIdEjemplar(rs.getLong(5));

				resul.add(mensaje);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resul;
		
	}
	

}
