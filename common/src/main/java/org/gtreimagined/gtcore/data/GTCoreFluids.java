package org.gtreimagined.gtcore.data;

import earth.terrarium.botarium.common.registry.fluid.FluidProperties;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.fluid.AntimatterFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;

import static muramasa.antimatter.fluid.AntimatterFluid.OVERLAY_TEXTURE;

public class GTCoreFluids {
    public static final ResourceLocation PAHOEHOE_STILL_TEXTURE = new ResourceLocation(GTCore.ID, "fluid/pahoehoe_lava");
    public static final AntimatterFluid PAHOEHOE_LAVA = AntimatterAPI.register(AntimatterFluid.class, new AntimatterFluid(GTCore.ID,"pahoehoe_lava", prepareLavaAttributes(), prepareLavaProperties()));
    public static final AntimatterFluid FIERY_BLOOD = AntimatterAPI.register(AntimatterFluid.class, new AntimatterFluid(GTCore.ID,"fiery_blood", prepareAttributes("fiery_blood"), prepareProperties()));
    public static final AntimatterFluid FIERY_TEARS = AntimatterAPI.register(AntimatterFluid.class, new AntimatterFluid(GTCore.ID,"fiery_tears", prepareAttributes("fiery_tears"), prepareProperties()));
    public static final AntimatterFluid BEET_JUICE = AntimatterAPI.register(AntimatterFluid.class, new AntimatterFluid(GTCore.ID, "beet_juice", prepareAttributes("beet_juice"), prepareProperties()));

    public static void init(){

    }
    private static FluidProperties.Builder prepareLavaAttributes() {
        FluidProperties.Builder builder = FluidProperties.create();
        return builder.still(PAHOEHOE_STILL_TEXTURE).flowing(PAHOEHOE_STILL_TEXTURE).overlay(OVERLAY_TEXTURE)
                .viscosity(3000).density(6000).temperature(1200).sounds("bucket_fill", SoundEvents.BUCKET_FILL).sounds("bucket_empty", SoundEvents.BUCKET_EMPTY);
    }

    private static Block.Properties prepareLavaProperties() {
        return Block.Properties.of(Material.LAVA).strength(100.0F).noDrops().lightLevel(s -> 9);
    }

    private static FluidProperties.Builder prepareAttributes(String fluid) {
        FluidProperties.Builder builder = FluidProperties.create();
        return builder.still(new ResourceLocation(GTCore.ID, "fluid/" + fluid)).flowing(new ResourceLocation(GTCore.ID, "fluid/" + fluid)).overlay(OVERLAY_TEXTURE)
                .sounds("bucket_fill", SoundEvents.BUCKET_FILL).sounds("bucket_empty", SoundEvents.BUCKET_EMPTY);
    }

    private static Block.Properties prepareProperties() {
        return Block.Properties.of(net.minecraft.world.level.material.Material.WATER).strength(100.0F).noDrops();
    }
}
