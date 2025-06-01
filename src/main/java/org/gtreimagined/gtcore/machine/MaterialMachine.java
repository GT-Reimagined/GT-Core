package org.gtreimagined.gtcore.machine;

import org.gtreimagined.gtcore.blockentity.BlockEntityMaterial;
import org.gtreimagined.gtlib.Data;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.util.Utils;

import static org.gtreimagined.gtlib.machine.MachineFlag.COVERABLE;


public class MaterialMachine extends Machine<MaterialMachine> {
    Material material;
    public MaterialMachine(String domain, String id, Material material) {
        super(domain, id);
        this.material = material;
        setItemBlockClass(() -> BlockMachineMaterial.class);
        setBlock(BlockMachineMaterial::new);
        setTile(BlockEntityMaterial::new);
        addFlags(COVERABLE);
        this.setGUI(Data.BASIC_MENU_HANDLER);
        noOutputCover();
        allowFrontIO();
        frontCovers();
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
