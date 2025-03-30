package org.gtreimagined.gtcore;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.machine.DrumMachine;
import org.gtreimagined.gtcore.machine.LockerMachine;
import org.gtreimagined.gtcore.machine.WorkbenchMachine;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTRemapping;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.machine.BlockMachine;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.registration.IGTObject;

public class GTCoreRemapping {

    public static void init(){
        GTAPI.all(DrumMachine.class, GTCore.ID).forEach(m -> {
            GTRemapping.remapMachine(new ResourceLocation("gtutility", m.getId()), m);
        });
        GTAPI.all(WorkbenchMachine.class, GTCore.ID).forEach(m -> {
            GTRemapping.remapMachine(new ResourceLocation("gtutility", m.getId()), m);
        });
        GTAPI.all(LockerMachine.class, GTCore.ID).forEach(m -> {
            GTRemapping.remapMachine(new ResourceLocation("gtutility", m.getId()), m);
        });
        GTAPI.all(Block.class, GTCore.ID).stream().filter(b -> b instanceof IGTObject && !(b instanceof BlockMachine)).map(b -> (IGTObject)b).forEach(b -> {
            GTRemapping.remap(new ResourceLocation("gtrubber", b.getId()), b.getLoc());
        });
        GTAPI.all(Item.class, GTCore.ID).stream().filter(b -> b instanceof IGTObject).map(b -> (IGTObject)b).forEach(b -> {
            GTRemapping.remap(new ResourceLocation("gtrubber", b.getId()), b.getLoc());
            GTRemapping.remap(new ResourceLocation("gti", b.getId()), b.getLoc());
            GTRemapping.remap(new ResourceLocation("gregtech", b.getId()), b.getLoc());
            GTRemapping.remap(new ResourceLocation("gt4r", b.getId()), b.getLoc());
        });
        GTRemapping.remapBlockEntity(new ResourceLocation("gtrubber", "sap_bag"), new ResourceLocation(GTCore.ID, "sap_bag"));
        GTRemapping.remapMachine("wood_item_barrel", GTCoreBlocks.WOOD_ITEM_BARREL);
        if (GTCoreBlocks.IRONWOOD_ITEM_BARREL != null){
            GTRemapping.remapMachine("ironwood_item_barrel", GTCoreBlocks.IRONWOOD_ITEM_BARREL);
        }
        GTRemapping.remap(GTCore.ID, "sword_head_shape", "sword_blade_shape");
        GTRemapping.remap(GTCore.ID, "saw_head_shape", "saw_blade_shape");
        Tier[] tiers = new Tier[] {Tier.LV, Tier.MV, Tier.HV};
        for (Tier tier : tiers) {
            GTRemapping.remap(new ResourceLocation(Ref.SHARED_ID, "drill_" + tier.getId()), new ResourceLocation(GTCore.ID, "drill_" + tier.getId()));
            GTRemapping.remap(new ResourceLocation(Ref.SHARED_ID, "buzzsaw_" + tier.getId()), new ResourceLocation(GTCore.ID, "buzzsaw_" + tier.getId()));
            GTRemapping.remap(new ResourceLocation(Ref.SHARED_ID, "electric_screwdriver_" + tier.getId()), new ResourceLocation(GTCore.ID, "electric_screwdriver_" + tier.getId()));
            GTRemapping.remap(new ResourceLocation(Ref.SHARED_ID, "electric_wrench_" + tier.getId()), new ResourceLocation(GTCore.ID, "electric_wrench_" + tier.getId()));
            GTRemapping.remap(new ResourceLocation(Ref.SHARED_ID, "electric_wrench_alt_" + tier.getId()), new ResourceLocation(GTCore.ID, "electric_wrench_alt_" + tier.getId()));
            GTRemapping.remap(new ResourceLocation(Ref.SHARED_ID, "chainsaw_" + tier.getId()), new ResourceLocation(GTCore.ID, "chainsaw_" + tier.getId()));
        }
        GTRemapping.remap(new ResourceLocation(Ref.SHARED_ID, "jackhammer_hv"), new ResourceLocation(GTCore.ID, "jackhammer_hv"));
    }
}
