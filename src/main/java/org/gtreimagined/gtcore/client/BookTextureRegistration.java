package org.gtreimagined.gtcore.client;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.GTCore;

import java.util.Map;

public class BookTextureRegistration {
    private static final Object2ObjectMap<Item, Pair<ResourceLocation, ResourceLocation>> TEXTURE_MAP = new Object2ObjectOpenHashMap<>();
    private static final Map<Item, Pair<TextureAtlasSprite, TextureAtlasSprite>> SPRITE_MAP = new Object2ObjectOpenHashMap<>();

    static {
        TEXTURE_MAP.defaultReturnValue(Pair.of(new ResourceLocation(GTCore.ID, "block/books/book_vanilla_back"), new ResourceLocation(GTCore.ID, "block/books/book_vanilla_side")));
    }

    public static void registerBookTexture(Item item, ResourceLocation back, ResourceLocation side) {
        TEXTURE_MAP.put(item, Pair.of(back, side));
    }

    public static void registerBooks(){
        registerBookTexture(Items.BOOK, new ResourceLocation(GTCore.ID, "block/books/book_vanilla_back"), new ResourceLocation(GTCore.ID, "block/books/book_vanilla_side"));
    }
}
