package gamesObjects;

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


    public Ufo(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, ArrayList<Vector2D> path, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.path = path;
        index = 0;
        following = true;
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

        if(position.getX() < 0 || position.getX() > Constants.WIDTH ||
                position.getY() < 0 || position.getY() > Constants.HEIGHT)
            destroy();

        angle += Constants.UFO_ROTATE;
        collidesWith();

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width/2, height/2);
        g2d.drawImage(texture, at, null);
        g.setColor(Color.RED);

        for (int i = 0; i < path.size(); i++) {
            g.drawRect((int)path.get(i).getX(), (int)path.get(i).getY(), 5, 5);
        }

    }
}
