package conexionBD;

public class SesionActiva {

	private static SesionActiva sesion;
	private String usuario;
	
	public static SesionActiva getSesionActiva() {
		if (sesion == null)
			sesion = new SesionActiva();
		return sesion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
}
