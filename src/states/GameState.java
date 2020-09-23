package states;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import gamesObjects.*;
import graphics.Animation;
import graphics.Assets;
import input.KeyBoard;
import math.Vector2D;

public class GameState {

    private Player player;
    private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();
    private ArrayList<Animation> explosions = new ArrayList<Animation>();

    private int score = 0;
    private int lives = 3;

    public int meteors;

    public GameState() {

        player = new Player(new Vector2D(Constants.WIDTH/2 - Assets.player.getWidth()/2,
                Constants.HEIGHT/2 - Assets.player.getHeight()/2),new Vector2D(), Constants.PLAYER_MAX_VEL, Assets.player, this);
        movingObjects.add(player);

        meteors = 1;
        startWave();
    }

    public void addScore(int value) {
        score += value;
    }

    private void startWave(){
        double x, y;
        for(int i = 0; i < meteors; i++){
            x = i % 2 == 0 ? Math.random()*Constants.WIDTH : 0;
            // SI X ES PAR, OSEA SI EL RESULTADO ES 0, SI ES VERDADERO X VA A SER IGUAL A UN NUMERO ALEATORIO ENTRE 0 Y EL ANCHO DE LA VENTANA
            // DE LO CONTRARIO VA A SER 0, EN CASO DE QUE SEA IMPAR

            y = i % 2 == 0 ? 0 : Math.random()*Constants.HEIGHT;

            BufferedImage texture = Assets.bigs[(int) Math.random()*Assets.bigs.length];

            movingObjects.add(new Meteor(
                    new Vector2D(x,y),
                    new Vector2D(0,1).setDirection(Math.random()*Math.PI*2),
                    Constants.METEOR_VEL*Math.random()+1,
                    texture,
                    this,
                    Size.BIG
            ));
        }
        meteors++;
        spawnUfo();
        spawnUfo();
        spawnUfo();
        spawnUfo();
    }

    private void spawnUfo() {
        int rand = (int) (Math.random()*2);
        double x = rand == 0 ? (Math.random()*Constants.WIDTH) : 0;
        double y = rand == 0 ? 0 : (Math.random()*Constants.HEIGHT);

        ArrayList<Vector2D> path = new ArrayList<Vector2D>();

        double posX, posY;
        // primer punto random superior izquierdo
        posX = Math.random()*Constants.WIDTH/2;
        posY = Math.random()*Constants.HEIGHT/2;
        path.add(new Vector2D(posX, posY));
        // segundo punto random superior derecho
        posX = Math.random()*(Constants.WIDTH/2) + Constants.WIDTH/2;
        posY = Math.random()*Constants.HEIGHT/2;
        path.add(new Vector2D(posX, posY));
        // tercer punto random inferior izquierdo
        posX = Math.random()*Constants.WIDTH/2;
        posY = Math.random()*(Constants.HEIGHT/2) + Constants.HEIGHT/2;
        path.add(new Vector2D(posX, posY));
        // cuarto punto random inferior derecho
        posX = Math.random()*(Constants.WIDTH/2) + Constants.WIDTH/2;
        posY = Math.random()*(Constants.HEIGHT/2) + Constants.HEIGHT/2;
        path.add(new Vector2D(posX, posY));

        movingObjects.add(new Ufo(
                new Vector2D(x, y),
                new Vector2D(),
                Constants.UFO_MAX_VEL,
                Assets.ufo[ThreadLocalRandom.current().nextInt(0,3)],
                path,
                this
        ));
    }


    public void playExplosion(Vector2D position){
        explosions.add(new Animation(
                Assets.exp,
                20,
                position.subtract(new Vector2D(Assets.exp[0].getWidth()/2, Assets.exp[0].getHeight()/2))
        ));
    }

    public void update() {

        // RESTART
        if (KeyBoard.SPACE) {
            respawnWithSpace();
        }

        for (int i = 0; i < movingObjects.size(); i++) {
            movingObjects.get(i).update();
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            Animation anim = explosions.get(i);
            anim.update();
            if(!anim.isRunning())
                explosions.remove(i);
        }

        for (int i = 0; i < movingObjects.size(); i++) {
            if(movingObjects.get(i) instanceof Meteor)
                return;
        }

        startWave();
    }

