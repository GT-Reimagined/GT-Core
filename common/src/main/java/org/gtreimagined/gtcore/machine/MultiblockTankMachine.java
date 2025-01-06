package org.gtreimagined.gtcore.machine;

import com.gtnewhorizon.structurelib.structure.StructureUtility;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.machine.MachineFlag;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialTags;
import muramasa.antimatter.structure.FakeTileElement;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityLargeTank;


import java.util.function.Supplier;

import static muramasa.antimatter.data.AntimatterMaterials.Wood;

public class MultiblockTankMachine extends MaterialBasicMultiMachine {
    final int capacity;
    final boolean small;
    final Supplier<Block> casing;
    boolean acidProof;
    boolean gasProof;
    int maxHeat = Integer.MAX_VALUE;
    public MultiblockTankMachine(String domain, Material material, boolean small, int capacity, Supplier<Block> casing) {
        super(domain, (small ? "small" : "large") + "_" + material.getId() + "_tank_main_valve", material);
        setTiers(Tier.NONE);
        if (small){
            this.setStructure(BlockEntityLargeTank.class, b -> b.part("main")
                    .of("CCC", "CCC", "CCC").of("C~C", "C-C", "CCC").of(0).build()
                    .atElement('C', StructureUtility.lazy(t -> new FakeTileElement<>(t.getCasing()))).offset(1, 1, 0)
                    .build());
        } else {
            this.setStructure(BlockEntityLargeTank.class, b -> b.part("main")
                    .of("CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC").of("CCCCC", "C---C", "C---C", "C---C", "CCCCC").of("CC~CC", "C---C", "C---C", "C---C", "CCCCC").of(1).of(0).build()
                    .atElement('C', StructureUtility.lazy(t -> new FakeTileElement<>(t.getCasing()))).offset(2, 2, 0)
                    .build());
        }
        addTooltipInfo((machine, stack, world, tooltip, flag) -> {
            tooltip.add(Utils.translatable("machine.drum.capacity", capacity));
        });
        addFlags(MachineFlag.FLUID);
        setTile((type1, pos, state1) -> new BlockEntityLargeTank(this, pos, state1));
        this.capacity = capacity;
        this.small = small;
        this.casing = casing;
        String prefix = material.has(MaterialTags.WOOD) ? "wood" : "metal";
        baseTexture(new Texture(GTCore.ID, "block/casing/wall/" + prefix));
        overlayTexture((type, state, tier, i) -> {
            Texture blank = new Texture(GTCore.ID, "block/machine/empty");
            return new Texture[]{blank, blank, blank, new Texture(GTCore.ID, "block/casing/wall/" + prefix + "_tank_side_overlay"), blank, blank};
        });
        setVerticalFacingAllowed(true);
        AntimatterAPI.register(MultiblockTankMachine.class, this);
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isSmall() {
        return small;
    }

    public Supplier<Block> getCasing() {
        return casing;
    }

    public MultiblockTankMachine acidProof(){
        acidProof = true;
        return this;
    }

    public boolean isAcidProof() {
        return acidProof;
    }

    public MultiblockTankMachine gasProof(){
        gasProof = true;
        return this;
    }

    public boolean isGasProof() {
        return gasProof;
    }

    public MultiblockTankMachine maxHeat(int maxHeat) {
        this.maxHeat = maxHeat;
        return this;
    }

    public int getMaxHeat() {
        return maxHeat;
    }
}
