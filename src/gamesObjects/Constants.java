package gamesObjects;

import java.awt.*;

public class Constants {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 750;

    // player properties
    public static int FIRERATE = 300;
    public static final double DELTAANGLE = 0.1;
    public static final double ACC = 0.2;
    public static final double PLAYER_MAX_VEL = 7.0;
    public static final long FLICKER_TIME = 200;
    public static final long SPAWNING_TIME = 3000;


    // laser properties
    public static final double LASER_VEL = 15.0;

    // meteors properties
    public static final double METEOR_VEL = 2.0;
    public static final int METEOR_SCORE = 20;

    //ufo properties
    public static final int NODE_RADIUS = 160;
    public static final double UFO_MASS = 60;
    public static final double UFO_ROTATE = 0.05;
    public static final int UFO_MAX_VEL = 3;
    public static long UFO_FIRE_RATE = 1000;
    public static double UFO_ANGLE_RANGE = Math.PI / 2;
    public static final int UFO_SCORE = 20;

}
