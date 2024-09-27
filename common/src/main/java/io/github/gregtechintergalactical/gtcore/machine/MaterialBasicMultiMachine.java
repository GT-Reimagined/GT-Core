package io.github.gregtechintergalactical.gtcore.machine;

import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityMaterialBasicMultiMachine;
import muramasa.antimatter.Data;
import muramasa.antimatter.machine.types.BasicMultiMachine;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.util.Utils;

import static muramasa.antimatter.machine.MachineFlag.COVERABLE;

public class MaterialBasicMultiMachine extends BasicMultiMachine<MaterialBasicMultiMachine> {
    Material material;
    public MaterialBasicMultiMachine(String domain, String id, Material material) {
        super(domain, id);
        this.material = material;
        setItemBlockClass(() -> BlockMultiMachineMaterial.class);
        setBlock(BlockMultiMachineMaterial::new);
        setTile(BlockEntityMaterialBasicMultiMachine::new);
        addFlags(COVERABLE);
        this.setGUI(Data.BASIC_MENU_HANDLER);
        noCovers();
        allowFrontIO();
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public String getLang(String lang) {
        String returnedLang = super.getLang(lang);
        if (material.getDisplayNameString() != null)
            returnedLang = returnedLang.replace(Utils.lowerUnderscoreToUpperSpaced(material.getId()), material.getDisplayNameString());
        return returnedLang;
    }
}
