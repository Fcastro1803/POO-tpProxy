package org.example.ejer1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

// El Proxy implementa o hereda del "Sujeto" (Set/HashSet)
public class TelefonosProxy extends HashSet<Telefono> {
    private int idPersona;
    private PersonaDao personaDao;

    public TelefonosProxy(int idPersona, PersonaDao personaDao) {
        this.idPersona = idPersona;
        this.personaDao = personaDao;
    }

    // Metodo privado que se ejecuta cunado se lo requiere
    private void cargarTelefonosSiEsNecesario() {
        System.out.println("PROXY - Carga de teléfonos para ID: " + idPersona);
        String sql = "select numero from telefonos where idPersona = ?";

        // Le pedimos la conexión al DAO, pero sólo para esta consulta específica
        try (Connection conn = personaDao.obtenerConexionPublica();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idPersona);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    // Nos agregamos los teléfonos a nosotros mismos (al HashSet super)
                    super.add(new Telefono(result.getString(1)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en el Proxy al cargar teléfonos", e);
        }
    }

    // Interceptamos los métodos que Persona o cualquier cliente usaría para leer el Set
    @Override
    public int size() {
        cargarTelefonosSiEsNecesario();
        return super.size();
    }

    @Override
    public Object[] toArray() {
        cargarTelefonosSiEsNecesario(); // <-- Este es el que gatilla Persona#telefonos()
        return super.toArray();
    }

}