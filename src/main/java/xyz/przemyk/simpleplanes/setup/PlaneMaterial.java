package xyz.przemyk.simpleplanes.setup;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class PlaneMaterial implements IForgeRegistryEntry<PlaneMaterial> {
    public final boolean fireResistant;
    public String name;
    private ResourceLocation registyName;

    public PlaneMaterial(String name, boolean fireResistant) {
        this.name = name;
        this.fireResistant = fireResistant;
    }

    public PlaneMaterial(String name) {
        this(name, false);
    }

    @Override
    public String toString() {
        return name;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return registyName;
    }

    @Override
    public PlaneMaterial setRegistryName(ResourceLocation name) {
        registyName = name;
        return this;
    }

    @Override
    public Class<PlaneMaterial> getRegistryType() {
        return PlaneMaterial.class;
    }
}