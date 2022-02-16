package xyz.przemyk.simpleplanes.items;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Function;

public class PlaneItem extends ItemTFC {

    private final Function<World, PlaneEntity> planeSupplier;

    public PlaneItem(Function<World, PlaneEntity> planeSupplier) {
        super();
        setMaxStackSize(1);
        this.planeSupplier = planeSupplier;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return super.hasEffect(stack) || stack.getSubCompound("EntityTag") != null;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote) {
            return new ActionResult<>(EnumActionResult.PASS, itemstack);
        }
        float f = 1.0F;
        float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch);
        float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw);
        double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX);
        double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) + (double) playerIn.getEyeHeight();
        double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ);
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        Vec3d vec3d1 = vec3d.add((double) f7 * 5.0D, (double) f6 * 5.0D, (double) f8 * 5.0D);
        RayTraceResult raytraceresult = worldIn.rayTraceBlocks(vec3d, vec3d1, true);
        if (raytraceresult == null) {
            return new ActionResult<>(EnumActionResult.PASS, itemstack);
        }
        Vec3d vec3d2 = playerIn.getLook(1.0F);
        boolean flag = false;
        List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox().expand(vec3d2.x * 5.0D, vec3d2.y * 5.0D, vec3d2.z * 5.0D).grow(1.0D));

        for (Entity entity : list) {
            if (entity.canBeCollidedWith()) {
                AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(entity.getCollisionBorderSize());
                if (axisalignedbb.contains(vec3d)) {
                    flag = true;
                }
            }
        }

        if (flag) {
            return new ActionResult<>(EnumActionResult.PASS, itemstack);
        } else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return new ActionResult<>(EnumActionResult.PASS, itemstack);
        }

        PlaneEntity planeEntity = planeSupplier.apply(worldIn);
        UpgradeType coalEngine = SimplePlanesUpgrades.COAL_ENGINE;
        Upgrade upgrade = coalEngine.instanceSupplier.apply(planeEntity);
        if (itemstack.getSubCompound("Used") == null) {
            planeEntity.upgrades.put(coalEngine.getRegistryName(), upgrade);
            planeEntity.upgradeChanged();
        }
        planeEntity.setPosition(raytraceresult.getBlockPos().getX(), raytraceresult.getBlockPos().getY() + 1, raytraceresult.getBlockPos().getZ());
        planeEntity.rotationYaw = playerIn.rotationYaw;
        planeEntity.prevRotationYaw = playerIn.prevRotationYaw;
        planeEntity.setCustomNameTag(itemstack.getDisplayName());
        NBTTagCompound entityTag = itemstack.getSubCompound("EntityTag");
        if (entityTag != null) {
            planeEntity.readEntityFromNBT(entityTag);
        }
        if (!worldIn.checkNoEntityCollision(planeEntity.getCollisionBoundingBox().grow(-0.1D), planeEntity)) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        } else {
            boolean b = worldIn.spawnEntity(planeEntity);
            if (!playerIn.capabilities.isCreativeMode) {
                itemstack.shrink(1);
            }
            playerIn.addStat(StatList.getObjectUseStats(this));
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack) {
        return Size.VERY_LARGE;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack) {
        return Weight.VERY_HEAVY;
    }
}