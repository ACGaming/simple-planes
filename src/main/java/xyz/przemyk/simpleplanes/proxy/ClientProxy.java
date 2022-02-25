package xyz.przemyk.simpleplanes.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.*;
import xyz.przemyk.simpleplanes.setup.PlaneMovingSound;

public class ClientProxy extends CommonProxy {
    public static KeyBinding keyBind;

    public static void playEngineSound(PlaneEntity plane) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new PlaneMovingSound(plane));
    }

    @Override
    public void init() {
        RenderingRegistry.registerEntityRenderingHandler(PlaneEntity.class, PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(LargePlaneEntity.class, LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HelicopterEntity.class, HelicopterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MegaPlaneEntity.class, MegaPlaneRenderer::new);

        MinecraftForge.EVENT_BUS.register(new PlaneGui());

        keyBind = new KeyBinding("key.plane_boost.desc", 29, "key.simpleplanes.category");
        ClientRegistry.registerKeyBinding(keyBind);
    }
}