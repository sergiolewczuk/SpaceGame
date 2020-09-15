package gamesObjects;

public class Chronometer {
    private long delta, lastTime;
    private long time;
    public boolean running;

    public Chronometer() {
        delta = 0;
        lastTime = 0;
        running = false;
    }

    public void run (long time) {
        running = true;
        this.time = time;
    }

    public void update (){
        if(running){
            delta += System.currentTimeMillis() - lastTime;
        }
        if(delta >= time){
            running = false;
            delta = 0;
        }

        lastTime = System.currentTimeMillis();
    }

    public boolean isRunning(){
        return running;
    }

}
