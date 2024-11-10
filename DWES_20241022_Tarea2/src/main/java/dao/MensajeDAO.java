package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Ejemplar;
import modelo.Mensaje;
import modelo.Planta;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class MensajeDAO {
	
	private Connection connection;

	public MensajeDAO(Connection connection) {
		this.connection = connection;
	}
	
	
	/**
	 * Inserta un nuevo mensaje en la base de datos.
	 * 
	 * @param Mensaje : mensaje = objeto Mensaje que contiene los detalles del mensaje a insertar.
	 * @return int que indica el resultado de la operación: 1 si la inserción fue exitosa, 
	 * 		   o 0 en caso contrario.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
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

	
	/**
	 * Obtiene una lista de mensajes asociados a un ejemplar específico, ordenados por fecha y hora.
	 * 
	 * @param Ejemplar : ejemplar = objeto Ejemplar cuyo ID se utiliza para filtrar los mensajes.
	 * @return ArrayList<Mensaje> con los mensajes asociados al ejemplar, ordenados por fechaHora, 
	 *         o una lista vacía si no hay mensajes.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
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

	
	/**
	 * Obtiene una lista de mensajes asociados a un usuario específico, ordenados por fecha y hora.
	 * 
	 * @param Long : idUsuario = ID del usuario cuyos mensajes se desean obtener.
	 * @return ArrayList<Mensaje> con los mensajes asociados al usuario, ordenados por fechaHora, 
	 *         o una lista vacía si no hay mensajes.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public ArrayList<Mensaje> getMensajesPorUsuario(Long idUsuario) {
		String sql = "SELECT * FROM mensajes WHERE idPersona = ? ORDER BY fechaHora ASC";
		ArrayList<Mensaje> resul = new ArrayList<Mensaje>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, idUsuario);
			
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

	
	/**
	 * Obtiene una lista de mensajes asociados a una planta específica, ordenados por el nombre del ejemplar y la fecha y hora del mensaje.
	 * 
	 * @param Planta : planta = objeto Planta cuyo código se utiliza para filtrar los mensajes.
	 * @return ArrayList<Mensaje> con los mensajes asociados a la planta, ordenados por el nombre del ejemplar y fechaHora,
	 *         o una lista vacía si no hay mensajes.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public ArrayList<Mensaje> getMensajesPorPlanta(Planta planta) {
		
		String sql = "SELECT * FROM mensajes "
				+ "JOIN ejemplares ON mensajes.idEjemplar = ejemplares.id "
				+ "JOIN plantas ON ejemplares.idPlanta = plantas.codigo "
				+ "WHERE plantas.codigo = ? "
				+ "ORDER BY ejemplares.nombre, mensajes.fechaHora";
		ArrayList<Mensaje> resul = new ArrayList<Mensaje>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, planta.getCodigo());
			
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

	
	/**
	 * Obtiene una lista de mensajes dentro de un rango de fechas y horas especificado.
	 * 
	 * @param LocalDateTime : fechaHora1 = fecha y hora de inicio del rango.
	 * @param LocalDateTime : fechaHora2 = fecha y hora de fin del rango.
	 * @return ArrayList<Mensaje> con los mensajes cuyo campo fechaHora esté dentro del rango especificado,
	 *         ordenados por fechaHora, o una lista vacía si no hay mensajes.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public ArrayList<Mensaje> getMensajesPorFecha(LocalDateTime fechaHora1, LocalDateTime fechaHora2) {
		String sql = "SELECT * FROM mensajes WHERE fechaHora BETWEEN ? AND ?";
		ArrayList<Mensaje> resul = new ArrayList<Mensaje>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, Timestamp.valueOf(fechaHora1));
			ps.setTimestamp(2, Timestamp.valueOf(fechaHora2));
			
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
