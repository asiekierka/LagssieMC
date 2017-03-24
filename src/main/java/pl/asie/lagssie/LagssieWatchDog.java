package pl.asie.lagssie;

import java.util.Random;

public class LagssieWatchDog implements Runnable {
    public int ticks = 0;

    private final String name;
    private Thread dog_thread;
    private Thread watch_thread;
    private double sleep_time;
    private final boolean be_cute = true;

    public LagssieWatchDog(String name, Thread watch_thread, double sleep_time) {
        this.name = name;
        this.watch_thread = watch_thread;
        this.sleep_time = sleep_time;
    }

    private static final String[] sickeningly_cute_lag_detected_messages = new String[] {
            "Woof! Something is being slow!",
            "Bark Bark! There's some lag! Bark bark!",
            "Pant Pant! Lag! Whiiinnee",
            "Whiine! Lag!",
            "Bark! Lag! Bark! Lag!",
            "Woof! Why is Minecraft being so laggy!",
            "Yip! Lagfest!",
            "Bark! Minecraft is running slowly! It must be your computer! Bark!",
            "Arf! Why's it freezing up!",
            "Arf arf! Minecraft fell down the well!",
            "Bark! *I'm* the one who's supposed to play dead!"
    }; // This is what makes this really useful.

    @Override
    public void run() {
        log("Woof! I am Lagssie the Lag Watch Dog!");
        int last_tick = 0;
        boolean had_good_tick = true;
        Random rng = new Random();
        while (true) {
            try {
                Thread.sleep((int)(sleep_time*1000));
            } catch (InterruptedException e) {
                continue;
            }
            if (ticks == last_tick) {
                if (had_good_tick) {
                    if (be_cute) {
                        log(sickeningly_cute_lag_detected_messages[rng.nextInt(sickeningly_cute_lag_detected_messages.length)]);
                    }
                    had_good_tick = false;
                } else {
                    log("");
                }
                for (StackTraceElement ste : watch_thread.getStackTrace()) {
                    log("   " + ste.toString());
                }
            } else {
                had_good_tick = true;
            }
            last_tick = ticks;
        }
    }

    void log(String msg) {
        Lagssie.logger.info("[" + name + "] " + msg);
    }

    public void start() {
        dog_thread = new Thread(this);
        dog_thread.setDaemon(true);
        dog_thread.start();
    }
}
