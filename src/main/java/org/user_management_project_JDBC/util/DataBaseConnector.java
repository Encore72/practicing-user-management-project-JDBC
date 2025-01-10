package org.user_management_project_JDBC.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    // Clase que se encarga de establecer la conexión con la base de datos.
public class DataBaseConnector {

    // String indica dónde está ubicada la base de datos (host, puerto y nombre de la base de datos).
    // Incluimos la configuración de zona horaria con el parámetro 'serverTimezone'.
    private static final String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=Europe/Madrid";

    // Nombre de usuario para autenticar la conexión con la base de datos.
    private static final String username = "******";

    // Contraseña asociada al usuario especificado para conectarse a la base de datos.
    private static final String password = "******";

    // Objeto de tipo 'Connection' que es parte de la API JDBC de Java que permite realizar
    // operaciones como ejecutar consultas SQL (SELECT, INSERT, UPDATE, etc.), gestionar
    // transacciones, y cerrar la conexión cuando ya no es necesaria.
    private static Connection connection;


        // Retorna una instancia única de la conexión a la base de datos.
        // Lanza una excepción 'SQLException' si ocurre un error al intentar conectar con la base de datos.
    public static Connection getInstance() throws SQLException {
        if (connection == null) {
                // Verifica si la conexión aún no se ha creado.
                // Esto asegura que solo se cree una única instancia de la conexión (patrón Singleton).
            connection = DriverManager.getConnection(url, username, password);
                // Si 'connection' es null, se establece una nueva conexión a la base de datos.
                // DriverManager.getConnection() toma la URL, el nombre de usuario y la contraseña para conectarse.
                // En caso de un error (como credenciales incorrectas o problemas de red), se lanza una SQLException.
        }
        return connection;
            // retorna la conexión a la base de datos, ya sea una nueva o una existente.
    }
}
