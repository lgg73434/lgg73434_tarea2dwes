package controlador;

public class SesionActiva {

	private String usuario;
	
	public SesionActiva(String usu) {
		this.usuario = usu;
	}


	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	@Override
	public String toString() {
		return "SesionActiva [usuario=" + usuario + "]";
	}
	
	
	
}
