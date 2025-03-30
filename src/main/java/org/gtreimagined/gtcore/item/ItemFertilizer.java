package org.gtreimagined.gtcore.item;

import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.registration.ITextureProvider;
import org.gtreimagined.gtlib.texture.Texture;

public class ItemFertilizer extends BoneMealItem implements IGTObject, ITextureProvider, IModelProvider {
    public ItemFertilizer() {
        super(new Item.Properties().tab(Ref.TAB_ITEMS));
        GTAPI.register(ItemFertilizer.class,this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "fertilizer";
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(GTCore.ID, "item/basic/fertilizer")};
    }
}
