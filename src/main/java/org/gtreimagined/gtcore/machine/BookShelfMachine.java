package org.gtreimagined.gtcore.machine;

import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.texture.Texture;

public class BookShelfMachine extends MaterialMachine {
    public BookShelfMachine(String id, Material material) {
        super(GTCore.ID, id, material);
        setTiers(Tier.NONE);
        setCustomModel((a,s,d) -> new ResourceLocation(GTCore.ID, "block/machine/overlay/bookshelf/" + d.getSerializedName()));
        setBaseTexture(new Texture(GTCore.ID, "block/machine/base/bookshelf"));
        Texture empty = new Texture(GTCore.ID, "block/machine/empty");
        setOverlayTextures((machine, machineState, tier, i) -> new Texture[]{
                empty, empty, empty,empty, empty, empty
        });
    }
}
