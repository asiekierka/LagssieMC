package pl.asie.lagssie;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LagssieServer {
    private LagssieWatchDog watchDog;
    private int i;

    @SubscribeEvent
    public void serverTicks(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        i++;
        if (watchDog != null) {
            // Pat Lagssie
            watchDog.ticks++;
        } else if (i == 40) {
            watchDog = new LagssieWatchDog("LAG-SERVER", Thread.currentThread(), Lagssie.intervalServer);
            watchDog.start();
        }
    }
}
