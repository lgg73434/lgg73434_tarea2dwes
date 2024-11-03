package modelo;

public class Credenciales {
	
	private Long idPersona;
	private String usuario;
	private String password;
	

	public Credenciales() {
	}

	public Credenciales(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
	}

	public Credenciales(Long id, String usuario, String password) {
		this.idPersona = id;
		this.usuario = usuario;
		this.password = password;
	}

	public Long getId() {
		return idPersona;
	}

	public void setId(Long id) {
		this.idPersona = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Credenciales [id=" + idPersona + ", usuario=" + usuario + ", password=" + password + "]";
	}

	
	

}
