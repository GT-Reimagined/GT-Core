package org.gtreimagined.gtcore.client;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.gtreimagined.gtcore.BookRegistration;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.client.ModelUtils;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GTCore.ID, value = Dist.CLIENT)
public class BookSpriteMaps {
    private static final Map<Item, Pair<TextureAtlasSprite, TextureAtlasSprite>> SPRITE_MAP = new Object2ObjectOpenHashMap<>();
    private static Map<Item, Pair<Material, Material>> MATERIAL_MAP = null;

    public static Map<Item, Pair<Material, Material>> getMaterialMap() {
        return MATERIAL_MAP;
    }

    public static Map<Item, Pair<TextureAtlasSprite, TextureAtlasSprite>> getSpriteMap() {
        return SPRITE_MAP;
    }

    public static void initMaterialMap(){
        if (MATERIAL_MAP == null){
            MATERIAL_MAP = new Object2ObjectOpenHashMap<>();
            BookRegistration.getTextureMap().forEach((i, p) -> {
                MATERIAL_MAP.put(i, Pair.of(ModelUtils.getBlockMaterial(p.first()), ModelUtils.getBlockMaterial(p.second())));
            });
        }

    }

    @SubscribeEvent
    public static void onTextureStitchPost(TextureStitchEvent.Post event) {
        if (!event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) return;
        BookRegistration.getTextureMap().forEach((i, p) -> {
            SPRITE_MAP.put(i, Pair.of(event.getAtlas().getSprite(p.first()), event.getAtlas().getSprite(p.second())));
        });
    }
}
