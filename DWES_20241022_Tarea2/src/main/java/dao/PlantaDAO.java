package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexionBD.ConexionBD;
import modelo.Planta;

public class PlantaDAO {
	
	private Connection connection;

	public PlantaDAO(Connection connection) {
		this.connection = connection;
	}




	public int insertarPlanta(Planta planta) {
		int result = 0;
		
		String query = "INSERT INTO Planta (codigo, nombreComun, nombreCientifico) VALUES (?, ?, ?)";
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
	
	
	public int actualizarPlanta(Planta planta) {
		int result = 0;
		
		String query = "UPDATE Planta SET nombreComun = ?, nombreCientifico = ? WHERE codigo = ?";
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
	
	
        
	

}
