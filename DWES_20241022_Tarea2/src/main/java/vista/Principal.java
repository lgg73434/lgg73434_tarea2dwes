package vista;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import com.mysql.cj.jdbc.MysqlDataSource;

import modelo.Planta;

public class Principal {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.println("Dame el código de una nueva planta.");
		String codigo = in.nextLine().trim().toUpperCase();
		System.out.println("Dame el nombre común de la nueva planta.");
		String nombreComun = in.nextLine().trim().toUpperCase();
		System.out.println("Dame el nombre científico de la nueva planta.");
		String nombreCientifico = in.nextLine().trim().toUpperCase();
		
		Planta planta = new Planta(codigo, nombreComun, nombreCientifico);
		
		Connection con;
		Properties prop = new Properties();
		MysqlDataSource m = new MysqlDataSource();
		
		try {
			FileInputStream fis = new FileInputStream("src/main/resources/db.properties");
			prop.load(fis);
			String url = prop.getProperty("url");
			String usuario = prop.getProperty("usuario");
			String password = prop.getProperty("password");
			m.setUrl(url);
			m.setUser(usuario);
			m.setPassword(password);
			
			con = m.getConnection();
			
			String sql = "INSERT INTO plantas(codigo, nombrecomun, nombreCientifico) VALUES (?, ?, ?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			 // Establecer los parámetros del PreparedStatement
		    ps.setString(1, codigo);          // Reemplaza 'codigo' con tu variable
		    ps.setString(2, nombreComun);      // Reemplaza 'nombreComun' con tu variable
		    ps.setString(3, nombreCientifico); // Reemplaza 'nombreCientifico' con tu variable

		    // Ejecutar la inserción
		    ps.executeUpdate();

		    // Opcionalmente, puedes cerrar el PreparedStatement
		    ps.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		
	}

}
