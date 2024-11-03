package utilidades;

public class Validar {
	
	public static boolean validarNombre(String nombre) {
		return nombre != null && nombre.matches("^[a-zA-ZÀ-ÿ]+( [a-zA-ZÀ-ÿ]+)*$");
	}
	
	public static boolean validarEmail(String email) {
		return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
		
	}

	public static boolean validarNombreUsuario(String nombreUsuario) {
		return nombreUsuario != null && nombreUsuario.matches("^[a-zA-Z0-9]+$");
	}

	public static boolean validarContrasena(String contrasena) {	
		return contrasena != null && contrasena.length() >= 6 && contrasena.length() <= 10 && !contrasena.contains(" ");
	}

}
