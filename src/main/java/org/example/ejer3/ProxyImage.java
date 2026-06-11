package org.example.ejer3;

public class ProxyImage implements Imagen {

    private String path;
    private ImageFile imageFileReal;

    public ProxyImage(String path) {
        this.path = path;
    }

    @Override
    public void display() {
        if (imageFileReal == null) {
            System.out.println("PROXY - Primera vez detectada. Inicializando y guardando en caché...");
            imageFileReal = new ImageFile(path);
        } else {
            System.out.println("PROXY - Imagen recuperada desde la caché en memoria (Evitamos el disco!)");
        }
        imageFileReal.display();
    }
}