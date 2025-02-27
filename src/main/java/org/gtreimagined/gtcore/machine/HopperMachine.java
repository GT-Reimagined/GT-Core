package org.gtreimagined.gtcore.machine;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.machine.MachineFlag;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.util.Utils;
import org.gtreimagined.gtcore.block.BlockGTHopper;
import org.gtreimagined.gtcore.blockentity.BlockEntityGTHopper;

import static muramasa.antimatter.machine.MachineFlag.GUI;
import static muramasa.antimatter.machine.MachineFlag.ITEM;

public class HopperMachine extends MaterialMachine{
    public HopperMachine(String domain, Material material, int slots) {
        super(domain, material.getId() + "_hopper", material);
        this.setTiers(Tier.NONE);
        setItemBlockClass(() -> BlockGTHopper.class);
        this.setBlock((type, tier) -> new BlockGTHopper(this, tier));
        setTile(BlockEntityGTHopper::new);
        addFlags(ITEM, GUI);
        removeFlags(MachineFlag.COVERABLE);
        addTooltipInfo(Utils.translatable("machine.gtcore.hopper.slots", slots));
        addTooltipInfo(Utils.translatable("machine.gtcore.hopper.screwdriver"));
        if (slots <= 0) throw new IllegalArgumentException("slots cannot be <= 0!");
        if (slots > 36) throw new IllegalArgumentException("slots cannot be > 36!");
        AntimatterAPI.register(HopperMachine.class, this);
        int startY;
        int rows;
        if (slots > 27 || slots == 16 || slots == 20){
            startY = 8;
            rows = 4;
        } else if (slots > 18 || slots == 9 || slots == 15){
            startY = 17;
            rows = 3;
        } else if (slots > 9 || slots == 8 || slots == 6 || slots == 4){
            startY = 26;
            rows = 2;
        } else {
            startY = 35;
            rows = 1;
        }
        int startX = 8;
        int columns = 9;
        if (slots == 32){
            startX = 17;
            columns = 8;
        } else if (slots % 8 == 0){
            startX = 53;
            columns = 4;
        } else if (slots != 36 && slots % 12 == 0){
            startX = 35;
            columns = 6;
        } else if (slots % 5 == 0 && slots <= 20){
            startX = 44;
            columns = 5;
        } else if (slots % 7 == 0){
            startX = 26;
            columns = 7;
        } else if (slots == 9 || slots == 6 || slots == 3){
            startX = 62;
            columns = 3;
        } else if (slots == 4 || slots == 2){
            startX = 71;
            columns = 2;
        } else if (slots == 1){
            startX = 80;
            columns = 1;
        }
        int i = 0;
        outer:
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (i == slots) break outer;
                add(SlotType.STORAGE, startX + (18 * x), startY + (18 * y));
                i++;
            }
        }
    }
}
