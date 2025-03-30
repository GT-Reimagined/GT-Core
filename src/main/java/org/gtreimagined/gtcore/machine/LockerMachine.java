package org.gtreimagined.gtcore.machine;

import org.gtreimagined.gtcore.blockentity.BlockEntityLocker;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.machine.MachineFlag;
import org.gtreimagined.gtlib.material.Material;

import static org.gtreimagined.gtlib.gui.SlotType.ENERGY;
import static org.gtreimagined.gtlib.gui.SlotType.STORAGE;


public class LockerMachine extends ChargingMachine{
    public LockerMachine(String domain, Material material, boolean charge) {
        super(domain, material, "locker", charge);
        this.addFlags(MachineFlag.ITEM, MachineFlag.GUI);
        if (!charge){
            add(STORAGE, 80, 8).add(STORAGE, 80, 8 + (18)).add(STORAGE, 80, 8 + (2 * 18)).add(STORAGE, 80, 8 + (3 * 18));
        } else {
            add(ENERGY, 80, 8).add(ENERGY, 80, 8 + (18)).add(ENERGY, 80, 8 + (2 * 18)).add(ENERGY, 80, 8 + (3 * 18));
        }
        this.setTile(BlockEntityLocker::new);
        GTAPI.register(LockerMachine.class, this);
    }
}
