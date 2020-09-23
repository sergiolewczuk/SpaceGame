package graphics;

import math.Vector2D;

import java.awt.*;

public class Text {
    public static void drawText(Graphics g, String text, Vector2D pos, boolean center, Color color, Font font) {
        g.setColor(color);
        g.setFont(font);
        Vector2D posistion = new Vector2D(pos.getX(), pos.getY());

        if (center) {
            FontMetrics fm = g.getFontMetrics();
        }

        g.drawString(text, (int)posistion.getX(), (int)posistion.getY());
    }
}
