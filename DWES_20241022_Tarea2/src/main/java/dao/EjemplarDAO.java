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

	
	/**
	 * Registra un nuevo ejemplar en la base de datos.
	 * 
	 * @param String : nombre = nombre del ejemplar a insertar.
	 * @param String : idPlanta = identificador de la planta asociada al ejemplar.
	 * @return int que indica el resultado de la operación: 1 si la inserción fue exitosa, 0 en caso contrario.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
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
	
	
	/**
	 * Crea un nuevo ejemplar en la base de datos basado en una planta dada, asignando un nombre único 
	 * generado automáticamente que combina el código de la planta con el ID generado del ejemplar.
	 * 
	 * @param Planta : p = objeto Planta con la información necesaria para crear el ejemplar.
	 * @return Ejemplar con el ID y nombre asignados si la creación fue exitosa, 
	 * 		   o null en caso contrario.
	 * @throws SQLException si ocurre un error durante la ejecución de las consultas SQL.
	 */
	public Ejemplar crearEjemplar(Planta p){
		Ejemplar e=null;
        String insertSQL = "INSERT INTO ejemplares (nombre, idPlanta) VALUES (?, ?)";
        String updateSQL = "UPDATE ejemplares SET nombre = ? WHERE id = ?";

        // Auto-generated keys permite recuperar el ID generado
        try (PreparedStatement ps = connection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            // Paso 1: Insertar el ejemplar solo con el id_planta
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getCodigo());
            ps.executeUpdate();

            // Paso 2: Recuperar el ID generado automáticamente
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long ejemplarId = generatedKeys.getLong(1);  // Obtiene el ID generado
                    String nombreEjemplar = p.getCodigo() + "_" + ejemplarId;

                    // Paso 3: Actualizar el nombre del ejemplar con el id_planta y el id del ejemplar
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
	
	
	/**
	 * Lista todos los ejemplares asociados a una planta específica en la base de datos.
	 * 
	 * @param Planta : planta = objeto Planta cuyo código se usará para buscar sus ejemplares.
	 * @return ArrayList<Ejemplar> con los ejemplares encontrados, 
	 * 		   o una lista vacía si no hay resultados.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
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

	
	/**
	 * Lista todos los ejemplares en la base de datos, ordenados por nombre.
	 * 
	 * @return ArrayList<Ejemplar> con todos los ejemplares registrados, 
	 * 		   o una lista vacía si no hay resultados.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
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

	
	/**
	 * Obtiene el nombre de un ejemplar a partir de su ID.
	 * 
	 * @param Long : idEjemplar = ID del ejemplar del que queremos obtener su nombre.
	 * @return String con el nombre del ejemplar, 
	 * 		   o una cadena vacía si no se encuentra el ejemplar.
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
	 */
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
