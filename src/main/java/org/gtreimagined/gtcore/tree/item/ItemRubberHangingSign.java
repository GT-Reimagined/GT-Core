package org.gtreimagined.gtcore.tree.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.SignItem;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.registration.ICreativeTabProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.registration.ITextureProvider;
import org.gtreimagined.gtlib.texture.Texture;

public class ItemRubberHangingSign extends HangingSignItem implements IGTObject, ITextureProvider, IModelProvider, ICreativeTabProvider {
    public ItemRubberHangingSign() {
        super(GTCoreBlocks.RUBBER_HANGING_SIGN, GTCoreBlocks.RUBBER_WALL_HANGING_SIGN, new Properties().stacksTo(16));
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_hanging_sign";
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(GTCore.ID, "item/basic/rubber_hanging_sign")};
    }

    @Override
    public boolean allowedIn(CreativeModeTab creativeModeTab) {
        return creativeModeTab == Ref.TAB_ITEMS;
    }
}
