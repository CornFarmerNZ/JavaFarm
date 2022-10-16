/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author tim
 */
public class ImageFactory {

    public BufferedImage getImage(String image) {
        try {
            String input = image.toUpperCase();
            if (input.equals("PIG")) {
                return ImageIO.read(new File("./resources/pig.png"));
            } else if (input.equals("BACKGROUND")) {
                return ImageIO.read(new File("./resources/background.jpg"));
            } else if (input.equals("HEADER")) {
                return ImageIO.read(new File("./resources/straw.png"));
            }
        } catch (IOException ex) {
            Logger.getLogger(ImageFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
