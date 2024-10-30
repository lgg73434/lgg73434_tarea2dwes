package factory;

import dao.*;

public class DaoFactory {
	
	private static DaoFactory df;
	
	public static DaoFactory getDAOs() {
		if (df == null)
			df = new DaoFactory();
		return df;
	}
	
	public PlantaDAO getPlantaDao() {
		return new PlantaDAO();
	}
	
	public EjemplarDAO getEjemplarDao() {
		return new EjemplarDAO();
	}
	
	public PersonaDAO getPersonaDao() {
		return new PersonaDAO();
	}
	
	public CredencialesDAO getCredencialesDao() {
		return new CredencialesDAO();
	}
	
	public MensajeDAO getMensajeDao() {
		return new MensajeDAO();
	}

}
