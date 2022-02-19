package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.proxy.ClientProxy;

//@SideOnly(Side.CLIENT)
//@Mod.EventBusSubscriber
public class PlaneGui extends Gui {
    private final ResourceLocation bar = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/hpbar.png");

    @SubscribeEvent
    public void renderGameOverlayPre(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getRidingEntity();
            if (event.getType() == ElementType.ALL) {
                if (planeEntity.mountMessage) {
                    planeEntity.mountMessage = false;
                    if (planeEntity instanceof HelicopterEntity) {
                        Minecraft.getMinecraft().ingameGUI
                                .setOverlayMessage(new TextComponentTranslation("helicopter.onboard", mc.gameSettings.keyBindSneak.getDisplayName(),
                                        ClientProxy.keyBind.getDisplayName()), false);
                    } else {
                        Minecraft.getMinecraft().ingameGUI
                                .setOverlayMessage(new TextComponentTranslation("plane.onboard", mc.gameSettings.keyBindSneak.getDisplayName(),
                                        ClientProxy.keyBind.getDisplayName()), false);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void renderGameOverlayPost(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = new ScaledResolution(mc);
        int scaledWidth = res.getScaledWidth();
        int scaledHeight = res.getScaledHeight();

        if (mc.player.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getRidingEntity();
            if (event.getType() == ElementType.ALL) {
                int x = scaledWidth / 2 - 91;
                int y = scaledHeight - 32 + 3;
                mc.getTextureManager().bindTexture(bar);
                float fuel = planeEntity.getFuel();
                //fuel = (float) Math.log1p(fuel);
                float max_fuel = planeEntity.getMaxFuel();
                //max_fuel = (float) Math.log1p(max_fuel);
                float part = fuel / max_fuel;
                part = (float) Math.pow(part, 0.5);
                part = MathHelper.clamp(part, 0, 1);
                int bar_width = 182;
                int currentWidth = (int) (bar_width * part);
                int tex_width = 182;
                int tex_height = 5;
                drawTexturedModalRect(x, y, 0, 0, tex_width, tex_height);
                int bar_image_pos = planeEntity.isSprinting() ? tex_height * 2 : tex_height;
                drawTexturedModalRect(x, y, 0, bar_image_pos, currentWidth, tex_height);
            }
            if (event.getType() == ElementType.ALL) {
                mc.getTextureManager().bindTexture(bar);
                int left_align = scaledWidth / 2 + 91;
                int health = (int) Math.ceil(planeEntity.getHealth());
                float healthMax = planeEntity.getMaxHealth();
                int hearts = (int) (healthMax);
                if (hearts > 10) hearts = 10;
                final int FULL = 0;
                final int EMPTY = 16;
                final int GOLD = 32;
                int right_height = 49;
                int max_row_size = 5;

                for (int heart = 0; hearts > 0; heart += max_row_size) {
                    int top = scaledHeight - right_height;
                    int rowCount = Math.min(hearts, max_row_size);
                    hearts -= rowCount;

                    for (int i = 0; i < rowCount; ++i) {
                        int x = left_align - i * 16 - 16;
                        if (i + heart + 10 < health)
                            drawTexturedModalRect(x, top, GOLD, 15, 16, 9);
                        else if (i + heart < health)
                            drawTexturedModalRect(x, top, FULL, 15, 16, 9);
                        else
                            drawTexturedModalRect(x, top, EMPTY, 15, 16, 9);
                    }
                    right_height += 10;
                }
            }
        }
    }
}