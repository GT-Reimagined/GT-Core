package org.gtreimagined.gtcore.datagen;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.AntimatterTags;
import muramasa.antimatter.datagen.providers.AntimatterBlockTagProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemTagProvider;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.pipe.types.Cable;
import muramasa.antimatter.pipe.types.Wire;
import muramasa.antimatter.tool.IAntimatterTool;
import muramasa.antimatter.util.TagUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreCables;
import org.gtreimagined.gtcore.data.GTCoreTags;

import java.util.Objects;
import java.util.Set;

import static muramasa.antimatter.material.MaterialTags.*;
import static org.gtreimagined.gtcore.data.GTCoreItems.*;
import static org.gtreimagined.gtcore.data.GTCoreMaterials.*;
import static org.gtreimagined.gtcore.data.GTCoreTags.*;

public class GTCoreItemTagProvider extends AntimatterItemTagProvider {
    public GTCoreItemTagProvider(String providerDomain, String providerName, boolean replace, AntimatterBlockTagProvider p) {
        super(providerDomain, providerName, replace, p);
    }

    @Override
    protected void processTags(String domain) {
        super.processTags(domain);
        processSubtags();
        this.copy(TagUtils.getBlockTag(new ResourceLocation(GTCore.ID, "rubber_logs")), GTCoreTags.RUBBER_LOGS);
        this.tag(ItemTags.LEAVES).add(GTCoreBlocks.RUBBER_LEAVES.asItem());
        this.tag(ItemTags.PLANKS).add(GTCoreBlocks.RUBBER_PLANKS.asItem());
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
        this.tag(AntimatterTags.RADIATION_PROOF).add(UniversalHazardSuitMask, UniversalHazardSuitShirt, UniversalHazardSuitPants, UniversalHazardSuitBoots);
        if (AntimatterAPI.isModLoaded("farmersdelight")) {
            this.tag(TagUtils.getItemTag(new ResourceLocation("farmersdelight:tools/knives"))).addTag(AntimatterDefaultTools.KNIFE.getTag());
        }
        if (AntimatterAPI.isModLoaded("tfc")){
            this.tag(ItemTags.WOODEN_FENCES).add(AntimatterAPI.get(Item.class, "rubber_log_fence", GTCore.ID));
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "lumber"))).add(AntimatterAPI.get(Item.class, "rubber_lumber", GTCore.ID));
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "twigs"))).add(AntimatterAPI.get(Item.class, "rubber_twig", GTCore.ID));
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "fallen_leaves"))).add(AntimatterAPI.get(Item.class, "rubber_fallen_leaves", GTCore.ID));
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "firepit_fuel"))).addTag(GTCoreTags.RUBBER_LOGS);
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "firepit_logs"))).addTag(GTCoreTags.RUBBER_LOGS);
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "firepit_fuel"))).addTag(GTCoreTags.RUBBER_LOGS);
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "pit_kiln_logs"))).addTag(GTCoreTags.RUBBER_LOGS);
            this.tag(TagUtils.getItemTag(new ResourceLocation("tfc", "log_pile_logs"))).addTag(GTCoreTags.RUBBER_LOGS);

        }
        this.tag(CIRCUITS_BASIC).add(CircuitBasic);
        this.tag(CIRCUITS_GOOD).add(CircuitGood);
        this.tag(CIRCUITS_ADVANCED).add(CircuitAdv);
        this.tag(CIRCUITS_COMPLEX).add(CircuitComplex);
        this.tag(CIRCUITS_DATA).add(CircuitDataStorage);
        this.tag(CIRCUITS_ELITE).add(CircuitDataControl);
        this.tag(CIRCUITS_MASTER).add(CircuitEnergyFlow);
        this.tag(CIRCUITS_FUTURISTIC).add(CircuitFuturistic);
        this.tag(CIRCUITS_3D).add(Circuit3D);
        this.tag(CIRCUITS_INFINITE).add(CircuitInfinite);
        this.tag(CIRCUITS_DATA_ORB).add(DataOrb);
        this.tag(BATTERIES_RE).add(BatteryRE);
        this.tag(BATTERIES_SMALL).add(BatterySmallSodium, BatterySmallCadmium, BatterySmallLithium);
        this.tag(BATTERIES_MEDIUM).add(BatteryMediumSodium, BatteryMediumCadmium, BatteryMediumLithium);
        this.tag(BATTERIES_LARGE).add(BatteryLargeSodium, BatteryLargeCadmium, BatteryLargeLithium, EnergyCrystal);
        if (AntimatterAPI.isModLoaded("curios")){
            this.tag(TagUtils.getItemTag(new ResourceLocation("curios", "belt"))).addTag(BATTERIES_RE).addTag(BATTERIES_SMALL).addTag(BATTERIES_MEDIUM).addTag(BATTERIES_LARGE);
        }
        this.tag(POWER_UNIT_LV).add(PowerUnitLV);
        this.tag(POWER_UNIT_MV).add(PowerUnitMV);
        this.tag(POWER_UNIT_HV).add(PowerUnitHV);
        this.tag(POWER_UNIT_SMALL).add(SmallPowerUnit);
        this.tag(POWER_UNIT_JACKHAMMER).add(JackhammerPowerUnit);
        this.tag(FIRESTARTER).add(Items.FLINT_AND_STEEL, Match, Lighter, MatchBook);
        this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TWILIGHT, "banned_uncraftables"))).add(AntimatterAPI.all(Item.class).toArray(Item[]::new));
        this.tag(MAGNETIC_TOOL).add(
                AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_lv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_mv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_hv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "drill_lv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "drill_mv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "drill_hv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_alt_lv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_alt_mv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_alt_hv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "chainsaw_lv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "chainsaw_mv", GTCore.ID).getItem(),
                AntimatterAPI.get(IAntimatterTool.class, "chainsaw_hv", GTCore.ID).getItem());
        Material[] materials = new Material[]{IronMagnetic, SteelMagnetic, NeodymiumMagnetic};
        for (Material material : materials) {
            if (material.has(TOOLS)){
                this.tag(MAGNETIC_TOOL).add(AntimatterDefaultTools.PICKAXE.getToolItem(material), AntimatterDefaultTools.WRENCH.getToolItem(material),
                        AntimatterDefaultTools.WRENCH_ALT.getToolItem(material), AntimatterDefaultTools.SHOVEL.getToolItem(material), AntimatterDefaultTools.AXE.getToolItem(material));
            }
        }
    }

    protected void processSubtags() {
        for (PipeSize value : PipeSize.values()) {
            Set<Material> mats = WIRE.allSub(GTCoreCables.TIN_WIRE);
            if (mats.size() > 0) {
                this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.ID, GTCoreCables.TIN_WIRE.getId() + "_" + value.getId()))).add(mats.stream().map(t ->
                        AntimatterAPI.get(Wire.class, "wire_" + t.getId())).filter(Objects::nonNull).map(t -> t.getBlockItem(value)).toArray(Item[]::new));
            }
            mats = CABLE.allSub(GTCoreCables.TIN_CABLE);
            if (mats.size() > 0) {
                this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.ID, GTCoreCables.TIN_CABLE.getId() + "_" + value.getId()))).add(mats.stream().map(t ->
                        AntimatterAPI.get(Cable.class, "cable_" + t.getId())).filter(Objects::nonNull).map(t -> t.getBlockItem(value)).toArray(Item[]::new));
            }
        }
    }
}
