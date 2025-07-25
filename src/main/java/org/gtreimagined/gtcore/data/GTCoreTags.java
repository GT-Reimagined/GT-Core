package org.gtreimagined.gtcore.data;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.util.TagUtils;

public class GTCoreTags {
    public static final TagKey<Fluid> STEAM =  TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation("forge", "steam"));
    public static TagKey<Item> PLATES_IRON_ALUMINIUM = getTag("plates/ironaluminium");
    public static TagKey<Item> CIRCUITS_BASIC = getTag("circuits/basic");
    public static TagKey<Item> CIRCUITS_GOOD = getTag("circuits/good");
    public static TagKey<Item> CIRCUITS_ADVANCED = getTag("circuits/advanced");
    public static TagKey<Item> CIRCUITS_COMPLEX = getTag("circuits/complex");
    public static TagKey<Item> CIRCUITS_DATA = getTag("circuits/data");
    public static TagKey<Item> CIRCUITS_ELITE = getTag("circuits/elite");
    public static TagKey<Item> CIRCUITS_MASTER = getTag("circuits/master");
    public static TagKey<Item> CIRCUITS_ULTIMATE = getTag("circuits/ultimate");
    public static TagKey<Item> CIRCUITS_DATA_ORB = getTag("circuits/data_orb");
    public static TagKey<Item> DUST_LAPIS_LAZURITE = getTag("dusts/lapislazurite");
    public static TagKey<Item> DUST_COALS = getTag("dusts/coals");
    public static TagKey<Item> BATTERIES_LV = getTag("batteries/lv");
    public static TagKey<Item> BATTERIES_MV = getTag("batteries/mv");
    public static TagKey<Item> BATTERIES_HV = getTag("batteries/hv");
    public static TagKey<Item> POWER_UNIT_LV = getTag("power_units/lv");
    public static TagKey<Item> POWER_UNIT_MV = getTag("power_units/mv");
    public static TagKey<Item> POWER_UNIT_HV = getTag("power_units/hv");
    public static TagKey<Item> POWER_UNIT_SMALL = getTag("power_units/small");
    public static TagKey<Item> POWER_UNIT_JACKHAMMER = getTag("power_units/jackhammer");
    public static TagKey<Item> FIRESTARTER = TagUtils.getItemTag(new ResourceLocation(GTCore.ID, "firestarter"));
    public static TagKey<Item> MAGNETIC_TOOL = TagUtils.getItemTag(new ResourceLocation(GTCore.ID, "magnetic_tool"));
    public static TagKey<Item> INGOTS_MIXED_METAL = getTag("ingots/mixed_metal");
    public static final TagKey<Item> RUBBER_LOGS = TagUtils.getItemTag(new ResourceLocation(GTCore.ID, "rubber_logs"));
    public static final TagKey<Item> BOOKS_NORMAL = TagUtils.getItemTag(new ResourceLocation(GTCore.ID, "books/normal"));
    public static final TagKey<Item> BOOKS_ENCHANTED = TagUtils.getItemTag(new ResourceLocation(GTCore.ID, "books/enchanted"));
    public static final TagKey<Item> STONE_ROCKS = TagUtils.getItemTag(new ResourceLocation(GTCore.ID, "rocks/stone"));
    public static final TagKey<Item> ORE_ROCKS = TagUtils.getItemTag(new ResourceLocation(GTCore.ID, "rocks/ore"));

    public static TagKey<Item> getTag(String id){
        return TagUtils.getForgelikeItemTag(id);
    }
}
