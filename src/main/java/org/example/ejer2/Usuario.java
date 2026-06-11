package org.example.ejer2;

import java.util.List;

public class Usuario {
    private String name;
    private List<Permiso> permisos;
    public Usuario(String name, List<Permiso> permisos) {
        this.name = name;
        this.permisos = permisos;
    }
    public boolean poseePermiso(Permiso permiso) {
        return permisos.stream().anyMatch(p -> p.equals(permiso));
    }

    public String getName() {
        return name;
    }
}
