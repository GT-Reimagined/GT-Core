package org.gtreimagined.gtcore.machine;

import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityBarrel;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.texture.Texture;
import org.gtreimagined.gtcore.blockentity.BlockEntityBarrel;

import static muramasa.antimatter.gui.SlotType.STORAGE;
import static muramasa.antimatter.machine.MachineFlag.*;

public class BarrelMachine extends MaterialMachine{
    public BarrelMachine(String domain, Material material, boolean addSlots) {
        super(domain, material.getId() + "_barrel", material);
        this.setTile(BlockEntityBarrel::new);
        this.setTiers(Tier.NONE);
        this.addFlags(ITEM, GUI);
        this.tesr().noCovers().setVerticalFacingAllowed(true);
        this.getGui().setPlayerYOffset(56).setPlayerXOffset(4);
        this.getGui().setYSize(222).setXSize(184).setBackgroundTexture("chest_base");
        this.overlayTexture((type, state, tier, i) -> new Texture[] {
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/side"),
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/side"),
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/bottom"),
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/top" + (state == MachineState.ACTIVE ? "_open" : "")),
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/right"),
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/left"),
        });
        this.baseTexture((m, t, s) -> new Texture[] {
                new Texture(GTCore.ID, "block/machine/base/item_barrel/side"),
                new Texture(GTCore.ID, "block/machine/base/item_barrel/side"),
                new Texture(GTCore.ID, "block/machine/base/item_barrel/bottom"),
                new Texture(GTCore.ID, "block/machine/base/item_barrel/top" + (s == MachineState.ACTIVE ? "_open" : "")),
                new Texture(GTCore.ID, "block/machine/base/item_barrel/right"),
                new Texture(GTCore.ID, "block/machine/base/item_barrel/left"),
        });
        this.removeFlags(COVERABLE);
        if (addSlots){
            for (int y = 0; y < 6; y++){
                for (int x = 0; x < 9; ++x) {
                    this.add(STORAGE, 12 + x * 18, 18 + (y * 18));
                }
            }
        }
        AntimatterAPI.register(BarrelMachine.class, this);
    }
}
