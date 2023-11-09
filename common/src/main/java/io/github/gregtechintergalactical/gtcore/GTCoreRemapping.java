package io.github.gregtechintergalactical.gtcore;

import io.github.gregtechintergalactical.gtcore.data.GTCoreBlocks;
import io.github.gregtechintergalactical.gtcore.machine.DrumMachine;
import io.github.gregtechintergalactical.gtcore.machine.LockerMachine;
import io.github.gregtechintergalactical.gtcore.machine.WorkbenchMachine;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.AntimatterRemapping;
import muramasa.antimatter.machine.BlockMachine;
import muramasa.antimatter.registration.IAntimatterObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class GTCoreRemapping {

    public static void init(){
        AntimatterAPI.all(DrumMachine.class, GTCore.ID).forEach(m -> {
            AntimatterRemapping.remapMachine(new ResourceLocation("gtutility", m.getId()), m);
        });
        AntimatterAPI.all(WorkbenchMachine.class, GTCore.ID).forEach(m -> {
            AntimatterRemapping.remapMachine(new ResourceLocation("gtutility", m.getId()), m);
        });
        AntimatterAPI.all(LockerMachine.class, GTCore.ID).forEach(m -> {
            AntimatterRemapping.remapMachine(new ResourceLocation("gtutility", m.getId()), m);
        });
        AntimatterAPI.all(Block.class, GTCore.ID).stream().filter(b -> b instanceof IAntimatterObject && !(b instanceof BlockMachine)).map(b -> (IAntimatterObject)b).forEach(b -> {
            AntimatterRemapping.remap(new ResourceLocation("gtrubber", b.getId()), b.getLoc());
        });
        AntimatterAPI.all(Item.class, GTCore.ID).stream().filter(b -> b instanceof IAntimatterObject).map(b -> (IAntimatterObject)b).forEach(b -> {
            AntimatterRemapping.remap(new ResourceLocation("gtrubber", b.getId()), b.getLoc());
            AntimatterRemapping.remap(new ResourceLocation("gti", b.getId()), b.getLoc());
            AntimatterRemapping.remap(new ResourceLocation("gregtech", b.getId()), b.getLoc());
            AntimatterRemapping.remap(new ResourceLocation("gt4r", b.getId()), b.getLoc());
        });
        AntimatterRemapping.remapBlockEntity(new ResourceLocation("gtrubber", "sap_bag"), new ResourceLocation(GTCore.ID, "sap_bag"));
        AntimatterRemapping.remapMachine("wood_item_barrel", GTCoreBlocks.WOOD_ITEM_BARREL);
        if (GTCoreBlocks.IRONWOOD_ITEM_BARREL != null){
            AntimatterRemapping.remapMachine("ironwood_item_barrel", GTCoreBlocks.IRONWOOD_ITEM_BARREL);
        }
    }
}
