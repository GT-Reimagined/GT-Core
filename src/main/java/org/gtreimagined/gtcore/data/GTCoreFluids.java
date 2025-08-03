package org.gtreimagined.gtcore.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidType.Properties;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.fluid.GTClientFluidTypeExtension;
import org.gtreimagined.gtlib.fluid.GTFluid;

import static org.gtreimagined.gtlib.fluid.GTFluid.OVERLAY_TEXTURE;

public class GTCoreFluids {
    public static final ResourceLocation PAHOEHOE_STILL_TEXTURE = new ResourceLocation(GTCore.ID, "fluid/pahoehoe_lava");
    public static final GTFluid PAHOEHOE_LAVA = GTAPI.register(GTFluid.class, new GTFluid(GTCore.ID,"pahoehoe_lava", prepareFluidType(true), prepareLavaProperties(), prepareClientExtensions("pahoehoe_lava")));
    public static final GTFluid FIERY_BLOOD = GTAPI.register(GTFluid.class, new GTFluid(GTCore.ID,"fiery_blood", prepareFluidType(false), prepareProperties(), prepareClientExtensions("fiery_blood")));
    public static final GTFluid FIERY_TEARS = GTAPI.register(GTFluid.class, new GTFluid(GTCore.ID,"fiery_tears", prepareFluidType(false), prepareProperties(), prepareClientExtensions("fiery_tears")));
    public static final GTFluid BEET_JUICE = GTAPI.register(GTFluid.class, new GTFluid(GTCore.ID, "beet_juice", prepareFluidType(false), prepareProperties(), prepareClientExtensions("beet_juice")));

    public static void init(){

    }

    private static IClientFluidTypeExtensions prepareClientExtensions(String name){
        return GTClientFluidTypeExtension.builder().stillTexture(new ResourceLocation(GTCore.ID, "fluid/" + name)).flowingTexture(new ResourceLocation(GTCore.ID, "fluid/" + name)).overlayTexture(OVERLAY_TEXTURE).build();
    }

    private static Block.Properties prepareLavaProperties() {
        return Block.Properties.of(Material.LAVA).strength(100.0F).noLootTable().lightLevel(s -> 9);
    }

    private static FluidType.Properties prepareFluidType(boolean lava) {
        Properties properties = lava ? net.minecraftforge.fluids.FluidType.Properties.create().sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA) : net.minecraftforge.fluids.FluidType.Properties.create().sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL);
        if (lava) properties.lightLevel(9).viscosity(3000).density(6000).temperature(1200);
        return properties;
    }

    private static Block.Properties prepareProperties() {
        return Block.Properties.of(net.minecraft.world.level.material.Material.WATER).strength(100.0F).noLootTable();
    }
}
