package gamesObjects;

import graphics.Assets;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Meteor extends MovingObject{

    private Size size;

    public Meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, Size size) {
        super(position, velocity, maxVel, texture, gameState);
        this.size = size;
        this.velocity = velocity.scale(maxVel);
    }

    @Override
    public void update() {
        position = position.add(velocity);


        if(position.getX() > Constants.WIDTH){
            position.setX(-width);
        }
        if(position.getY() > Constants.HEIGHT){
            position.setY(-height);
        }
        if(position.getX() < -width) {
            position.setX(Constants.WIDTH);
        }
        if(position.getY() < -height){
            position.setY(Constants.HEIGHT);
        }

        angle += Constants.DELTAANGLE/2;
    }

    @Override
    public void destroy(){
        gameState.divideMeteor(this);
        super.destroy();
    }

    @Override
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle,width/2, height/2);
        g2d.drawImage(texture, at, null);


    }

    public Size getSize() {
        return size;
    }
}
