package data;

import interfaces.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class ImageLoaderImpl implements ImageLoader {

    @Override
    public BufferedImage loadImageInPreferredSize(String path, int width, int length) {
        BufferedImage image = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(path).getFile());
            image = ImageIO.read(file);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        image = resizeBufferedImage(image,width,length);
        return image;
    }

    private BufferedImage resizeBufferedImage(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
