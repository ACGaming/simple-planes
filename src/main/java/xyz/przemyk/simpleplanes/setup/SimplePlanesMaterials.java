package xyz.przemyk.simpleplanes.setup;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

import java.util.Map.Entry;
import java.util.Set;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID)
public class SimplePlanesMaterials {

    public static final String[] MATERIALS = new String[]{
            ("tfc_acacia"),
            ("tfc_ash"),
            ("tfc_aspen"),
            ("tfc_birch"),
            ("tfc_blackwood"),
            ("tfc_chestnut"),
            ("tfc_douglas_fir"),
            ("tfc_hickory"),
            ("tfc_kapok"),
            ("tfc_maple"),
            ("tfc_oak"),
            ("tfc_palm"),
            ("tfc_pine"),
            ("tfc_rosewood"),
            ("tfc_sequoia"),
            ("tfc_spruce"),
            ("tfc_sycamore"),
            ("tfc_white_cedar"),
            ("tfc_willow")
    };

    @GameRegistry.ObjectHolder(SimplePlanesMod.MODID + ":tfc_oak")
    public static final PlaneMaterial TFC_OAK = null;

    public static Set<Entry<ResourceLocation, PlaneMaterial>> getMaterials() {
        return SimplePlanesRegistries.PLANE_MATERIALS.getEntries();
    }

    public static PlaneMaterial getMaterial(ResourceLocation name) {
        return SimplePlanesRegistries.PLANE_MATERIALS.getValue(name);
    }

    @SubscribeEvent
    public static void registerMaterials(RegistryEvent.Register<PlaneMaterial> event) {
        for (String name :
                MATERIALS) {
            register(event, name);
        }
    }

    public static void register(Register<PlaneMaterial> event, String name) {
        event.getRegistry().register(new PlaneMaterial(name).setRegistryName(new ResourceLocation(SimplePlanesMod.MODID, name)));
    }

    public static PlaneMaterial getMaterial(String name) {
        return getMaterial(new ResourceLocation(SimplePlanesMod.MODID, name));
    }
}