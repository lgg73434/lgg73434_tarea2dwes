package utilidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validar {
	
	/**
	 * Valida que el nombre cumpla con ciertas condiciones:
	 * - No es nulo.
	 * - Solo contiene letras (incluyendo acentos).
	 * - La longitud es de al menos 3 caracteres y como máximo 50 caracteres.
	 * 
	 * @param String : nombre = nombre a validar.
	 * @return true si el nombre es válido, false en caso contrario.
	 */
	public static boolean validarNombre(String nombre) {
		return nombre != null && nombre.matches("^[a-zA-ZÀ-ÿ]+( [a-zA-ZÀ-ÿ]+)*$") && nombre.length() >=3 && nombre.length()<=50;
	}
	
	
	/**
	 * Valida que el email cumpla con ciertas condiciones:
	 * - No es nulo.
	 * - Cumple con un formato válido de correo electrónico.
	 * - La longitud está entre 5 y 50 caracteres.
	 * 
	 * @param String : email = correo electrónico a validar.
	 * @return true si el email es válido, false en caso contrario.
	 */
	public static boolean validarEmail(String email) {
		return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
				&& email.length() >=5 && email.length()<=50;
	}

	
	/**
	 * Valida que el nombre de usuario cumpla con ciertas condiciones:
	 * - No es nulo.
	 * - Solo contiene letras y números.
	 * - La longitud no excede los 15 caracteres.
	 * 
	 * @param String : nombreUsuario el nombre de usuario a validar.
	 * @return true si el nombre de usuario es válido, false en caso contrario.
	 */
	public static boolean validarNombreUsuario(String nombreUsuario) {
		return nombreUsuario != null && nombreUsuario.matches("^[a-zA-Z0-9]+$") && nombreUsuario.length()<=15;
	}

	
	/**
	 * Valida que la contraseña cumpla con ciertas condiciones:
	 * - Solo contiene letras y números.
	 * - La longitud está entre 6 y 10 caracteres.
	 * 
	 * @param contrasena la contraseña a validar.
	 * @return true si la contraseña es válida, false en caso contrario.
	 */
	public static boolean validarContrasena(String contrasena) {	
		return contrasena.matches("^[a-zA-Z0-9]{6,10}$");
	}

	
	/**
	 * Valida que el código cumpla con ciertas condiciones:
	 * - Solo contiene letras.
	 * - La longitud está entre 1 y 50 caracteres.
	 * 
	 * @param String : codigo = código a validar.
	 * @return true si el código es válido, false en caso contrario.
	 */
	public static boolean validarCodigo(String codigo) {
		return codigo.matches("^[A-Za-z]{1,50}$");
	}
	
	
	/**
	 * Valida que el mensaje cumpla con ciertas condiciones:
	 * - No es nulo ni vacío.
	 * - La longitud es menor o igual a 500 caracteres.
	 * 
	 * @param String : mensaje = mensaje a validar.
	 * @return true si el mensaje es válido, false en caso contrario.
	 */
	public static boolean validarMensaje(String mensaje) { 
	    return mensaje !=null && !mensaje.trim().isEmpty() && mensaje.length() <=500;
	}

	
	/**
	 * Formatea una fecha y hora en el formato "dd/MM/yyyy HH:mm:ss".
	 * 
	 * @param LocalDateTime : fechaHora = fecha y hora a formatear.
	 * @return una cadena de texto con la fecha y hora formateadas.
	 */
	public static String formatoFecha(LocalDateTime fechaHora) {
		 // Definir el formato deseado
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        // Formatear la fecha
        String fechaFormateada = fechaHora.format(formato);
        
        return fechaFormateada;
	}


	/**
	 * Valida y convierte una cadena de texto de fecha en el formato "dd/MM/yyyy" a un objeto LocalDateTime.
	 * La hora se establece a la hora actual.
	 * 
	 * @param String : fecha = fecha a validar y convertir.
	 * @return un objeto LocalDateTime correspondiente a la fecha validada, o null si la fecha no es válida.
	 */
	public static LocalDateTime validarYConvertirFechaFin(String fecha) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            // Intentar parsear la fecha sin hora
            LocalDate fechaLocal = LocalDate.parse(fecha, formato);
            
            // Obtener la hora actual
            LocalTime horaActual = LocalTime.now();
            
            // Convertir la fecha a LocalDateTime con la hora establecida a medianoche (00:00:00)
            return LocalDateTime.of(fechaLocal, horaActual);
            
        } catch (DateTimeParseException e) {
            return null; // Retorna null si la fecha no es válida
        }
	}

	
	/**
	 * Valida y convierte una cadena de texto de fecha en el formato "dd/MM/yyyy" a un objeto LocalDateTime.
	 * La hora se establece a medianoche (00:00:00).
	 * 
	 * @param String : fecha = fecha a validar y convertir.
	 * @return un objeto LocalDateTime correspondiente a la fecha validada, o null si la fecha no es válida.
	 */
	public static LocalDateTime validarYConvertirFechaInicio(String fecha) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            // Intentar parsear la fecha sin hora
            LocalDate fechaLocal = LocalDate.parse(fecha, formato);
            
            // Convertir la fecha a LocalDateTime con la hora establecida a medianoche (00:00:00)
            return fechaLocal.atStartOfDay();
            
        } catch (DateTimeParseException e) {
            return null; // Retorna null si la fecha no es válida
        }
	}
}
