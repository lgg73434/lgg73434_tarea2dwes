package conexionBD;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

import dao.*;

public class ConexionBD {
	
	private Connection connection;
	private static ConexionBD conexionBD;
	
	
		private ConexionBD() {
			Properties prop = new Properties();
			MysqlDataSource m = new MysqlDataSource();

			 //try (FileInputStream fis = new FileInputStream("src/main/resources/db.properties")) {
			try (InputStream fis = getClass().getClassLoader().getResourceAsStream("db.properties")) {
				
				if (fis == null) {
	                System.err.println("Archivo de configuración no encontrado en el classpath");
	                return;
	            }
		           
				prop.load(fis);
				
				m.setUrl(prop.getProperty("url"));
				m.setUser(prop.getProperty("user"));
				m.setPassword(prop.getProperty("password"));

				connection = m.getConnection();

			} catch (IOException e) {
				System.out.println("Error al leer las propiedades del archivo properties" + e.getMessage());
			} catch (SQLException e) {
				System.out.println("Error al conectar con la base de datos " + e.getMessage());
			}
		}
		
		
		public static ConexionBD getCon() {
			if (conexionBD == null)
				conexionBD = new ConexionBD();
			return conexionBD;
		}
		
		
		public PlantaDAO getPlantaDao() {
			return new PlantaDAO(connection);
		}
		
		public EjemplarDAO getEjemplarDao() {
			return new EjemplarDAO(connection);
		}
		
		public PersonaDAO getPersonaDao() {
			return new PersonaDAO(connection);
		}
		
		public CredencialesDAO getCredencialesDao() {
			return new CredencialesDAO(connection);
		}
		
		public MensajeDAO getMensajeDao() {
			return new MensajeDAO(connection);
		}
		






		
		



}
