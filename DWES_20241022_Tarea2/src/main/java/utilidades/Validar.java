package utilidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
	
	public static boolean validarMensaje(String mensaje) { 
	    return mensaje !=null && !mensaje.trim().isEmpty() && mensaje.length() <=500;
	}

	public static String formatoFecha(LocalDateTime fechaHora) {
		 // Definir el formato deseado
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        // Formatear la fecha
        String fechaFormateada = fechaHora.format(formato);
        
        return fechaFormateada;
	}


	public static LocalDateTime validarYConvertirFechaFin(String fecha) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            // Intentar parsear la fecha sin hora
            LocalDate fechaLocal = LocalDate.parse(fecha, formato);
            
            // Obtener la hora actual y restarle 1 hora
            LocalTime horaActual = LocalTime.now().minusMinutes(3);
            
            // Convertir la fecha a LocalDateTime con la hora establecida a medianoche (00:00:00)
            return LocalDateTime.of(fechaLocal, horaActual);
            
        } catch (DateTimeParseException e) {
            return null; // Retorna null si la fecha no es válida
        }
	}

	
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
