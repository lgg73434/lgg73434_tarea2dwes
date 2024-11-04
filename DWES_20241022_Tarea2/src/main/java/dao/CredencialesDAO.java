package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			ps.setString(1, credenciales.getUsuario().toLowerCase());
			ps.setString(2, credenciales.getPassword());

			result = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}
	
	public Credenciales getCredencialesPorUsuario(String nombreUsuario) {
		String query = "SELECT id FROM credenciales WHERE usuario = LOWER(?)";
		
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
