package gamesObjects;

import graphics.Loader;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class MovingObject extends GameObjects {

    protected Vector2D velocity;
    protected AffineTransform at;
    protected double angle;
    protected double maxVel;
    protected int width;
    protected int height;
    protected GameState gameState;

    public MovingObject(Vector2D position, Vector2D velocity, double maxVel, BufferedImage  texture, GameState gameState) {
        super(position, texture);

        this.velocity = velocity;
        this.maxVel = maxVel;
        this.gameState = gameState;
        width = texture.getWidth();
        height = texture.getHeight();
        angle = 0;

    }

    protected void collidesWith(){

        ArrayList<MovingObject> movingObjects = gameState.getMovingObjects();
        for(int i = 0; i < movingObjects.size(); i++){
            MovingObject m = movingObjects.get(i);

            if (m.equals(this))
                continue;

            double distance = m.getCenter().subtract(getCenter()).getMagnitude();
            if(distance < m.width/2 + width/2 && movingObjects.contains(this)){
                objetCollision(m, this);
            }
        }
    }

    private void objetCollision(MovingObject a, MovingObject b){

        if (a instanceof Player && ((Player)a).isSpawning()) {
            return;
        }
        if (b instanceof Player && ((Player)b).isSpawning()) {
            return;
        }

        // que no se maten entre ufos ni rompan meteoros
        if((a instanceof Laser && ((Laser) a).getFrom().equals("Ufo") && b instanceof Meteor) ||
                (a instanceof Meteor && b instanceof Laser && ((Laser) b).getFrom().equals("Ufo")) ||

                (a instanceof Laser && ((Laser) a).getFrom().equals("Ufo") && b instanceof Ufo) ||
                (a instanceof Ufo && b instanceof Laser && ((Laser) b).getFrom().equals("Ufo")))
            return;


        if(!(a instanceof Meteor && b instanceof Meteor) &&     // no chocar entre meteoros
                !(a instanceof Ufo && b instanceof Ufo) &&      // no chocar entre Ufo
                !(a instanceof Ufo && b instanceof Meteor) &&   // no chocar entre ufo y meteoros
                !(a instanceof Meteor && b instanceof Ufo)) {   // no chocar entre ufo y meteoros

            gameState.playExplosion(getCenter());
            a.destroy();
            b.destroy();
        }
    }

    protected void destroy(){
        gameState.getMovingObjects().remove(this);
    }

    protected Vector2D getCenter(){
        return new Vector2D(position.getX() + width/2, position.getY() + height/2);
    }





}
