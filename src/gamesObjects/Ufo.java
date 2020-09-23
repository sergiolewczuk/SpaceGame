package gamesObjects;

import graphics.Assets;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ufo extends MovingObject {

    private ArrayList<Vector2D> path;
    private Vector2D currentNode;
    private int index;
    private boolean following;

    private Chronometer fireRate;


    public Ufo(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, ArrayList<Vector2D> path, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.path = path;
        index = 0;
        following = true;

        fireRate = new Chronometer();
        fireRate.run(Constants.UFO_FIRE_RATE);

    }

    private Vector2D pathFollowing() {
        currentNode = path.get(index);
        double distanceToNode = currentNode.subtract(getCenter()).getMagnitude();
        if(distanceToNode < Constants.NODE_RADIUS) {
            index++;
            if(index >= path.size()){
                following = false;
            }
        }
        return seekForce(currentNode);
    }

    private Vector2D seekForce(Vector2D target) {
        // VECTOR DESDE UFO AL OBJETIVO
        Vector2D desiredVelocity = target.subtract(getCenter());
        desiredVelocity = desiredVelocity.normalize().scale(maxVel);

        return desiredVelocity.subtract(velocity);

    }

    @Override
    public void update() {
        Vector2D pathFollowing;
        if(following)
            pathFollowing = pathFollowing();
        else
            pathFollowing = new Vector2D();

        pathFollowing = pathFollowing.scale(1/Constants.UFO_MASS);
        velocity = velocity.add(pathFollowing);
        velocity = velocity.limit(maxVel);
        position = position.add(velocity);

        /*if(position.getX() < 0 || position.getX() > Constants.WIDTH ||
                position.getY() < 0 || position.getY() > Constants.HEIGHT)
            destroy();
        */
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


        // shoot
        if(!fireRate.isRunning()) {
            Vector2D toPlayer = gameState.getPlayer().getCenter().subtract(getCenter());
            toPlayer = toPlayer.normalize();

            // obtener angulo del player
            double currentAngle = toPlayer.getAngle();
            // random multiplicar por PI(que es 180º) menos PI/2 (que es 90º)  --- obtengo un angulo de 0 a 180º con respecto al jugador
            currentAngle += Math.random()*Constants.UFO_ANGLE_RANGE - Constants.UFO_ANGLE_RANGE /2;

            if (toPlayer.getX() < 0) {
                currentAngle = -currentAngle + Math.PI;

                toPlayer = toPlayer.setDirection(currentAngle);

                Laser laser = new Laser(
                        getCenter().add(toPlayer.scale(width)),
                        toPlayer,
                        Constants.LASER_VEL,
                        currentAngle + Math.PI / 2,
                        Assets.lasserRed,
                        gameState,
                        "Ufo"
                );

                gameState.getMovingObjects().add(0, laser);
                fireRate.run(Constants.UFO_FIRE_RATE);
            }
        }


        angle += Constants.UFO_ROTATE;
        collidesWith();

        fireRate.update();

    }

    @Override
    public void destroy(){
        gameState.addScore(Constants.UFO_SCORE);
        super.destroy();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width/2, height/2);
        g2d.drawImage(texture, at, null);
        g.setColor(Color.RED);
    }
}
