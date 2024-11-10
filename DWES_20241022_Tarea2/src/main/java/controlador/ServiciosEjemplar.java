package controlador;

import java.util.ArrayList;

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

	
	public ArrayList<Ejemplar> mostrarEjemplaresPlanta(Planta planta) {
		return ejemplarDao.listarEjemplaresPlanta(planta);
		
	}

	
	public ArrayList<Ejemplar> mostrarEjemplares() {
		return ejemplarDao.listarEjemplares();
	}

	
	public String getEjemplarPorId(Long idEjemplar) {
		return ejemplarDao.getNombreEjemplar(idEjemplar);
	}



}
