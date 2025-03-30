package org.gtreimagined.gtcore.tree.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.SignItem;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.registration.ITextureProvider;
import org.gtreimagined.gtlib.texture.Texture;

public class ItemRubberSign extends SignItem implements IGTObject, ITextureProvider, IModelProvider {
    public ItemRubberSign() {
        super(new Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), GTCoreBlocks.RUBBER_SIGN, GTCoreBlocks.RUBBER_WALL_SIGN);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_sign";
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(GTCore.ID, "item/basic/rubber_sign")};
    }
}
