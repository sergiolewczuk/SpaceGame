package gamesObjects;

import math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObjects {

    protected BufferedImage texture;
    protected Vector2D position;

    public GameObjects(Vector2D position, BufferedImage texture) {
        this.texture = texture;
        this.position = position;
    }

    public abstract void update();
    public abstract void draw(Graphics g);

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
}
