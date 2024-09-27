package io.github.gregtechintergalactical.gtcore.machine;

import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityMaterial;
import muramasa.antimatter.Data;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.util.Utils;

import static muramasa.antimatter.machine.MachineFlag.COVERABLE;

public class MaterialMachine extends Machine<MaterialMachine> {
    Material material;
    String suffix;
    public MaterialMachine(String domain, String suffix, Material material) {
        super(domain, material.getId() + "_" + suffix);
        this.material = material;
        this.suffix = suffix;
        setItemBlockClass(() -> BlockMachineMaterial.class);
        setBlock(BlockMachineMaterial::new);
        setTile(BlockEntityMaterial::new);
        addFlags(COVERABLE);
        this.setGUI(Data.BASIC_MENU_HANDLER);
        noCovers();
        allowFrontIO();
        frontCovers();
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public String getLang(String lang) {
        return material.getDisplayNameString() + " " + Utils.lowerUnderscoreToUpperSpaced(suffix);
    }
}
