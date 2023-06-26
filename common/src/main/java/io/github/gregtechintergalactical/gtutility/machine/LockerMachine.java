package io.github.gregtechintergalactical.gtutility.machine;

import io.github.gregtechintergalactical.gtutility.GTUtility;
import io.github.gregtechintergalactical.gtutility.blockentity.BlockEntityLocker;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.machine.MachineFlag;
import muramasa.antimatter.machine.types.BasicMachine;
import muramasa.antimatter.material.Material;
import net.minecraft.resources.ResourceLocation;

import static muramasa.antimatter.gui.SlotType.ENERGY;
import static muramasa.antimatter.gui.SlotType.STORAGE;

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
        this.getGui().setOverrideLocation(new ResourceLocation(GTUtility.ID, "textures/gui/machine/" + (charge ? "charging_" : "") + "locker.png"));
        AntimatterAPI.register(LockerMachine.class, this);
    }
}
