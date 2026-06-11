package org.example.ejer3;

public class ImageGalery {
    public static void main(String[] args) {
        Imagen image1 = new ProxyImage("src/main/resources/image1.jpeg");

        System.out.println("--- Intento de visualización 1 ---");
        image1.display();

        System.out.println("--- Intento de visualización 2 ---");
        image1.display();

        System.out.println("--- Intento de visualización 3 ---");
        image1.display();
    }
}