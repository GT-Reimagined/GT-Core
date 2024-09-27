package io.github.gregtechintergalactical.gtcore.machine;


import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.block.BlockMaterialChest;
import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityChest;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.texture.ITextureHandler;
import muramasa.antimatter.texture.Texture;

import static muramasa.antimatter.gui.SlotType.STORAGE;
import static muramasa.antimatter.machine.MachineFlag.*;

public class ChestMachine extends MaterialMachine {
    public ChestMachine(String domain, Material material, boolean addSlots) {
        super(domain, "chest", material);
        setBlock(BlockMaterialChest::new);
        setItemBlockClass(() -> BlockMaterialChest.class);
        this.setTile(BlockEntityChest::new);
        this.setTiers(Tier.NONE);
        this.addFlags(ITEM, GUI);
        this.tesr().noCovers();
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
        AntimatterAPI.register(ChestMachine.class, this);
    }
}
