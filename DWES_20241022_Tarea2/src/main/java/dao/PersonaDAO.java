package dao;

import java.sql.Connection;

public class PersonaDAO {

	private Connection connection;
	
	public PersonaDAO(Connection connection) {
		this.connection = connection;
	}

}
