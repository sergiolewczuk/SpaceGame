package graphics;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage player;

    // effects
    public static BufferedImage speed;

    // lassers
    public static BufferedImage lasserBlue;
    public static BufferedImage lasserRed;
    public static BufferedImage lasserGreen;

    public static void init(){

        player = Loader.ImageLoader("/ships/player.png");
        speed = Loader.ImageLoader("/effects/fire08.png");

        lasserBlue =  Loader.ImageLoader("/lassers/laserBlue.png");
        lasserGreen =  Loader.ImageLoader("/lassers/laserGreen.png");
        lasserRed =  Loader.ImageLoader("/lassers/laserRed.png");

    }

}
