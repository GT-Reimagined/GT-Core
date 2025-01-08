package org.gtreimagined.gtcore.datagen;

import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.BlockCasing;
import org.gtreimagined.gtcore.block.BlockRedstoneWire;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.item.ItemHazmatArmor;
import org.gtreimagined.gtcore.item.ItemSelectorTag;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.datagen.providers.AntimatterLanguageProvider;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.pipe.BlockItemPipe;
import muramasa.antimatter.pipe.BlockPipe;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.pipe.types.Cable;
import muramasa.antimatter.util.Utils;
import org.apache.commons.lang3.StringUtils;

import static org.gtreimagined.gtcore.data.GTCoreMaterials.Beeswax;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.util.Utils.lowerUnderscoreToUpperSpaced;

public class GTCoreLang {
    public static class en_US extends AntimatterLanguageProvider {

        public en_US() {
            this("en_us");
        }

        public en_US(String locale) {
            super(GTCore.ID, GTCore.NAME + " " + locale + " Localization", locale);
        }

        @Override
        protected void english(String domain, String locale) {
            super.english(domain, locale);
            add("machine.drum.fluid", "Contains %s L of %s");
            add("machine.drum.output", "Currently set to auto output");
            add("machine.drum.capacity", "Capacity: %sL(MB)");
            add("machine.mass_storage.contains", "Contains: %s of %s");
            add("machine.mass_storage.display", "Locked to: %s");
            add("machine.mass_storage.output", "Auto outputs");
            add("machine.mass_storage.output_overflow", "Auto outputs overflow");
            add("machine.mass_storage.capacity", "Can store items of one Type, Capacity: %s");
            add("machine.gtcore.stack_limit", "Outputs exactly %s items");
            add("machine.gtcore.no_stack_limit", "Outputs up to a full stack");
            add("tooltip.gtcore.redstone_wire_range", "Range: %s");
            add("tooltip.gtcore.redstone_wire_light", "Emits light");
            add(GTCoreBlocks.RUBBER_LEAVES, "Rubber Leaves");
            add(GTCoreBlocks.RUBBER_LOG, "Rubber Log");
            add(GTCoreBlocks.STRIPPED_RUBBER_LOG, "Stripped Rubber Log");
            add(GTCoreBlocks.RUBBER_WOOD, "Rubber Wood");
            add(GTCoreBlocks.STRIPPED_RUBBER_WOOD, "Stripped Rubber Wood");
            add(GTCoreBlocks.RUBBER_PLANKS, "Rubber Planks");
            add(GTCoreBlocks.RUBBER_SAPLING, "Rubber Sapling");
            add(GTCoreBlocks.RUBBER_SIGN, "Rubber Sign");
            add(GTCoreBlocks.RUBBER_DOOR, "Rubber Door");
            add(GTCoreBlocks.RUBBER_TRAPDOOR, "Rubber Trapdoor");
            add(GTCoreBlocks.RUBBER_BUTTON, "Rubber Button");
            add(GTCoreBlocks.RUBBER_PRESSURE_PLATE, "Rubber Pressure Plate");
            add(GTCoreBlocks.RUBBER_SLAB, "Rubber Slab");
            add(GTCoreBlocks.RUBBER_STAIRS, "Rubber Stairs");
            add(GTCoreBlocks.RUBBER_FENCE, "Rubber Fence");
            add(GTCoreBlocks.RUBBER_FENCE_GATE, "Rubber Fence Gate");
            add(GTCoreItems.RubberBoat, "Rubberwood Boat");
            add(GTCoreBlocks.SAP_BAG, "Sap Bag");
            add("block.gtcore.rubber_twig", "Rubber Twig");
            add("block.gtcore.rubber_fallen_leaves", "Rubber Fallen Leaves");
            add("block.gtcore.rubber_log_fence", "Rubber Log Fence");
            override(GTCoreItems.TapeEmpty.getDescriptionId(), "Tape");
            override(GTCoreItems.DuctTapeEmpty.getDescriptionId(), "Duct Tape");
            override(GTCoreItems.FALDuctTapeEmpty.getDescriptionId(), "BrainTech Aerospace Advanced Reinforced Duct Tape FAL-84");
            override(GTCoreItems.FALDuctTape.getDescriptionId(), "BrainTech Aerospace Advanced Reinforced Duct Tape FAL-84");
            override(GTCoreItems.LighterEmpty.getDescriptionId(), "Lighter (Empty)");
            override(GTCoreItems.Lighter.getDescriptionId(), "Lighter (Full)");
            override(GTCoreItems.GTCredit.getDescriptionId(), "GT Credit");
            add(GTCoreItems.Fertilizer, "Fertilizer");
            add("tooltip.gtcore.tape.used_roll", "Used Roll");
            add("tooltip.gtcore.tape.full_roll", "Full Roll");
            add("tooltip.gtcore.tape.can_fix_anything", "Can fix anything!*");
            add("tooltip.gtcore.tape.remaining_uses", "Remaining Uses: %s");
            add("tooltip.gtcore.pocket_multitool", "6 useful Tools in one!");
            add("tooltip.gtcore.pocket_multitool.switch_mode", "Sneak Rightclick to switch Mode");
            add("tooltip.gtcore.knife", "Can be used to harvest sticky resin from spots on rubber trees");
            add("tooltip.gtcore.selector_tag.0", "Right click to cycle mode forward");
            add("tooltip.gtcore.selector_tag.1", "Shift right click to cycle mode backward");
            AntimatterAPI.all(ItemBasic.class, domain).forEach(i -> override(i.getDescriptionId(), lowerUnderscoreToUpperSpaced(i.getId())
                    .replace("Lv", "(LV)")
                    .replace("Mv", "(MV)")
                    .replace("Hv", "(HV)")
                    .replace("Ev", "(EV)")
                    .replace("Iv", "(IV)")));
            AntimatterAPI.all(BlockCasing.class).forEach(b -> this.add(b, Utils.lowerUnderscoreToUpperSpaced(b.getId())));
            AntimatterAPI.all(ItemHazmatArmor.class, domain).forEach(i -> this.add(i, Utils.lowerUnderscoreToUpperSpaced(i.getId())));
        }

        @Override
        protected void overrides() {
            super.overrides();
            AntimatterAPI.all(BlockPipe.class).stream().filter(s -> s instanceof BlockRedstoneWire<?>).forEach(s -> {
                String type = s.getSize() == PipeSize.TINY ? "Cable" : "Wire";
                override(Ref.ID, s.getDescriptionId(), StringUtils.join(Utils.getLocalizedType(s.getType().getMaterial()), " ", type));
            });
            AntimatterAPI.all(ItemSelectorTag.class, GTCore.ID).forEach(i -> override(i.getDescriptionId(), "Selector Tag (" + i.circuitId + ")"));
            override(Ref.ID, DUST.get(Beeswax).getDescriptionId(), "Beeswax");
            override(Ref.ID, DUST_SMALL.get(Beeswax).getDescriptionId(), "Small Beeswax");
            override(Ref.ID, DUST_TINY.get(Beeswax).getDescriptionId(), "Tiny Beeswax");
        }
    }
}
