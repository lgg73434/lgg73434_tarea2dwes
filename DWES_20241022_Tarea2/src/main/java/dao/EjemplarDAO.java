package dao;

import java.sql.Connection;

public class EjemplarDAO {

	private Connection connection;
	
	public EjemplarDAO(Connection connection) {
		this.connection = connection;
	}

}
