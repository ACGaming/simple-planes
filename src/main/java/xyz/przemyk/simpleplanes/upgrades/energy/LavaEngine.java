package xyz.przemyk.simpleplanes.upgrades.energy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.przemyk.simpleplanes.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.EngineModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

public class LavaEngine extends AbstractEngine {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/engine_l.png");
    public static final ResourceLocation TEXTURE_LIT = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/engine_l_lit.png");

    public LavaEngine(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.LAVA_ENGINE, planeEntity);
    }

    public LavaEngine(UpgradeType type, PlaneEntity planeEntity) {
        super(type, planeEntity);
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, World world, EnumHand hand, ItemStack itemStack) {
        if (!player.world.isRemote && planeEntity.getFuel() < (SimplePlanesConfig.LAVA_MAX_FUEL - SimplePlanesConfig.FLY_TICKS_PER_LAVA)) {
            IFluidHandler bucketCap = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (itemStack.getItem() == Items.LAVA_BUCKET) {
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                    ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(Items.BUCKET));
                }
                planeEntity.addFuelMaxed(SimplePlanesConfig.FLY_TICKS_PER_LAVA);
            } else if (bucketCap != null) {
                FluidStack fluidStack = bucketCap.drain(1000, false);
                if (fluidStack != null && fluidStack.getFluid() == FluidRegistry.LAVA) {
                    bucketCap.drain(1000, !player.isCreative());
                    planeEntity.addFuelMaxed(SimplePlanesConfig.FLY_TICKS_PER_LAVA);
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
        planeEntity.setMaxFuel(SimplePlanesConfig.LAVA_MAX_FUEL);
    }

    @Override
    public void onApply(ItemStack itemStack, EntityPlayer playerEntity) {
        super.onApply(itemStack, playerEntity);
        planeEntity.setMaxFuel(SimplePlanesConfig.LAVA_MAX_FUEL);
    }

    @Override
    public ResourceLocation getTexture() {
        return planeEntity.isPowered() ? TEXTURE_LIT : TEXTURE;
    }
}