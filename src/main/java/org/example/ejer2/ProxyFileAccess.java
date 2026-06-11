package org.example.proxy3;

import java.io.IOException;

public class ProxyFileAccess implements FileAccess {
    private Usuario usuario;
    private FileAccessReal fileAccessReal;

    public ProxyFileAccess(Usuario usuario, FileAccessReal fileAccessReal) {
        this.usuario = usuario;
        this.fileAccessReal = fileAccessReal;
    }

    @Override
    public String readFile() throws IOException {
        String nombre = fileAccessReal.getNombreArchivo();

        // REGLA 1: Empieza con 'i' y NO es ADMIN -> Bloqueado
        if (nombre.startsWith("i") && !usuario.poseePermiso(Permiso.ADMIN)) {
            throw new SecurityException("ERROR DE SEGURIDAD: El usuario " + usuario.getName()
                    + " no tiene permisos ADMIN para leer el archivo importante: " + nombre);
        }

        // REGLA 2: Empieza con 'm' y NO es ADMIN ni INTERMEDIO -> Bloqueado
        if (nombre.startsWith("m") && !usuario.poseePermiso(Permiso.ADMIN) && !usuario.poseePermiso(Permiso.INTERMEDIO)) {
            throw new SecurityException("ERROR DE SEGURIDAD: El usuario " + usuario.getName()
                    + " no tiene permisos suficientes para leer el archivo moderado: " + nombre);
        }

        // Si no entró a ningún bloqueo de seguridad, significa que el acceso está permitido
        return fileAccessReal.readFile();
    }
}