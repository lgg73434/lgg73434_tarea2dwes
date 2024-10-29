package modelo;

import java.time.LocalDate;

public class Mensaje {
	
	private Long id;
	private LocalDate fechaHora;
	private String mensaje;
	private Long idPersona;
	private Long idEjemplar;
	
	public Mensaje() {
	}

	public Mensaje(Long id, LocalDate fechaHora, String mensaje, Long idPersona, Long idEjemplar) {
		this.id = id;
		this.fechaHora = fechaHora;
		this.mensaje = mensaje;
		this.idPersona = idPersona;
		this.idEjemplar = idEjemplar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDate fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Long getIdEjemplar() {
		return idEjemplar;
	}

	public void setIdEjemplar(Long idEjemplar) {
		this.idEjemplar = idEjemplar;
	}

	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", fechaHora=" + fechaHora + ", mensaje=" + mensaje + ", idPersona=" + idPersona
				+ ", idEjemplar=" + idEjemplar + "]";
	}

	
	
	
	

}
