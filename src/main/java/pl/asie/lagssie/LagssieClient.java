package pl.asie.lagssie;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LagssieClient {
    private LagssieWatchDog watchDog;
    private int i;

    @SubscribeEvent
    public void clientTicks(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        i++;
        if (watchDog != null) {
            // Pat Lagssie
            watchDog.ticks++;
        } else if (i == 40) {
            watchDog = new LagssieWatchDog("LAG-CLIENT", Thread.currentThread(), Lagssie.intervalClient);
            watchDog.start();
        }
    }
}
