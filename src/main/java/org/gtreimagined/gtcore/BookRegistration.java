package org.gtreimagined.gtcore;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.item.ItemSelectorTag;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.util.RegistryUtils;

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
        registerBookTexture(Items.PAINTING, "frame");
        registerBookTexture(Items.PAPER, "folder");
        registerBookTexture(Items.NAME_TAG, "folder");
        registerBookTexture(Items.MAP, "folder");
        registerBookTexture(Items.FILLED_MAP, "folder_red");
        registerBookTexture(Items.ITEM_FRAME, "frame");
        Item[] records = new Item[]{Items.MUSIC_DISC_13, Items.MUSIC_DISC_CAT, Items.MUSIC_DISC_BLOCKS, Items.MUSIC_DISC_CHIRP, Items.MUSIC_DISC_FAR,
        Items.MUSIC_DISC_MALL, Items.MUSIC_DISC_MELLOHI, Items.MUSIC_DISC_STAL, Items.MUSIC_DISC_STRAD, Items.MUSIC_DISC_WARD, Items.MUSIC_DISC_11,
        Items.MUSIC_DISC_WAIT, Items.MUSIC_DISC_OTHERSIDE, Items.MUSIC_DISC_PIGSTEP};
        for (Item item : records) {
            registerBookTexture(item, "record");
        }
        registerBookTexture(GTCoreItems.EmptyShape, "extruder_shape");
        GTAPI.all(ItemBasic.class, GTCore.ID).forEach(i -> {
            if (i.getId().endsWith("_shape") || i.getId().endsWith("_mold")){
                registerBookTexture(i, "extruder_shape");
            }
        });
        GTAPI.all(ItemSelectorTag.class, GTCore.ID).forEach(i -> {
            registerBookTexture(i, "tablet_computer_metallic");
        });
        registerBookTexture(GTCoreItems.Tape, "tape_white");
        registerBookTexture(GTCoreItems.TapeEmpty, "tape_white");
        registerBookTexture(GTCoreItems.DuctTape, "tape_gray");
        registerBookTexture(GTCoreItems.DuctTapeEmpty, "tape_gray");
        registerBookTexture(GTCoreItems.FALDuctTape, "tape_black");
        registerBookTexture(GTCoreItems.FALDuctTapeEmpty, "tape_black");
        registerBookTexture(GTCoreItems.EmptyBlueprint, "folder");
        registerBookTexture(GTCoreItems.Blueprint, "folder_blue");
        if (GTAPI.isModLoaded("bic_clipboard")) registerBookTexture(RegistryUtils.getItemFromID("bic_clipboard", "clipboard"), "clipboard");
        if (GTAPI.isModLoaded(Ref.MOD_TWILIGHT)){
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_TWILIGHT, "magic_map"), "folder");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_TWILIGHT, "maze_map"), "folder");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_TWILIGHT, "ore_map"), "folder");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_TWILIGHT, "filled_magic_map"), "folder_red");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_TWILIGHT, "filled_maze_map"), "folder_red");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_TWILIGHT, "filled_ore_map"), "folder_red");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_TWILIGHT, "tower_key"), "book_vanilla");
        }
        if (GTAPI.isModLoaded("computercraft")){
            registerBookTexture(RegistryUtils.getItemFromID("computercraft", "pocket_computer_normal"), "tablet_computer_metallic");
            registerBookTexture(RegistryUtils.getItemFromID("computercraft", "pocket_computer_advanced"), "tablet_computer_gold");
            registerBookTexture(RegistryUtils.getItemFromID("computercraft", "disk"), "floppy");
            registerBookTexture(RegistryUtils.getItemFromID("computercraft", "printed_page"), "folder_red");
            registerBookTexture(RegistryUtils.getItemFromID("computercraft", "printed_pages"), "folder_red");
            registerBookTexture(RegistryUtils.getItemFromID("computercraft", "printed_book"), "book_vanilla");
        }
        if (GTAPI.isModLoaded(Ref.MOD_AE)){
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "biometric_card"), "id");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "memory_card"),"ae_handheld");
            String[] types = {"1k", "4k", "16k", "64k", "256k"};
            for (String type : types){
                registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "portable_item_cell_" + type), "ae_handheld");
                registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "portable_fluid_cell_" + type), "ae_handheld");
                registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "item_storage_cell_" + type), "ae_cell");
                registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "fluid_storage_cell_" + type), "ae_cell");
                if (GTAPI.isModLoaded("megacells")){
                    registerBookTexture(RegistryUtils.getItemFromID("megacells", "portable_item_cell_" + type.replace('k', 'm')), "ae_handheld");
                    registerBookTexture(RegistryUtils.getItemFromID("megacells", "portable_fluid_cell_" + type.replace('k', 'm')), "ae_handheld");
                    registerBookTexture(RegistryUtils.getItemFromID("megacells", "item_storage_cell_" + type.replace('k', 'm')), "ae_cell");
                    registerBookTexture(RegistryUtils.getItemFromID("megacells", "fluid_storage_cell_" + type.replace('k', 'm')), "ae_cell");
                }
                if (GTAPI.isModLoaded("appbot")){
                    registerBookTexture(RegistryUtils.getItemFromID("appbot", "portable_mana_storage_cell_" + type), "ae_handheld");
                    registerBookTexture(RegistryUtils.getItemFromID("appbot", "mana_storage_cell_" + type), "ae_cell");
                    if (GTAPI.isModLoaded("megacells")){
                        registerBookTexture(RegistryUtils.getItemFromID("megacells", "portable_mana_cell_" + type.replace('k', 'm')), "ae_handheld");
                        registerBookTexture(RegistryUtils.getItemFromID("megacells", "mana_storage_cell_" + type.replace('k', 'm')), "ae_cell");
                    }
                }
                if (GTAPI.isModLoaded("appmek")){
                    registerBookTexture(RegistryUtils.getItemFromID("appmek", "portable_chemical_storage_cell_" + type), "ae_handheld");
                    registerBookTexture(RegistryUtils.getItemFromID("appmek", "chemical_storage_cell_" + type), "ae_cell");
                    if (GTAPI.isModLoaded("megacells")){
                        registerBookTexture(RegistryUtils.getItemFromID("megacells", "portable_chemical_cell_" + type.replace('k', 'm')), "ae_handheld");
                        registerBookTexture(RegistryUtils.getItemFromID("megacells", "chemical_storage_cell_" + type.replace('k', 'm')), "ae_cell");
                    }
                }
            }
            for (DyeColor color : DyeColor.values()){
                registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "memory_card_" + color.getSerializedName()), "ae_handheld");
            }
            if (GTAPI.isModLoaded("megacells")){
                registerBookTexture(RegistryUtils.getItemFromID("megacells", "mega_item_cell_housing"), "ae_cell");
                registerBookTexture(RegistryUtils.getItemFromID("megacells", "mega_fluid_cell_housing"), "ae_cell");
            }
            if (GTAPI.isModLoaded("appbot")){
                registerBookTexture(RegistryUtils.getItemFromID("appbot", "mana_cell_housing"), "ae_cell");
                registerBookTexture(RegistryUtils.getItemFromID("appbot", "creative_mana_cell"), "ae_cell");
                if (GTAPI.isModLoaded("megacells")){
                    registerBookTexture(RegistryUtils.getItemFromID("megacells", "mega_mana_cell_housing"), "ae_cell");
                }
            }
            if (GTAPI.isModLoaded("appmek")){
                registerBookTexture(RegistryUtils.getItemFromID("appmek", "chemical_cell_housing"), "ae_cell");
                registerBookTexture(RegistryUtils.getItemFromID("appmek", "creative_chemical_cell"), "ae_cell");
                if (GTAPI.isModLoaded("megacells")){
                    registerBookTexture(RegistryUtils.getItemFromID("megacells", "mega_chemical_cell_housing"), "ae_cell");
                }
            }
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "creative_item_cell"), "ae_cell");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "creative_fluid_cell"), "ae_cell");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "item_cell_housing"), "ae_cell");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "fluid_cell_housing"), "ae_cell");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "view_cell"), "ae_handheld");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "wireless_terminal"), "ae_handheld");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "wireless_crafting_terminal"), "ae_handheld");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "spatial_storage_cell_2"), "ae_cell");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "spatial_storage_cell_16"), "ae_cell");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "spatial_storage_cell_128"), "ae_cell");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "calculation_processor_press"), "ae_press");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "engineering_processor_press"), "ae_press");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "logic_processor_press"), "ae_press");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "silicon_press"), "ae_press");
            registerBookTexture(RegistryUtils.getItemFromID(Ref.MOD_AE, "name_press"), "ae_press");
        }
    }
}