    public void divideMeteor(Meteor meteor){

        Size size = meteor.getSize();
        BufferedImage[] textures = size.textures;

        Size newSize = null;

        switch (size){
            case BIG:
                newSize = Size.MED;
                break;
            case MED:
                newSize = Size.SMALL;
                break;
            case SMALL:
                newSize = Size.TINY;
                break;
            default:
                return;
        }

        for (int i = 0; i < size.quantity; i++){
            movingObjects.add(new Meteor(
                    meteor.getPosition(),
                    new Vector2D(0,1).setDirection(Math.random()*Math.PI*2),
                    Constants.METEOR_VEL*Math.random()+1,
                    textures[(int) (Math.random()*textures.length)],
                    this,
                    newSize
            ));
        }

    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for (int i = 0; i < movingObjects.size(); i++) {
            movingObjects.get(i).draw(g);
        }

        for (int i = 0; i < explosions.size(); i++) {
           Animation anim = explosions.get(i);
            g2d.drawImage(anim.getCurrentFrame(), (int) anim.getPosition().getX(), (int) anim.getPosition().getY(), null);
        }

        drawScore(g);
        drawLives(g);
    }

    private void drawScore(Graphics g) {
        Vector2D pos = new Vector2D(1050, 25);
        String scoreToString = Integer.toString(score);

        for (int i = 0; i < scoreToString.length(); i++) {
            g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i +1))],
                    (int)pos.getX(), (int)pos.getY(), null);
            pos.setX(pos.getX() + 20);
        }
    }

    private void drawLives(Graphics g){
        Vector2D livesPosition = new Vector2D(650, 25);
        g.drawImage(Assets.life, (int)livesPosition.getX(), (int)livesPosition.getY(), null);
        g.drawImage(Assets.numbers[10], (int)livesPosition.getX() + 40,
                (int)livesPosition.getY() + 5, null);

        String livesToString = Integer.toString(lives);

        Vector2D pos = new Vector2D(livesPosition.getX(), livesPosition.getY());

        for(int i = 0; i < livesToString.length(); i++) {

            int number = Integer.parseInt(livesToString.substring(i, i+1));
            if (number < 0)
                break;

            g.drawImage(Assets.numbers[number],
                    (int)pos.getX() + 60,(int)pos.getY() + 5,null);
            pos.setX(pos.getX() + 20);

        }
    }

    public ArrayList<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    public void setMovingObjects(ArrayList<MovingObject> movingObjects) {
        this.movingObjects = movingObjects;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void substractLives(){
        lives--;
    }

    private void respawnWithSpace(){

        boolean hayPlayer = false;

        for(int i = 0; i < movingObjects.size(); i++){
            MovingObject m = movingObjects.get(i);
            if (m instanceof Player) {
                hayPlayer = true;
            }
        }

        if (hayPlayer) {
            for(int i = 0; i < movingObjects.size(); i++){
                MovingObject m = movingObjects.get(i);
                if (m instanceof Player && getLives() == 0) {
                    ((Player) m).destroy();
                    setLives(5);

                }
            }
        } else {
            Player newPlayer = new Player(
                    new Vector2D(Constants.WIDTH/2 - Assets.player.getWidth()/2,
                            Constants.HEIGHT/2 - Assets.player.getHeight()/2),
                    new Vector2D(),
                    Constants.PLAYER_MAX_VEL,
                    Assets.player,
                    this);

            movingObjects.add(newPlayer);

            setLives(5);
        }



        /*if (hayPlayer) {
            for(int i = 0; i < movingObjects.size(); i++){
                MovingObject m = movingObjects.get(i);
                if (m instanceof Player){
                    getMovingObjects().remove(m);
                }
            }
        } else if (!hayPlayer && lives == 0) {
            movingObjects.add(new Player(
                    new Vector2D(Constants.WIDTH/2 - Assets.player.getWidth()/2,
                                Constants.HEIGHT/2 - Assets.player.getHeight()/2),
                    new Vector2D(),
                    Constants.PLAYER_MAX_VEL,
                    Assets.player,
                    this
                ));
            setLives(5);
        }*/


    }

}
