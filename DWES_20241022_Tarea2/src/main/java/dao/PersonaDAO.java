package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Credenciales;
import modelo.Persona;


public class PersonaDAO {

	private Connection connection;
	
	public PersonaDAO(Connection connection) {
		this.connection = connection;
	}


}
