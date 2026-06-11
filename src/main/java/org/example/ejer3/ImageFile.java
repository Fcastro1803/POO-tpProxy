package org.example.ejer3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ImageFile implements Imagen {

    private BufferedImage image;
    private String path;

    public ImageFile(String path) {
        this.path = path;
        this.image = load(path);
    }

    @Override
    public void display() {
        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);
        JOptionPane.showMessageDialog(null, label);
    }

    private BufferedImage load(String path) {
        BufferedImage img = null;
        try {
            System.out.println("DISCO - Cargando imagen de forma física desde: " + path);
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}