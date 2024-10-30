package dao;

import java.sql.Connection;

public class CredencialesDAO {
	
	private Connection connection;

	public CredencialesDAO(Connection connection) {
		this.connection = connection;
	}

}
