package org.gtreimagined.gtcore.machine;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.machine.MachineFlag;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.BlockGTHopper;
import org.gtreimagined.gtcore.blockentity.BlockEntityGTHopper;

import static muramasa.antimatter.machine.MachineFlag.GUI;
import static muramasa.antimatter.machine.MachineFlag.ITEM;

public class HopperMachine extends MaterialMachine{
    public HopperMachine(String domain, Material material, boolean queue, int slots) {
        super(domain, material.getId() + (queue ? "_queue" : "") + "_hopper", material);
        this.setTiers(Tier.NONE);
        setItemBlockClass(() -> BlockGTHopper.class);
        this.setBlock((type, tier) -> new BlockGTHopper(this, tier));
        setTile(BlockEntityGTHopper::new);
        addFlags(ITEM, GUI);
        removeFlags(MachineFlag.COVERABLE);
        if (slots <= 0) throw new IllegalArgumentException("slots cannot be <= 0!");
        AntimatterAPI.register(HopperMachine.class, this);
        int startY;
        int rows;
        if (slots > 27 || slots == 16 || slots == 20){
            startY = 12;
            rows = 4;
        } else if (slots > 18 || slots == 9 || slots == 15){
            startY = 21;
            rows = 3;
        } else if (slots > 9 || slots == 8 || slots == 6 || slots == 4){
            startY = 30;
            rows = 2;
        } else {
            startY = 39;
            rows = 1;
        }
        int startX = 8;
        if (slots % 8 == 0){
            startX = 53;
        } else if (slots % 12 == 0){
            startX = 35;
        } else if (slots % 5 == 0 && slots <= 20){
            startX = 44;
        } else if (slots % 7 == 0){
            startX = 26;
        } else if (slots == 9 || slots == 6 || slots == 3){
            startX = 62;
        } else if (slots == 4 || slots == 2){
            startX = 71;
        } else if (slots == 1){
            startX = 80;
        }
        int columns = slots / rows;
        for (int i = 0; i < slots; i++) {
            int currentRow = i / rows;
            int currentColumn = i % columns;
            add(SlotType.STORAGE, startX + (18 * currentColumn), startY + (18 * currentRow));
        }
    }
}
