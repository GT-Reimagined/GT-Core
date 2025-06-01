package org.gtreimagined.gtcore.machine;


import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.BlockMaterialChest;
import org.gtreimagined.gtcore.blockentity.BlockEntityChest;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.texture.Texture;

import static org.gtreimagined.gtlib.gui.SlotType.STORAGE;
import static org.gtreimagined.gtlib.machine.MachineFlag.*;


public class ChestMachine extends MaterialMachine {
    public ChestMachine(String domain, Material material, boolean addSlots) {
        super(domain, material.getId() + "_chest", material);
        setBlock(BlockMaterialChest::new);
        setItemBlockClass(() -> BlockMaterialChest.class);
        this.setTile(BlockEntityChest::new);
        this.setTiers(Tier.NONE);
        this.addFlags(ITEM, GUI);
        this.tesr().noOutputCover();
        this.getGui().setPlayerYOffset(56).setPlayerXOffset(4);
        this.getGui().setYSize(222).setXSize(184).setBackgroundTexture("chest_base");
        this.overlayTexture((type, state, tier, i) -> new Texture[] {
                new Texture(GTCore.ID, "model/material_chest_overlay_inventory"),
                new Texture(GTCore.ID, "model/material_chest_overlay_inventory"),
                new Texture(GTCore.ID, "model/material_chest_overlay_inventory"),
                new Texture(GTCore.ID, "model/material_chest_overlay_inventory"),
                new Texture(GTCore.ID, "model/material_chest_overlay_inventory"),
                new Texture(GTCore.ID, "model/material_chest_overlay_inventory"),
        });
        this.baseTexture((m, t, s) -> new Texture[] {
                new Texture(GTCore.ID, "model/material_chest_particle"),
                new Texture(GTCore.ID, "model/material_chest_particle"),
                new Texture(GTCore.ID, "model/material_chest_particle"),
                new Texture(GTCore.ID, "model/material_chest_particle"),
                new Texture(GTCore.ID, "model/material_chest_particle"),
                new Texture(GTCore.ID, "model/material_chest_particle"),
        });
        this.removeFlags(COVERABLE);
        if (addSlots){
            for (int y = 0; y < 6; y++){
                for (int x = 0; x < 9; ++x) {
                    this.add(STORAGE, 12 + x * 18, 18 + (y * 18));
                }
            }
        }
        GTAPI.register(ChestMachine.class, this);
    }
}
