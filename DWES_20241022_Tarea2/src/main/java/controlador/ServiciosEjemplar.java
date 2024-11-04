package controlador;

import conexionBD.ConexionBD;
import dao.EjemplarDAO;
import modelo.Ejemplar;
import modelo.Planta;

public class ServiciosEjemplar {
	
	private ConexionBD conexion;
	private EjemplarDAO ejemplarDao;
	
	public ServiciosEjemplar() {
		conexion = ConexionBD.getCon();
		ejemplarDao = conexion.getEjemplarDao();
	}
	
	public Ejemplar crearEjemplar (Planta p) {
		return ejemplarDao.crearEjemplar(p);
	}

}
