package utilidades;

public class Validar {
	
	public static boolean validarNombre(String nombre) {
		return nombre != null && nombre.matches("^[a-zA-ZÀ-ÿ]+( [a-zA-ZÀ-ÿ]+)*$") && nombre.length() >=3 && nombre.length()<=50;
	}
	
	public static boolean validarEmail(String email) {
		return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
				&& email.length() >=5 && email.length()<=50;
		
	}

	public static boolean validarNombreUsuario(String nombreUsuario) {
		return nombreUsuario != null && nombreUsuario.matches("^[a-zA-Z0-9]+$") && nombreUsuario.length()<=15;
	}

	public static boolean validarContrasena(String contrasena) {	
		return contrasena != null && contrasena.length() >= 6 && contrasena.length() <= 10 && !contrasena.contains(" ");
	}

	public static boolean validarCodigo(String codigo) {
		return codigo.matches("^[A-Za-z]{1,50}$");
	}

}
