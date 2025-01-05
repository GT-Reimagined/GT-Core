package org.gtreimagined.gtcore.machine;

import muramasa.antimatter.machine.MachineFlag;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import org.gtreimagined.gtcore.block.BlockGTHopper;
import org.gtreimagined.gtcore.blockentity.BlockEntityGTHopper;

public class HopperMachine extends MaterialMachine{
    public HopperMachine(String domain, String id, Material material) {
        super(domain, id, material);
        this.setTiers(Tier.NONE);
        setItemBlockClass(() -> BlockGTHopper.class);
        this.setBlock((type, tier) -> new BlockGTHopper(this, tier));
        setTile(BlockEntityGTHopper::new);
        removeFlags(MachineFlag.COVERABLE);
    }
}
