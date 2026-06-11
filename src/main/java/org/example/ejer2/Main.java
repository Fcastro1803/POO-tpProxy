package org.example.proxy3;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Usuario admin = new Usuario("Federico (Admin)", List.of(Permiso.ADMIN));
        Usuario basico = new Usuario("Juan (Basico)", List.of(Permiso.BASICO));

        FileAccessReal archivoImportante = new FileAccessReal("/documentos", "informe_confidencial.txt");

        FileAccess proxyAdmin = new ProxyFileAccess(admin, archivoImportante);
        FileAccess proxyBasico = new ProxyFileAccess(basico, archivoImportante);

        // 1. Probamos el acceso del Administrador
        try {
            System.out.println("--- Intento de Admin ---");
            System.out.println("Resultado: " + proxyAdmin.readFile());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 2. Probamos el acceso del Usuario Básico
        try {
            System.out.println("--- Intento de Usuario Básico ---");
            System.out.println(proxyBasico.readFile());
        } catch (SecurityException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}