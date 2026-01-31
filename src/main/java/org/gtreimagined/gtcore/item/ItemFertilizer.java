package org.gtreimagined.gtcore.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTCreativeTabs;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.registration.ICreativeTabProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.registration.ITextureProvider;
import org.gtreimagined.gtlib.texture.Texture;

public class ItemFertilizer extends BoneMealItem implements IGTObject, ITextureProvider, IModelProvider, ICreativeTabProvider {
    public ItemFertilizer() {
        super(new Item.Properties());
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

    @Override
    public boolean allowedIn(ResourceKey<CreativeModeTab> creativeModeTab) {
        return creativeModeTab == GTCreativeTabs.ITEMS.getKey();
    }
}
