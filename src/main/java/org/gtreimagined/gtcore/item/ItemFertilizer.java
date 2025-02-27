package org.gtreimagined.gtcore.item;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.registration.ITextureProvider;
import muramasa.antimatter.texture.Texture;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import org.gtreimagined.gtcore.GTCore;

public class ItemFertilizer extends BoneMealItem implements IAntimatterObject, ITextureProvider, IModelProvider {
    public ItemFertilizer() {
        super(new Item.Properties().tab(Ref.TAB_ITEMS));
        AntimatterAPI.register(ItemFertilizer.class,this);
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
