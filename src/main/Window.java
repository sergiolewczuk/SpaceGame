package main;

import gamesObjects.Constants;
import graphics.Assets;
import input.KeyBoard;
import states.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window extends JFrame implements Runnable {

    private Canvas canvas;
    private Thread thread;

    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    private final int FPS = 60;
    private double TARGETTIME = 1000000000/FPS;
    private double delta = 0;
    private int AVERAGEFPS = FPS;

    private GameState gameState;

    private KeyBoard keyBoard;

    public Window ()
    {

        setTitle("Space game");
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        canvas = new Canvas();
        keyBoard = new KeyBoard();

        canvas.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMaximumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setFocusable(true);




        add(canvas);
        canvas.addKeyListener(keyBoard);


        setVisible(true);
    }

    public static void main(String[] args) {

        new Window().start();



    }


    private void update() {
        keyBoard.update();
        gameState.update();
    }

    private void draw() {

        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }

        g = bs.getDrawGraphics();

        //-----------//
        g.setColor(Color.black);
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);


        gameState.draw(g);

        g.setColor(Color.WHITE);
        g.drawString("FPS "+AVERAGEFPS, 20, 20 );
        g.drawString("+ = +LaserSpeed", 20, 35 );
        g.drawString("- = -LaserSpeed", 20, 50 );
        g.drawString("SPACE = Starter Position", 20, 65 );
        g.drawString("P = Shoot", 20, 80 );
        g.drawString("Arrows = Move", 20, 95 );

        //--------------//
        g.dispose();
        bs.show();

    }

    private void init() {
        Assets.init();
        gameState = new GameState();

    }

    @Override
    public void run() {

        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        int time = 0;

        init();

        while (running) {

            now = System.nanoTime();
            delta += (now - lastTime)/TARGETTIME;
            time += (now - lastTime);
            lastTime = now;

            if (delta >= 1) {
                update();
                draw();

                delta --;
                frames ++;
            }

            if (time >= 1000000000) {

                AVERAGEFPS = frames;
                frames = 0;
                time = 0;

            }



        }

        stop();
    }

    private void start() {
        thread = new Thread(this);
        thread.start();

        running = true;
    }

    private void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
