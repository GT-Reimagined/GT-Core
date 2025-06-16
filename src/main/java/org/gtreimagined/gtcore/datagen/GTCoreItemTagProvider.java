package org.gtreimagined.gtcore.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreCables;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTLibMaterials;
import org.gtreimagined.gtlib.data.GTLibTags;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.providers.GTBlockTagProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemTagProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.pipe.types.Cable;
import org.gtreimagined.gtlib.pipe.types.Wire;
import org.gtreimagined.gtlib.tool.IGTTool;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.Objects;
import java.util.Set;

import static org.gtreimagined.gtcore.data.GTCoreItems.*;
import static org.gtreimagined.gtcore.data.GTCoreMaterials.*;
import static org.gtreimagined.gtcore.data.GTCoreTags.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.PLATE;
import static org.gtreimagined.gtlib.material.MaterialTags.*;

public class GTCoreItemTagProvider extends GTItemTagProvider {
    public GTCoreItemTagProvider(String providerDomain, String providerName, boolean replace, GTBlockTagProvider p) {
        super(providerDomain, providerName, replace, p);
    }

    @Override
    protected void processTags(String domain) {
        super.processTags(domain);
        processSubtags();
        this.copy(TagUtils.getBlockTag(new ResourceLocation(GTCore.ID, "rubber_logs")), GTCoreTags.RUBBER_LOGS);
        this.tag(ItemTags.LEAVES).add(GTCoreBlocks.RUBBER_LEAVES.asItem());
        this.tag(ItemTags.PLANKS).add(GTCoreBlocks.RUBBER_PLANKS.asItem(), PLATE.get(GTLibMaterials.Wood));
        this.tag(ItemTags.SLABS).add(GTCoreBlocks.RUBBER_SLAB.asItem());
        this.tag(ItemTags.STAIRS).add(GTCoreBlocks.RUBBER_STAIRS.asItem());
        this.tag(ItemTags.SIGNS).add(GTCoreBlocks.RUBBER_SIGN.asItem());
        this.tag(ItemTags.WOODEN_BUTTONS).add(GTCoreBlocks.RUBBER_BUTTON.asItem());
        this.tag(ItemTags.WOODEN_DOORS).add(GTCoreBlocks.RUBBER_DOOR.asItem());
        this.tag(ItemTags.WOODEN_FENCES).add(GTCoreBlocks.RUBBER_FENCE.asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(GTCoreBlocks.RUBBER_PRESSURE_PLATE.asItem());
        this.tag(ItemTags.WOODEN_SLABS).add(GTCoreBlocks.RUBBER_SLAB.asItem());
        this.tag(ItemTags.WOODEN_STAIRS).add(GTCoreBlocks.RUBBER_STAIRS.asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(GTCoreBlocks.RUBBER_TRAPDOOR.asItem());
        this.tag(INGOTS_MIXED_METAL).add(MixedMetalIngot);
        this.tag(BOOKS_NORMAL).add(Items.BOOK, Items.WRITABLE_BOOK, Items.WRITTEN_BOOK);
        this.tag(BOOKS_ENCHANTED).add(Items.ENCHANTED_BOOK);
        if (GTAPI.isModLoaded("computercraft")){
            this.tag(BOOKS_NORMAL).add(RegistryUtils.getItemFromID("computercraft", "printed_book"));
        }
        this.tag(GTLibTags.RADIATION_PROOF).add(UniversalHazardSuitMask, UniversalHazardSuitShirt, UniversalHazardSuitPants, UniversalHazardSuitBoots);
        if (GTAPI.isModLoaded("farmersdelight")) {
            this.tag(TagUtils.getItemTag(new ResourceLocation("farmersdelight:tools/knives"))).addTag(GTTools.KNIFE.getTag());
        }
        if (GTAPI.isModLoaded("tfc")){
            this.tag(ItemTags.WOODEN_FENCES).add(GTAPI.get(Item.class, "rubber_log_fence", GTCore.ID));
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "lumber"))).add(GTAPI.get(Item.class, "rubber_lumber", GTCore.ID));
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "twigs"))).add(GTAPI.get(Item.class, "rubber_twig", GTCore.ID));
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "fallen_leaves"))).add(GTAPI.get(Item.class, "rubber_fallen_leaves", GTCore.ID));
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "firepit_fuel"))).addTag(GTCoreTags.RUBBER_LOGS);
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "firepit_logs"))).addTag(GTCoreTags.RUBBER_LOGS);
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "firepit_fuel"))).addTag(GTCoreTags.RUBBER_LOGS);
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "pit_kiln_logs"))).addTag(GTCoreTags.RUBBER_LOGS);
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "log_pile_logs"))).addTag(GTCoreTags.RUBBER_LOGS);

        }

        if (GTAPI.isModLoaded("curios")){
            this.tag(TagUtils.getItemTag(new ResourceLocation("curios", "belt"))).addTag(BATTERIES_LV).addTag(BATTERIES_MV).addTag(BATTERIES_HV);
        }
        this.tag(POWER_UNIT_LV).add(PowerUnitLV);
        this.tag(POWER_UNIT_MV).add(PowerUnitMV);
        this.tag(POWER_UNIT_HV).add(PowerUnitHV);
        this.tag(POWER_UNIT_SMALL).add(SmallPowerUnit);
        this.tag(POWER_UNIT_JACKHAMMER).add(JackhammerPowerUnit);
        this.tag(FIRESTARTER).add(Items.FLINT_AND_STEEL, Match, Lighter, MatchBook);
        this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TWILIGHT, "banned_uncraftables"))).add(GTAPI.all(Item.class).toArray(Item[]::new));
        this.tag(MAGNETIC_TOOL).add(
                GTAPI.get(IGTTool.class, "electric_wrench_lv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "electric_wrench_mv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "electric_wrench_hv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "drill_lv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "drill_mv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "drill_hv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "electric_wrench_alt_lv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "electric_wrench_alt_mv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "electric_wrench_alt_hv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "chainsaw_lv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "chainsaw_mv", GTCore.ID).getItem(),
                GTAPI.get(IGTTool.class, "chainsaw_hv", GTCore.ID).getItem());
        Material[] materials = new Material[]{IronMagnetic, SteelMagnetic, NeodymiumMagnetic};
        for (Material material : materials) {
            if (material.has(TOOLS)){
                this.tag(MAGNETIC_TOOL).add(GTTools.PICKAXE.getToolItem(material), GTTools.WRENCH.getToolItem(material),
                        GTTools.WRENCH_ALT.getToolItem(material), GTTools.SHOVEL.getToolItem(material), GTTools.AXE.getToolItem(material));
            }
        }
    }

    protected void processSubtags() {
        for (PipeSize value : PipeSize.values()) {
            Set<Material> mats = WIRE.allSub(GTCoreCables.TIN_WIRE);
            if (mats.size() > 0) {
                this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.ID, GTCoreCables.TIN_WIRE.getId() + "_" + value.getId()))).add(mats.stream().map(t ->
                        GTAPI.get(Wire.class, "wire_" + t.getId())).filter(Objects::nonNull).map(t -> t.getBlockItem(value)).toArray(Item[]::new));
            }
            mats = CABLE.allSub(GTCoreCables.TIN_CABLE);
            if (mats.size() > 0) {
                this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.ID, GTCoreCables.TIN_CABLE.getId() + "_" + value.getId()))).add(mats.stream().map(t ->
                        GTAPI.get(Cable.class, "cable_" + t.getId())).filter(Objects::nonNull).map(t -> t.getBlockItem(value)).toArray(Item[]::new));
            }
        }
    }
}
