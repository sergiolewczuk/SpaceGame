package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.concurrent.ThreadLocalRandom;

public class Assets {

    public static BufferedImage player;

    // effects
    public static BufferedImage speed;

    // lassers
    public static BufferedImage lasserBlue;
    public static BufferedImage lasserRed;
    public static BufferedImage lasserGreen;

    //meteors
    public static BufferedImage[] bigs = new BufferedImage[4];
    public static BufferedImage[] mids = new BufferedImage[2];
    public static BufferedImage[] smalls = new BufferedImage[2];
    public static BufferedImage[] tinies = new BufferedImage[2];

    //explosions
    public static BufferedImage[] exp = new BufferedImage[8];

    //ufo
    public static BufferedImage[] ufo = new BufferedImage[4];

    //numbers
    public static BufferedImage[] numbers = new BufferedImage[11];

    //lifes
    public static BufferedImage life;

    //fonts
    public static Font fontBig;
    public static Font fontMed;


    public static void init(){

        player = Loader.ImageLoader("/ships/player.png");
        speed = Loader.ImageLoader("/effects/fire08.png");

        lasserBlue =  Loader.ImageLoader("/lassers/laserBlue.png");
        lasserGreen =  Loader.ImageLoader("/lassers/laserGreen.png");
        lasserRed =  Loader.ImageLoader("/lassers/laserRed.png");

        for(int i = 0; i < bigs.length; i++){
            bigs[i] = Loader.ImageLoader("/meteors/big"+(i+1)+".png");
        }
        for(int i = 0; i < mids.length; i++){
            mids[i] = Loader.ImageLoader("/meteors/mid"+(i+1)+".png");
        }
        for(int i = 0; i < smalls.length; i++){
            smalls[i] = Loader.ImageLoader("/meteors/small"+(i+1)+".png");
        }
        for(int i = 0; i < tinies.length; i++){
            tinies[i] = Loader.ImageLoader("/meteors/tiny"+(i+1)+".png");
        }
        for (int i = 0; i < exp.length; i++){
            exp[i] = Loader.ImageLoader("/explosion/"+i+".png");
        }
        for (int i = 0; i < ufo.length; i++) {
            ufo[i] = Loader.ImageLoader("/enemies/ufo"+i+".png");
        }
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Loader.ImageLoader("/numbers/"+i+".png");
        }

        life = Loader.ImageLoader("/others/life.png");

        fontBig = Loader.loadFont("/fonts/kenvector_future.ttf", 42);
        fontMed = Loader.loadFont("/fonts/kenvector_future.ttf", 20);

    }

}
