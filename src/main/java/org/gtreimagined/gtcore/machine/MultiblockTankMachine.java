package org.gtreimagined.gtcore.machine;

import com.gtnewhorizon.structurelib.structure.StructureUtility;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityLargeTank;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.machine.MachineFlag;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.structure.FakeTileElement;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.Utils;

import java.util.function.Supplier;

public class MultiblockTankMachine extends MaterialBasicMultiMachine {
    @Getter final int capacity;
    @Getter final boolean small;
    @Getter final Supplier<Block> casing;
    @Getter boolean acidProof;
    @Getter boolean gasProof;
    @Getter boolean magicProof;
    @Getter int maxHeat = Integer.MAX_VALUE;
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
            tooltip.add(Utils.translatable("tooltip.gtcore.large_tank.0", machine.getName()));
            tooltip.add(Utils.translatable("tooltip.gtcore." + (small ? "small": "large") + "_tank.1", casing.get().getName()));
            tooltip.add(Utils.translatable("machine.drum.capacity", capacity));
        });
        addFlags(MachineFlag.FLUID);
        setTile((type1, pos, state1) -> new BlockEntityLargeTank(this, pos, state1));
        this.capacity = capacity;
        this.small = small;
        this.casing = casing;
        String prefix = material.has(MaterialTags.WOOD) ? "wood" : "metal";
        setBaseTexture(new Texture(GTCore.ID, "block/casing/wall/" + prefix));
        setOverlayTextures((type, state, tier, i) -> {
            Texture blank = new Texture(GTCore.ID, "block/machine/empty");
            return new Texture[]{blank, blank, blank, new Texture(GTCore.ID, "block/casing/wall/" + prefix + "_tank_side_overlay"), blank, blank};
        });
        setVerticalFacingAllowed(true);
        GTAPI.register(MultiblockTankMachine.class, this);
    }

    public MultiblockTankMachine acidProof(){
        acidProof = true;
        return this;
    }

    public MultiblockTankMachine gasProof(){
        gasProof = true;
        return this;
    }

    public MultiblockTankMachine magicProof(){
        magicProof = true;
        return this;
    }

    public MultiblockTankMachine maxHeat(int maxHeat) {
        this.maxHeat = maxHeat;
        return this;
    }
}
