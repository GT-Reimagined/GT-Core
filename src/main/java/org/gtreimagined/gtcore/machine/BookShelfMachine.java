package org.gtreimagined.gtcore.machine;

import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityBookShelf;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.texture.Texture;

import static org.gtreimagined.gtlib.machine.MachineFlag.GUI;
import static org.gtreimagined.gtlib.machine.MachineFlag.ITEM;

public class BookShelfMachine extends MaterialMachine {
    public BookShelfMachine(String id, Material material) {
        super(GTCore.ID, id, material);
        setTiers(Tier.NONE);
        setTile(BlockEntityBookShelf::new);
        addFlags(GUI, ITEM);
        setCustomModel((a,s,d) -> new ResourceLocation(GTCore.ID, "block/machine/overlay/bookshelf/" + d.getSerializedName()));
        setBaseTexture(new Texture(GTCore.ID, "block/machine/base/bookshelf"));
        Texture empty = new Texture(GTCore.ID, "block/machine/empty");
        setOverlayTextures((machine, machineState, tier, i) -> new Texture[]{
                empty, empty, empty,empty, empty, empty
        });
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 7; x++) {
                add(SlotType.STORAGE, 26 + (18 * x), 8 + (18 * y));
            }
        }
    }
}
