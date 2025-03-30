package org.gtreimagined.gtcore.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.fluid.GTFluid;

import static org.gtreimagined.gtlib.fluid.GTFluid.OVERLAY_TEXTURE;

public class GTCoreFluids {
    public static final ResourceLocation PAHOEHOE_STILL_TEXTURE = new ResourceLocation(GTCore.ID, "fluid/pahoehoe_lava");
    public static final GTFluid PAHOEHOE_LAVA = GTAPI.register(GTFluid.class, new GTFluid(GTCore.ID,"pahoehoe_lava", prepareLavaAttributes(), prepareLavaProperties()));
    public static final GTFluid FIERY_BLOOD = GTAPI.register(GTFluid.class, new GTFluid(GTCore.ID,"fiery_blood", prepareAttributes("fiery_blood"), prepareProperties()));
    public static final GTFluid FIERY_TEARS = GTAPI.register(GTFluid.class, new GTFluid(GTCore.ID,"fiery_tears", prepareAttributes("fiery_tears"), prepareProperties()));
    public static final GTFluid BEET_JUICE = GTAPI.register(GTFluid.class, new GTFluid(GTCore.ID, "beet_juice", prepareAttributes("beet_juice"), prepareProperties()));

    public static void init(){

    }
    private static FluidAttributes.Builder prepareLavaAttributes() {
        return FluidAttributes.builder(PAHOEHOE_STILL_TEXTURE, PAHOEHOE_STILL_TEXTURE).overlay(OVERLAY_TEXTURE)
                .viscosity(3000).density(6000).temperature(1200).sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY);
    }

    private static Block.Properties prepareLavaProperties() {
        return Block.Properties.of(Material.LAVA).strength(100.0F).noDrops().lightLevel(s -> 9);
    }

    private static FluidAttributes.Builder prepareAttributes(String fluid) {
        return FluidAttributes.builder(new ResourceLocation(GTCore.ID, "fluid/" + fluid), new ResourceLocation(GTCore.ID, "fluid/" + fluid)).overlay(OVERLAY_TEXTURE)
                .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY);
    }

    private static Block.Properties prepareProperties() {
        return Block.Properties.of(net.minecraft.world.level.material.Material.WATER).strength(100.0F).noDrops();
    }
}
