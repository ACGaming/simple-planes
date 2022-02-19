package xyz.przemyk.simpleplanes.setup;


import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SideOnly(Side.CLIENT)
public class PlaneMovingSound extends MovingSound {
    private final PlaneEntity plane;
    private float distance = 0.0F;

    public PlaneMovingSound(PlaneEntity planeIn) {
        super(SimplePlanesSounds.PLANE_LOOP, SoundCategory.AMBIENT);
        this.plane = planeIn;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    @Override
    public float getPitch() {
        return (float) MathHelper.clamp(0.9F + plane.getMotion().length() / 3F, 0.9F, 1.3F);
    }

    @Override
    public void update() {
        if (this.plane.isDead) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) this.plane.posX;
            this.yPosF = (float) this.plane.posY;
            this.zPosF = (float) this.plane.posZ;
            float f = MathHelper.sqrt(this.plane.motionX * this.plane.motionX + this.plane.motionZ * this.plane.motionZ);

            if ((double) f >= 0.01D) {
                this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 1.0F);
                this.volume = 0.0F + MathHelper.clamp(f, 0.0F, 1.0F) * 0.7F;
            } else {
                this.distance = 0.0F;
                this.volume = 0.0F;
            }
        }
    }
}