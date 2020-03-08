package interfaces;

import java.awt.image.BufferedImage;

public interface ImageLoader {
    BufferedImage loadImageInPreferredSize(String path, int width, int length);
}
