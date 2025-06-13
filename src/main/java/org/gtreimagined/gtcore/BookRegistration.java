package org.gtreimagined.gtcore;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.item.ItemSelectorTag;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.item.ItemBasic;

import java.util.Map;

public class BookRegistration {
    private static final Object2ObjectMap<Item, Pair<ResourceLocation, ResourceLocation>> TEXTURE_MAP = new Object2ObjectOpenHashMap<>();

    public static Map<Item, Pair<ResourceLocation, ResourceLocation>> getTextureMap() {
        return ImmutableMap.copyOf(TEXTURE_MAP);
    }

    public static void registerBookTexture(Item item, ResourceLocation back, ResourceLocation side) {
        TEXTURE_MAP.put(item, Pair.of(back, side));
    }

    private static void registerBookTexture(Item item, String id) {
        registerBookTexture(item, new ResourceLocation(GTCore.ID, "block/books/" + id + "_back"), new ResourceLocation(GTCore.ID, "block/books/" + id + "_side"));
    }

    public static void registerBooks(){
        registerBookTexture(Items.BOOK, "book_vanilla");
        registerBookTexture(Items.WRITTEN_BOOK, "book_vanilla");
        registerBookTexture(Items.WRITABLE_BOOK, "book_vanilla");
        registerBookTexture(Items.ENCHANTED_BOOK, "book_enchanted");
        registerBookTexture(Items.PAPER, "folder");
        registerBookTexture(Items.NAME_TAG, "folder");
        registerBookTexture(Items.MAP, "folder");
        registerBookTexture(Items.FILLED_MAP, "folder_red");
        registerBookTexture(GTCoreItems.EmptyShape, "extruder_shape");
        GTAPI.all(ItemBasic.class, GTCore.ID).forEach(i -> {
            if (i.getId().endsWith("_shape") || i.getId().endsWith("_mold")){
                registerBookTexture(i, "extruder_shape");
            }
        });
        GTAPI.all(ItemSelectorTag.class, GTCore.ID).forEach(i -> {
            registerBookTexture(i, "tablet_computer_metallic");
        });
    }
}
