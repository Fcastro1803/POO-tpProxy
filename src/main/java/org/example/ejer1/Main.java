package org.example.proxy2;

public class Main {
    public static void main(String args[]) {
        PersonaDao dao = new PersonaDao();
        Persona p = dao.personaPorId(1);
        System.out.println(p.nombre());
        for (Telefono telefono : p.telefonos()) {
            System.out.println(telefono);
        }
    }

}
