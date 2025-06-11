package org.gtreimagined.gtcore.machine;

import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityBarrel;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.texture.Texture;

import static org.gtreimagined.gtlib.gui.SlotType.STORAGE;
import static org.gtreimagined.gtlib.machine.MachineFlag.*;


public class BarrelMachine extends MaterialMachine{
    public BarrelMachine(String domain, Material material, boolean addSlots) {
        super(domain, material.getId() + "_barrel", material);
        this.setTile(BlockEntityBarrel::new);
        this.setTiers(Tier.NONE);
        this.addFlags(ITEM, GUI);
        this.setRenderAsTesr().setNoOutputCover().setVerticalFacingAllowed(true);
        this.getGuiData().setPlayerYOffset(56).setPlayerXOffset(4);
        this.getGuiData().setYSize(222).setXSize(184).setBackgroundTexture("chest_base");
        this.setOverlayTextures((type, state, tier, i) -> new Texture[] {
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/side"),
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/side"),
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/bottom"),
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/top" + (state == MachineState.ACTIVE ? "_open" : "")),
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/right"),
                new Texture(GTCore.ID, "block/machine/overlay/item_barrel/left"),
        });
        this.setBaseTexture((m, t, s) -> new Texture[] {
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
        GTAPI.register(BarrelMachine.class, this);
    }
}
