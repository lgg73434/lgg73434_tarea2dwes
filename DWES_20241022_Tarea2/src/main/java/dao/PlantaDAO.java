package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Planta;

public class PlantaDAO {
	
	private Connection connection;

	public PlantaDAO(Connection connection) {
		this.connection = connection;
	}


	/**
	 * Inserta una nueva planta en la base de datos.
	 * 
	 * @param Planta : planta = objeto de tipo Planta con la información de la planta a insertar.
	 * @return el número de filas afectadas en la base de datos (0 si no se insertó correctamente).
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public int insertarPlanta(Planta planta) {
		int result = 0;
		
		String query = "INSERT INTO plantas (codigo, nombreComun, nombreCientifico) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, planta.getCodigo().toUpperCase());
            ps.setString(2, planta.getNombreComun());
            ps.setString(3, planta.getNombreCientifico());
            
            result = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		return result;
        
	}
	
	
	/**
	 * Actualiza la información de una planta en la base de datos.
	 * 
	 * @param Planta : planta = objeto de tipo Planta con la nueva información a actualizar.
	 * @return el número de filas afectadas en la base de datos (0 si no se actualizó correctamente).
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public int actualizarPlanta(Planta planta) {
		int result = 0;
		
		String query = "UPDATE plantas SET nombreComun = ?, nombreCientifico = ? WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, planta.getNombreComun());
            ps.setString(2, planta.getNombreCientifico());
            ps.setString(3, planta.getCodigo().toUpperCase());
            
            result = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		return result;
	}
	
	
	/**
	 * Obtiene todas las plantas de la base de datos.
	 * 
	 * @return una lista de objetos Planta con los datos de todas las plantas en la base de datos.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public List<Planta> getAll(){
		
		String sql = "SELECT * FROM plantas order by nombreComun ASC";
		List<Planta> resul = new ArrayList<Planta>();
		
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()) {
				Planta planta = new Planta();
				planta.setCodigo(rs.getString("codigo"));
				planta.setNombreComun(rs.getString("nombreComun"));
				planta.setNombrecientifico(rs.getString("nombreCientifico"));

				resul.add(planta);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resul;
	}

	
	/**
	 * Verifica si un código de planta ya está registrado en la base de datos.
	 * 
	 * @param String : codigo = código de la planta a verificar.
	 * @return true si el código ya está registrado, false en caso contrario.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public boolean isCodigoRegistrado(String codigo) {
		 String query = "SELECT codigo FROM plantas WHERE codigo = ?";
		    
		    try (PreparedStatement ps = connection.prepareStatement(query)) {
		        ps.setString(1, codigo.toUpperCase());
		        
		        try (ResultSet rs = ps.executeQuery()) {
		            return rs.next(); // Retorna true si el codigo ya está registrado
		        }
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    return false; 
	}


}
