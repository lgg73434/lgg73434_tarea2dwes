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

	
	public boolean existeCodigo(String codigo) {
		return plantaDao.isCodigoRegistrado(codigo); 
	}

	
	public int registrarPlanta(Planta p) {
		return plantaDao.insertarPlanta(p);
	}
	
	
	public int actualizarPlanta(Planta p) {
		return plantaDao.actualizarPlanta(p);
	}
	

}
