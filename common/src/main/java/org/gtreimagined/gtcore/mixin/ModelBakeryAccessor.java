package org.gtreimagined.gtcore.mixin;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModelBakery.class)
public interface ModelBakeryAccessor {
    @Accessor
    ResourceManager getResourceManager();
}
