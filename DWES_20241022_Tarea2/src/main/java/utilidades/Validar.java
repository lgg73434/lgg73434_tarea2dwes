package utilidades;

public class Validar {
	
	public static boolean validarNombre(String nombre) {
		return nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
	}
	
	public static boolean validarEmail(String email) {
		return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
		
	}

	public static boolean validarNombreUsuario(String nombreUsuario) {
		return nombreUsuario.matches("^[a-zA-Z0-9]+$");
	}

}
