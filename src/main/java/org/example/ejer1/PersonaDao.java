package org.example.proxy2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonaDao {
    private static boolean tablesCreated = false;

    // Metodo puenta para que el proxy pida conexion
    public Connection obtenerConexionPublica() {
        return obtenerConexion();
    }

    private Connection obtenerConexion() {
        try {
            //Utilice aquí su motor de BD preferido
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1");
            if (!tablesCreated) {
                crearTablas(conn);
                tablesCreated = true;
            }
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    private void crearTablas(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Crear tabla personas
            stmt.execute("CREATE TABLE IF NOT EXISTS personas (" +
                    "id INT PRIMARY KEY, " +
                    "nombre VARCHAR(100)" +
                    ")");

            // Crear tabla telefonos
            stmt.execute("CREATE TABLE IF NOT EXISTS telefonos (" +
                    "id INT PRIMARY KEY, " +
                    "numero VARCHAR(20), " +
                    "idPersona INT, " +
                    "FOREIGN KEY (idPersona) REFERENCES personas(id)" +
                    ")");

            // Insertar datos de prueba
            stmt.execute("INSERT INTO personas VALUES (1, 'Juan Pérez')");
            stmt.execute("INSERT INTO personas VALUES (2, 'María García')");

            stmt.execute("INSERT INTO telefonos VALUES (1, '555-1234', 1)");
            stmt.execute("INSERT INTO telefonos VALUES (2, '555-5678', 1)");
            stmt.execute("INSERT INTO telefonos VALUES (3, '555-9999', 2)");
        }
    }

    // Refactor: Consulta liviana sin JOIN
    public Persona personaPorId(int id) {
        String sql = "select nombre from personas where id = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            String nombrePersona = null;

            if (result.next()) {
                nombrePersona = result.getString(1);
            }

            // Pasamos 'this' (el Dao) para que el Proxy pueda consultar la BD después
            TelefonosProxy proxyTelefonos = new TelefonosProxy(id, this);

            // Retornamos la persona, el constructor recibe el Set (el Proxy)
            return new Persona(id, nombrePersona, proxyTelefonos);

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
