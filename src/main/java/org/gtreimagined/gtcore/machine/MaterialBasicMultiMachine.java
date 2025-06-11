package org.gtreimagined.gtcore.machine;

import org.gtreimagined.gtcore.blockentity.BlockEntityMaterialBasicMultiMachine;
import org.gtreimagined.gtlib.Data;
import org.gtreimagined.gtlib.machine.types.BasicMultiMachine;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.util.Utils;

import static org.gtreimagined.gtlib.machine.MachineFlag.COVERABLE;


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
        setNoOutputCover();
        setAllowsFrontIO();
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
