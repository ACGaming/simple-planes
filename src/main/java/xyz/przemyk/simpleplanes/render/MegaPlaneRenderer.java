package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;

public class MegaPlaneRenderer extends AbstractPlaneRenderer<MegaPlaneEntity> {

    public MegaPlaneRenderer(RenderManager renderManager) {
        super(renderManager);
        propellerModel = new MegaPlanePropellerModel();
        shadowSize = 1.0f;
    }

    @Override
    protected void renderAdditional(MegaPlaneEntity planeEntity, float partialTicks) {
        MegaPlaneWindowsModel windowsModel = new MegaPlaneWindowsModel();
        windowsModel.render(planeEntity, 0, 0, 0, 0, 0, 0);
    }

    @Override
    protected ModelBase getModel() {
        propellerModel = new MegaPlanePropellerModel();
        return new MegaPlaneModel();
    }

    @Override
    public ResourceLocation getEntityTexture(MegaPlaneEntity entity) {
        return new ResourceLocation("tfc", "textures/blocks/wood/planks/" + entity.getMaterial().name.replace("tfc_", "") + ".png");
    }
}