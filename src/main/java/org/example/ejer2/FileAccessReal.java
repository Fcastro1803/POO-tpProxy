package org.example.ejer2;

import java.io.IOException;

public class FileAccessReal implements FileAccess {
    private String ruta;
    private String nombreArchivo;

    public FileAccessReal(String ruta, String nombreArchivo) {
        this.ruta = ruta;
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public String readFile() throws IOException {
        //Se modifico return para prueba de funcionamiento
        return "Se leyo el archivo " + this.nombreArchivo + ", de la ruta " + this.ruta;
        //return Files.readString(Paths.get(this.ruta + "/" + this.nombreArchivo));
    }

    // Getters necesarios para que el Proxy pueda inspeccionar el nombre del archivo
    public String getNombreArchivo() {
        return nombreArchivo;
    }
}