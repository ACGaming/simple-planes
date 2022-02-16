package xyz.przemyk.simpleplanes;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = SimplePlanesMod.MODID, name = "SimplePlanesTFC")
public class SimplePlanesConfig {
    @Config.Name("Easy flight")
    @Config.Comment("Easier flight mode, disables extreme movements")
    public static boolean EASY_FLIGHT = false;

    @Config.Name("Plane crash")
    @Config.Comment("Can planes crash?")
    public static boolean PLANE_CRASH = true;

    @Config.Name("Plane heist")
    @Config.Comment("Can players steal planes?")
    public static boolean PLANE_HEIST = true;

    @Config.Name("Coal max fuel")
    @Config.Comment("Max fuel value for the coal plane")
    public static int COAL_MAX_FUEL = 1600;

    @Config.Name("Fly ticks per coal")
    @Config.Comment("Ticks of flying per one coal burn time")
    public static int FLY_TICKS_PER_COAL = 160;

    @Config.Name("Turn threshold")
    @Config.Comment("For controllers, a threshold for the joystick movement of the plane")
    public static int TURN_THRESHOLD = 20;

    //public static int ENERGY_FLY_TICKS = 5;
    //public static int ENERGY_COST = 600;
    //public static int ENERGY_MAX_FUEL;
    //public static int LAVA_FLY_TICKS = 2000;
    //public static int LAVA_COST = 1000;
    //public static int LAVA_MAX_FUEL = 20000;

    @Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID)
    public static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(SimplePlanesMod.MODID)) {
                ConfigManager.sync(SimplePlanesMod.MODID, Config.Type.INSTANCE);
            }
        }
    }
}