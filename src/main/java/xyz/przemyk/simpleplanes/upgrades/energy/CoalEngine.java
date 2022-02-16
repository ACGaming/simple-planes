package xyz.przemyk.simpleplanes.upgrades.energy;

import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.EngineModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

public class CoalEngine extends AbstractEngine {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/engine.png");
    public static final ResourceLocation TEXTURE_LIT = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/engine_lit.png");

    public CoalEngine(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.COAL_ENGINE, planeEntity);
    }

    public CoalEngine(UpgradeType type, PlaneEntity planeEntity) {
        super(type, planeEntity);
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, World world, EnumHand hand, ItemStack itemStack) {
        if (!player.world.isRemote && planeEntity.getFuel() < SimplePlanesConfig.FLY_TICKS_PER_COAL / 4) {
            if (OreDictionaryHelper.doesStackMatchOre(itemStack, "gemCoal")) {
                planeEntity.addFuelMaxed();
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }
        }
        return false;
    }

    @Override
    public void render(float partialticks, float scale) {
        EngineModel.renderEngine(planeEntity, partialticks, scale);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        planeEntity.setMaxFuel(SimplePlanesConfig.COAL_MAX_FUEL);
    }

    @Override
    public void onApply(ItemStack itemStack, EntityPlayer playerEntity) {
        super.onApply(itemStack, playerEntity);
        planeEntity.setMaxFuel(SimplePlanesConfig.COAL_MAX_FUEL);
    }

    @Override
    public ResourceLocation getTexture() {
        return planeEntity.isPowered() ? TEXTURE_LIT : TEXTURE;
    }
}