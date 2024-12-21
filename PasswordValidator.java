// Nombre: Alisson Ibarguen
// Número de ID: 8-1020-2253
// Facultad: FACULTAD DE INGENIERÍA, ARQUITECTURA Y DISEÑO
// Carrera: Lic. INGENIERÍA EN SISTEMAS COMPUTACIONALES
// Curso: Programación de Computadoras II

/*
Enunciado: Crear una aplicación de consola en Java que utilice expresiones
regulares para validar contraseñas de usuarios de manera concurrente. El
programa debe lanzar varios hilos que, de manera independiente, validen
las contraseñas ingresadas por los usuarios. Cada hilo deberá verificar
si una contraseña cumple con ciertos requisitos: longitud mínima de ocho
(8) caracteres, presencia de caracteres especiales, uso de al menos dos
(2) letras mayúsculas, uso de al menos tres (3) letras minúsculas, uso de
al menos un (1) número.*/

// Importamos las librerias necesarias
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

// Clase para validar contraseñas
public class PasswordValidator implements Runnable {
    private final String password;

    // Constructor
    public PasswordValidator(String password) {
        this.password = password;
    }

    // Metodo para validar la contraseña
    @Override
    public void run() {
        // Validar la contraseña
        boolean isValid = isValidPassword(password);
        StringBuilder result = new StringBuilder();
        result.append("Password: ").append(password).append("\n");
        result.append("Resultado de la validación:\n");
        result.append("- Longitud mínima de 8 caracteres: ").append(password.length() >= 8 ? "✔" : "✘").append("\n");
        result.append("- Presencia de caracteres especiales: ").append(Pattern.matches(".*[!@#$%^&*(),.?\":{}|<>].*", password) ? "✔" : "✘").append("\n");
        result.append("- Uso de al menos dos letras mayúsculas: ").append(Pattern.matches(".*[A-Z].*[A-Z].*", password) ? "✔" : "✘").append("\n");
        result.append("- Uso de al menos tres letras minúsculas: ").append(Pattern.matches(".*[a-z].*[a-z].*[a-z].*", password) ? "✔" : "✘").append("\n");
        result.append("- Uso de al menos un número: ").append(Pattern.matches(".*[0-9].*", password) ? "✔" : "✘").append("\n");
        result.append("La contraseña \"").append(password).append("\" es ").append(isValid ? "válida" : "no válida").append(".\n");

        System.out.println(result.toString());

        // Guardar el resultado en un archivo de registro
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("password_log.txt", true))) {
            // Escribir el resultado en el archivo
            writer.write(result.toString());
            // Agregar un salto de línea
            writer.write("\n");
        } catch (IOException e) {
            // Manejar la excepción
            e.printStackTrace();
        }
    }

    // Metodo para validar la contraseña
    private boolean isValidPassword(String password) {
        // Verificar si la contraseña cumple con los requisitos
        if (password.length() < 8) {
            return false;
        }

        // Expresiones regulares para validar la contraseña
        String specialChars = ".*[!@#$%^&*(),.?\":{}|<>].*";
        String upperCase = ".*[A-Z].*[A-Z].*";
        String lowerCase = ".*[a-z].*[a-z].*[a-z].*";
        String digit = ".*[0-9].*";

        return Pattern.matches(specialChars, password) &&
                Pattern.matches(upperCase, password) &&
                Pattern.matches(lowerCase, password) &&
                Pattern.matches(digit, password);
    }
}