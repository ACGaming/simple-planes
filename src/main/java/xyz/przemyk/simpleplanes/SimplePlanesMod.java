package xyz.przemyk.simpleplanes;

import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.przemyk.simpleplanes.handler.PlaneNetworking;
import xyz.przemyk.simpleplanes.proxy.CommonProxy;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID)
@Mod(modid = SimplePlanesMod.MODID, name = SimplePlanesMod.MODID, version = SimplePlanesMod.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = "required-after:tfc")
public class SimplePlanesMod {
    public static final String MODID = "simpleplanes";
    public static final String VERSION = "3.0.1.9";
    public static final DamageSource DAMAGE_SOURCE_PLANE_CRASH = (new DamageSource("plane_crash")).setDamageBypassesArmor();
    @Mod.Instance
    public static SimplePlanesMod instance;
    @SidedProxy(clientSide = "xyz.przemyk.simpleplanes.proxy.ClientProxy", serverSide = "xyz.przemyk.simpleplanes.proxy.CommonProxy")
    public static CommonProxy proxy;

    public SimplePlanesMod() {
        SimplePlanesItems.init();
        PlaneNetworking.init();
    }

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent initializationEvent) {
        proxy.init();
    }
}