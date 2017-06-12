package pl.asie.lagssie;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Lagssie.MODID, version = Lagssie.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.9,1.13)")
public class Lagssie {
    public static final String MODID = "lagssie";
    public static final String VERSION = "@VERSION@";

    public static Logger logger;
    public static Configuration config;
    public static double intervalClient, intervalServer;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = LogManager.getLogger("lagssie");
        config = new Configuration(event.getSuggestedConfigurationFile());

        intervalClient = config.getFloat("intervalClient", "general", 0.25f, 0f, 1000.0f, "If the client is stuck longer than this amount of time (in seconds), dump a stacktrace of what it is doing. Set to 0 to disable.");
        intervalServer = config.getFloat("intervalServer", "general", 0.25f, 0f, 1000.0f, "If the server is stuck longer than this amount of time (in seconds), dump a stacktrace of what it is doing. Set to 0 to disable.");

        config.save();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (intervalServer > 0) {
            MinecraftForge.EVENT_BUS.register(new LagssieServer());
        }
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT && intervalClient > 0) {
            initClient();
        }
    }

    @SideOnly(Side.CLIENT)
    public void initClient() {
        MinecraftForge.EVENT_BUS.register(new LagssieClient());
    }
}
