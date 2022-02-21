package xyz.przemyk.simpleplanes.setup;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.items.InformationItem;
import xyz.przemyk.simpleplanes.items.PlaneItem;

import java.util.ArrayList;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID)
public class SimplePlanesItems {
    public static final ArrayList<Item> ITEMS = new ArrayList<>();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : ITEMS) {
            event.getRegistry().register(item);
        }
    }

    public static void init() {
        for (String name : SimplePlanesMaterials.MATERIALS) {
            register(name + "_plane", new PlaneItem(world -> new PlaneEntity(world, SimplePlanesMaterials.getMaterial(name))));
            register(name + "_large_plane", new PlaneItem(world -> new LargePlaneEntity(world, SimplePlanesMaterials.getMaterial(name))));
            register(name + "_helicopter", new PlaneItem(world -> new HelicopterEntity(world, SimplePlanesMaterials.getMaterial(name))));
            register(name + "_mega_plane", new PlaneItem(world -> new MegaPlaneEntity(world, SimplePlanesMaterials.getMaterial(name))));
        }
    }

    private static Item register(String id, Item item) {
        ITEMS.add(item);
        item.setRegistryName(SimplePlanesMod.MODID, id);
        item.setCreativeTab(tab);
        item.setTranslationKey(id);
        return item;
    }

    public static CreativeTabs tab = new CreativeTabs("simpleplanes") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(PROPELLER);
        }
    };

    public static final Item PROPELLER = register("propeller", new Item());
    public static final Item JUNK_ENGINE = register("junk_engine", new Item());
    public static final Item COAL_ENGINE = register("furnace_engine", new Item());
    public static final Item LAVA_ENGINE = register("lava_engine", new Item());
    public static final Item SPRAYER = register("sprayer", new InformationItem(new TextComponentTranslation("description.simpleplanes.sprayer")));
    public static final Item BOOSTER = register("booster", new InformationItem(new TextComponentTranslation("description.simpleplanes.booster")));
    public static final Item FLOATY_BEDDING = register("floaty_bedding", new InformationItem(new TextComponentTranslation("description.simpleplanes.floaty_bedding")));
    public static final Item SHOOTER = register("shooter", new InformationItem(new TextComponentTranslation("description.simpleplanes.shooter")));
    public static final Item FOLDING = register("folding", new InformationItem(new TextComponentTranslation("description.simpleplanes.folding")));
    public static final Item HEALING = register("healing", new InformationItem(new TextComponentTranslation("description.simpleplanes.healing")));
}