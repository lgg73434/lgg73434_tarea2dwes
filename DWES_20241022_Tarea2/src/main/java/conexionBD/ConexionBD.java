package conexionBD;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ConexionBD {
	
	private Connection connection;
	private static ConexionBD conexionBD;
	
	
		private ConexionBD() {
			Properties prop = new Properties();
			MysqlDataSource m = new MysqlDataSource();

			 try (FileInputStream fis = new FileInputStream("src/main/resources/db.properties")) {
		           
				prop.load(fis);

				m.setUrl(prop.getProperty("url"));
				m.setUser(prop.getProperty("user"));
				m.setPassword(prop.getProperty("password"));

				connection = m.getConnection();

			} catch (FileNotFoundException e) {
				System.out.println("Error al acceder al archivo properties " + e.getMessage());
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

		
	    public Connection getConnection() {
	        return connection;
	    }


}
