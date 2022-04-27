/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste.projeto;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class WaterMark {

    public static final String DEFAULT_FORMAT = "jpg";
    public static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;
    public static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 18);

    public String makeWaterMark(String fileName, String ctx)
            throws Exception {
        try {
            String dest = execute(ctx + "/" + fileName, "dest", "Water", DEFAULT_COLOR, DEFAULT_FONT);
            return dest.substring(ctx.length());
        } catch (Exception ex) {
            return fileName;
        }
    }

    public String execute(String src, String dest, String text,
            Color color, Font font) throws Exception {
        BufferedImage srcImage = ImageIO.read(new File(src));

        int width = srcImage.getWidth(null);
        int height = srcImage.getHeight(null);
        BufferedImage destImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = destImage.getGraphics();

        g.drawImage(srcImage, 0, 0, width, height, null);
        g.setColor(color);
        g.setFont(font);
        g.fillRect(0, 0, 50, 50);
        g.drawString(text, width / 5, height - 10);
        g.dispose();

        ImageIO.write(destImage, DEFAULT_FORMAT, new File("C:\\Users\\Jandisson\\Pictures\\watermarkedImage.jpg"));
        return dest;
    }
}
