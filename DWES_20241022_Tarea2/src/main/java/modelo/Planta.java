package modelo;

public class Planta {
	
	private String codigo;
	private String nombreComun;
	private String nombreCientifico;
	
	public Planta() {
	}

	public Planta(String codigo, String nombreComun, String nombrecientifico) {
		this.codigo = codigo;
		this.nombreComun = nombreComun;
		this.nombreCientifico = nombrecientifico;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreComun() {
		return nombreComun;
	}

	public void setNombreComun(String nombreComun) {
		this.nombreComun = nombreComun;
	}

	public String getNombreCientifico() {
		return nombreCientifico;
	}

	public void setNombrecientifico(String nombrecientifico) {
		this.nombreCientifico = nombrecientifico;
	}

	@Override
	public String toString() {
		return "Planta [codigo=" + codigo + ", nombreComun=" + nombreComun + ", nombrecientifico=" + nombreCientifico
				+ "]";
	}
	
	
	
	

}
