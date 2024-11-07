package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Ejemplar;
import modelo.Planta;



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
	
	public Ejemplar crearEjemplar(Planta p){
		Ejemplar e=null;
        String insertSQL = "INSERT INTO ejemplares (nombre, idPlanta) VALUES (?, ?)";
        String updateSQL = "UPDATE ejemplares SET nombre = ? WHERE id = ?";

        // Auto-generated keys nos permitirá recuperar el ID generado
        try (PreparedStatement ps = connection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            // Paso 1: Insertamos el ejemplar solo con el id_planta
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getCodigo());
            ps.executeUpdate();

            // Paso 2: Recuperamos el ID generado automáticamente
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long ejemplarId = generatedKeys.getLong(1);  // Obtiene el ID generado
                    String nombreEjemplar = p.getCodigo() + "_" + ejemplarId;

                    // Paso 3: Actualizamos el nombre del ejemplar con el id_planta y el id del ejemplar
                    try (PreparedStatement ups = connection.prepareStatement(updateSQL)) {
                        ups.setString(1, nombreEjemplar);
                        ups.setLong(2, ejemplarId);
                        ups.executeUpdate();
                        
                        e = new Ejemplar (ejemplarId, nombreEjemplar);
                    }
                } 
            }
        } catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
        return e;
	}

	public ArrayList<Ejemplar> listarEjemplaresPlanta(Planta planta) {
		String sql = "SELECT * FROM ejemplares WHERE idPlanta = ?";
		ArrayList<Ejemplar> resul = new ArrayList<Ejemplar>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, planta.getCodigo());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Ejemplar ejemplar = new Ejemplar();
				ejemplar.setId(rs.getLong(1));
				ejemplar.setNombre(rs.getString(2));
				
				resul.add(ejemplar);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resul;
	}

	public ArrayList<Ejemplar> listarEjemplares() {
		String sql = "SELECT * FROM ejemplares ORDER BY nombre";
		ArrayList<Ejemplar> resul = new ArrayList<Ejemplar>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Ejemplar ejemplar = new Ejemplar();
				ejemplar.setId(rs.getLong(1));
				ejemplar.setNombre(rs.getString(2));
				
				resul.add(ejemplar);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return resul;
	}

	public String getNombreEjemplar(Long idEjemplar) {
		String sql = "SELECT nombre FROM ejemplares WHERE id = ?";
		String nombreEjemplar = "";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, idEjemplar);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
	            nombreEjemplar = rs.getString(1); // O también: rs.getString("nombre");
	        }
			
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return nombreEjemplar;
	}
}
