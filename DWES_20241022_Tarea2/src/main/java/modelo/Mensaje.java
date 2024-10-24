package modelo;

import java.time.LocalDate;

public class Mensaje {
	
	private long id;
	private LocalDate fechaHora;
	private String mensaje;
	
	public Mensaje() {
	}

	public Mensaje(long id, LocalDate fechaHora, String mensaje) {
		this.id = id;
		this.fechaHora = fechaHora;
		this.mensaje = mensaje;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", fechaHora=" + fechaHora + ", mensaje=" + mensaje + "]";
	}
	
	

}
