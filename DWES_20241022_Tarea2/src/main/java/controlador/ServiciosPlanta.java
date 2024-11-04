package controlador;

import java.util.List;

import conexionBD.ConexionBD;
import dao.PlantaDAO;
import modelo.Planta;

public class ServiciosPlanta {
	
	private ConexionBD conexion;
	private PlantaDAO plantaDao;
	
	public ServiciosPlanta() {
		conexion = ConexionBD.getCon();
		plantaDao = conexion.getPlantaDao();
	}
	
	public List<Planta> mostrarPlantas(){
		return plantaDao.getAll();
	}
	

}
