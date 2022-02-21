package xyz.przemyk.simpleplanes.upgrades.energy;

import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

public class JunkEngine extends CoalEngine {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/engine_j.png");
    public static final ResourceLocation TEXTURE_LIT = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/engine_j_lit.png");

    public JunkEngine(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SMOKER_ENGINE, planeEntity);
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, World world, EnumHand hand, ItemStack itemStack) {
        if (!player.world.isRemote && planeEntity.getFuel() < SimplePlanesConfig.COAL_MAX_FUEL) {
            int burnTime = TileEntityFurnace.getItemBurnTime(itemStack);
            if (!OreDictionaryHelper.doesStackMatchOre(itemStack, "logWood")
                    && !OreDictionaryHelper.doesStackMatchOre(itemStack, "gemCoal")
                    && !OreDictionaryHelper.doesStackMatchOre(itemStack, "charcoal")
                    && burnTime > 0) {
                int fuel = (int) ((burnTime / 1600f) * SimplePlanesConfig.FLY_TICKS_PER_COAL);
                planeEntity.addFuelMaxed(fuel);
                if (!player.isCreative()) {
                    Item item = itemStack.getItem();
                    itemStack.shrink(1);
                    if (itemStack.isEmpty()) {
                        ItemStack item1 = item.getContainerItem(itemStack);
                        if (!item1.isEmpty()) {
                            player.setHeldItem(hand, item1);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ResourceLocation getTexture() {
        return planeEntity.isPowered() ? TEXTURE_LIT : TEXTURE;
    }
}