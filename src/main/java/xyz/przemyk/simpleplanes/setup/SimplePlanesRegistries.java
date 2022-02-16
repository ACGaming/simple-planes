package xyz.przemyk.simpleplanes.setup;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID)
public class SimplePlanesRegistries {

    public static IForgeRegistry<PlaneMaterial> PLANE_MATERIALS;
    public static IForgeRegistry<UpgradeType> UPGRADE_TYPES;

    @SubscribeEvent
    public static void registerRegistries(final RegistryEvent.NewRegistry event) {
        UPGRADE_TYPES = new RegistryBuilder<UpgradeType>().setName(new ResourceLocation(SimplePlanesMod.MODID, "upgrade_types")).setType(UpgradeType.class)
                .create();
        PLANE_MATERIALS = new RegistryBuilder<PlaneMaterial>()
                .setType(PlaneMaterial.class)
                .setDefaultKey(new ResourceLocation(SimplePlanesMod.MODID, "tfc_oak"))
                .setName(new ResourceLocation(SimplePlanesMod.MODID, "plane_materials"))
                .create();
    }
}