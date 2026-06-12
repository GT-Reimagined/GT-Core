package org.gtreimagined.gtcore.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import org.gtreimagined.gtcore.GTCore;

public class GTCoreDamageTypes {
    public static final ResourceKey<DamageType> MORTAR = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(GTCore.ID, "mortar"));
}
