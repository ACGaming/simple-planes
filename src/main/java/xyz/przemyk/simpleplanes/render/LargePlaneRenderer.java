package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

public class LargePlaneRenderer extends AbstractPlaneRenderer<LargePlaneEntity> {

    protected final LargeFurnacePlaneModel planeModel = new LargeFurnacePlaneModel();

    public LargePlaneRenderer(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 1.0f;
    }

    @Override
    protected ModelBase getModel() {
        return planeModel;
    }

    @Override
    public ResourceLocation getEntityTexture(LargePlaneEntity entity) {
        return new ResourceLocation("tfc", "textures/blocks/wood/planks/" + entity.getMaterial().name.replace("tfc_", "") + ".png");
    }
}