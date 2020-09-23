package graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Loader {

    public static BufferedImage ImageLoader (String path) {
        try {
            return ImageIO.read(Loader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Font loadFont (String path, int size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
