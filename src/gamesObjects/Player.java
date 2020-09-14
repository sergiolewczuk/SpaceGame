package gamesObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import input.KeyBoard;
import math.Vector2D;
import states.GameState;

public class Player extends MovingObject {

    private Vector2D heading;
    private Vector2D acceleration;

    private boolean accelerating = false;
    private Chronometer fireRate;

    public Player(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        heading = new Vector2D(0,1);
        acceleration = new Vector2D();
        fireRate = new Chronometer();
    }

    @Override
    public void update() {

        if(KeyBoard.SHOOT_SPEED_ADD && (Constants.FIRERATE > 50))
            Constants.FIRERATE -= 5;
        if(KeyBoard.SHOOT_SPEED_DELETE && (Constants.FIRERATE < 400))
            Constants.FIRERATE += 5;


        if(KeyBoard.SHOOT && !fireRate.isRunning()){

            gameState.getMovingObjects().add(
                    0,
                    new Laser(
                    getCenter().add(heading.scale(width)),
                    heading,
                    Constants.LASER_VEL,
                    angle,
                    Assets.lasserBlue,
                    gameState
            ));

            fireRate.run(Constants.FIRERATE);
        }

        if(KeyBoard.RIGHT)
            angle += Constants.DELTAANGLE;
        if(KeyBoard.LEFT)
            angle -= Constants.DELTAANGLE;

        if(KeyBoard.UP) {
            acceleration = heading.scale(Constants.ACC);
            accelerating = true;
        } else {
            if (velocity.getMagnitude() != 0)
                acceleration = (velocity.scale(-1).normalize()).scale(Constants.ACC/2);

            accelerating = false;
        }

        velocity = velocity.add(acceleration);

        velocity = velocity.limit(maxVel);

        heading = heading.setDirection(angle - Math.PI/2);

        position = position.add(velocity);



        if(position.getX() > Constants.WIDTH){
            position.setX(0);
        }
        if(position.getY() > Constants.HEIGHT){
            position.setY(0);
        }
        if(position.getX() < 0) {
            position.setX(Constants.WIDTH);
        }
        if(position.getY() < 0){
            position.setY(Constants.HEIGHT);
        }

        fireRate.update();

        collidesWith();

    }

    @Override
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + width/2 + 5,
                position.getY() + height/2 + 10);
        AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX() + 5,
                position.getY() + height/2 + 10);

        at1.rotate(angle, -5, -10);
        at2.rotate(angle, width/2 -5, -10);

        if (accelerating) {
            g2d.drawImage(Assets.speed, at1, null);
            g2d.drawImage(Assets.speed, at2, null);
        }



        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle,width/2, height/2);
        g2d.drawImage(texture, at, null);

    }



}
