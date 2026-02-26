package org.gtreimagined.gtcore.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import org.gtreimagined.gtlib.Data;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTLibMaterials;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.data.VanillaStoneTypes;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.util.RegistryUtils;

import java.util.List;

import static com.google.common.collect.ImmutableMap.of;
import static net.minecraft.world.item.Tiers.*;
import static net.minecraft.world.item.Tiers.NETHERITE;
import static org.gtreimagined.gtcore.data.GTCoreMaterials.*;
import static org.gtreimagined.gtcore.data.GTCoreMaterials.Lumium;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.*;
import static org.gtreimagined.gtlib.material.MaterialTags.*;
import static org.gtreimagined.gtlib.material.MaterialTags.WOOD;

public class GTCoreMaterialEvents {

    public static void onMaterialEvent(MaterialEvent<?> event){
        replacements();
        vanillaMaterials(event);
        event.setMaterial(GTCoreMaterials.Rubber).asSolid(295, PLATE, RING);
        event.setMaterial(GTCoreMaterials.Plastic).flags(RUBBERTOOLS);
        event.setMaterial(GTCoreMaterials.Beeswax).asDust();
        event.setMaterial(GTCoreMaterials.FierySteel).asMetal().tool().toolDamage(4).toolSpeed(9).toolDurability(1024).toolQuality(4)
                .toolEnchantments(ImmutableMap.of(Enchantments.FIRE_ASPECT, 2)).handleMaterial(GTCoreMaterials.Blaze)
                .blacklistToolTypes(PICKAXE, SWORD).build();
        event.setMaterial(GTCoreMaterials.Knightmetal).asMetal().tool().toolDamage(3).toolSpeed(8).toolDurability(512).toolQuality(3)
                .blacklistToolTypes(AXE, PICKAXE, SWORD).build();
        event.setMaterial(GTCoreMaterials.Ironwood).asMetal(WOOD).tool().toolDamage(2).toolSpeed(6.5f).toolDurability(512).toolQuality(2)
                .toolEnchantments(ImmutableMap.of(Enchantments.KNOCKBACK, 1))
                .blacklistToolTypes(AXE, PICKAXE, SHOVEL, SWORD, HOE).build();
        event.setMaterial(GTCoreMaterials.Steeleaf).asMetal().tool().toolDamage(4).toolSpeed(8).toolDurability(131).toolQuality(3)
                .toolEnchantments(ImmutableMap.of(Enchantments.MOB_LOOTING, 2, Enchantments.BLOCK_FORTUNE, 2))
                .blacklistToolTypes(AXE, PICKAXE, SHOVEL, SWORD, HOE).build();
        event.setMaterial(Teslatite).asOre(1, 5, true);
        if (GTAPI.isModLoaded("twilightforest")){
            INGOT.replacement(GTCoreMaterials.Ironwood, () -> RegistryUtils.getItemFromID("twilightforest", "ironwood_ingot"));
            BLOCK.replacement(GTCoreMaterials.Ironwood, () -> RegistryUtils.getItemFromID("twilightforest", "ironwood_block"));
            INGOT.replacement(GTCoreMaterials.Knightmetal, () -> RegistryUtils.getItemFromID("twilightforest", "knightmetal_ingot"));
            BLOCK.replacement(GTCoreMaterials.Knightmetal, () -> RegistryUtils.getItemFromID("twilightforest", "knightmetal_block"));
            INGOT.replacement(GTCoreMaterials.Steeleaf, () -> RegistryUtils.getItemFromID("twilightforest", "steeleaf_ingot"));
            BLOCK.replacement(GTCoreMaterials.Steeleaf, () -> RegistryUtils.getItemFromID("twilightforest", "steeleaf_block"));
            INGOT.replacement(GTCoreMaterials.FierySteel, () -> RegistryUtils.getItemFromID("twilightforest", "fiery_ingot"));
            BLOCK.replacement(GTCoreMaterials.FierySteel, () -> RegistryUtils.getItemFromID("twilightforest", "fiery_block"));
        }
        event.setMaterial(GTCoreMaterials.Signalum).asMetal(1353).mats(of(Copper, 1, Silver, 2, RedAlloy, 5));
        event.setMaterial(GTCoreMaterials.Lumium).asMetal(593).mats(of(Tin, 3, Silver, 1, Glowstone, 4));
        event.setMaterial(GTCoreMaterials.Enderium).asMetal(1071).mats(of(Tin, 2, Silver, 1, Platinum, 1, EnderPearl, 4));

        GTCoreBlocks.initItemBarrels();
        Guis.init();
    }



    public static void replacements(){
        NUGGET.replacement(GTCoreMaterials.Iron, () -> Items.IRON_NUGGET);
        NUGGET.replacement(GTCoreMaterials.Gold, () -> Items.GOLD_NUGGET);
        INGOT.replacement(GTCoreMaterials.Iron, () -> Items.IRON_INGOT);
        INGOT.replacement(GTCoreMaterials.Gold, () -> Items.GOLD_INGOT);
        INGOT.replacement(GTCoreMaterials.Netherite, () -> Items.NETHERITE_INGOT);
        INGOT.replacement(GTCoreMaterials.Copper, () -> Items.COPPER_INGOT);
        INGOT.replacement(GTCoreMaterials.NetheriteScrap, () -> Items.NETHERITE_SCRAP);


        DUST.replacement(GTCoreMaterials.Redstone, () -> Items.REDSTONE);
        DUST.replacement(GTCoreMaterials.Glowstone, () -> Items.GLOWSTONE_DUST);
        DUST.replacement(GTCoreMaterials.Blaze, () -> Items.BLAZE_POWDER);
        DUST.replacement(GTCoreMaterials.Sugar, () -> Items.SUGAR);
        RAW_ORE.replacement(GTCoreMaterials.Iron, () -> Items.RAW_IRON);
        RAW_ORE.replacement(GTCoreMaterials.Copper, () -> Items.RAW_COPPER);
        RAW_ORE.replacement(GTCoreMaterials.Gold, () -> Items.RAW_GOLD);
        GEM.replacement(GTCoreMaterials.Flint, () -> Items.FLINT);
        GEM.replacement(GTCoreMaterials.Diamond, () -> Items.DIAMOND);
        GEM.replacement(GTCoreMaterials.Emerald, () -> Items.EMERALD);
        GEM.replacement(GTCoreMaterials.Lapis, () -> Items.LAPIS_LAZULI);
        GEM.replacement(GTCoreMaterials.Quartz, () -> Items.QUARTZ);
        GEM.replacement(GTCoreMaterials.Coal, () -> Items.COAL);
        GEM.replacement(GTCoreMaterials.Charcoal, () -> Items.CHARCOAL);
        GEM.replacement(GTCoreMaterials.EnderEye, () -> Items.ENDER_EYE);
        GEM.replacement(GTCoreMaterials.EnderPearl, () -> Items.ENDER_PEARL);

        ROD.replacement(GTCoreMaterials.Blaze, () -> Items.BLAZE_ROD);
        ROD.replacement(GTCoreMaterials.Bone, () -> Items.BONE);

        BLOCK.replacement(GTCoreMaterials.Coal, () -> Items.COAL_BLOCK);
        BLOCK.replacement(GTCoreMaterials.Iron, () -> Items.IRON_BLOCK);
        BLOCK.replacement(GTCoreMaterials.Copper, () -> Items.COPPER_BLOCK);
        BLOCK.replacement(GTCoreMaterials.Gold, () -> Items.GOLD_BLOCK);
        BLOCK.replacement(GTCoreMaterials.Diamond, () -> Items.DIAMOND_BLOCK);
        BLOCK.replacement(GTCoreMaterials.Emerald, () -> Items.EMERALD_BLOCK);
        BLOCK.replacement(GTCoreMaterials.Lapis, () -> Items.LAPIS_BLOCK);
        BLOCK.replacement(GTCoreMaterials.Netherite, () -> Items.NETHERITE_BLOCK);
        RAW_ORE_BLOCK.replacement(GTCoreMaterials.Iron, () -> Items.RAW_IRON_BLOCK);
        RAW_ORE_BLOCK.replacement(GTCoreMaterials.Copper, () -> Items.RAW_COPPER_BLOCK);
        RAW_ORE_BLOCK.replacement(GTCoreMaterials.Gold, () -> Items.RAW_GOLD_BLOCK);
        ORE.replacement(GTCoreMaterials.Coal, VanillaStoneTypes.STONE, () -> Items.COAL_ORE);
        ORE.replacement(GTCoreMaterials.Coal, VanillaStoneTypes.DEEPSLATE, () -> Items.DEEPSLATE_COAL_ORE);
        ORE.replacement(GTCoreMaterials.Iron, VanillaStoneTypes.STONE, () -> Items.IRON_ORE);
        ORE.replacement(GTCoreMaterials.Iron, VanillaStoneTypes.DEEPSLATE, () -> Items.DEEPSLATE_IRON_ORE);
        ORE.replacement(GTCoreMaterials.Copper, VanillaStoneTypes.STONE, () -> Items.COPPER_ORE);
        ORE.replacement(GTCoreMaterials.Copper, VanillaStoneTypes.DEEPSLATE, () -> Items.DEEPSLATE_COPPER_ORE);
        ORE.replacement(GTCoreMaterials.Gold, VanillaStoneTypes.STONE, () -> Items.GOLD_ORE);
        ORE.replacement(GTCoreMaterials.Gold, VanillaStoneTypes.DEEPSLATE, () -> Items.DEEPSLATE_GOLD_ORE);
        ORE.replacement(GTCoreMaterials.Redstone, VanillaStoneTypes.STONE, () -> Items.REDSTONE_ORE);
        ORE.replacement(GTCoreMaterials.Redstone, VanillaStoneTypes.DEEPSLATE, () -> Items.DEEPSLATE_REDSTONE_ORE);
        ORE.replacement(GTCoreMaterials.Emerald, VanillaStoneTypes.STONE, () -> Items.EMERALD_ORE);
        ORE.replacement(GTCoreMaterials.Emerald, VanillaStoneTypes.DEEPSLATE, () -> Items.DEEPSLATE_EMERALD_ORE);
        ORE.replacement(GTCoreMaterials.Lapis, VanillaStoneTypes.STONE, () -> Items.LAPIS_ORE);
        ORE.replacement(GTCoreMaterials.Lapis, VanillaStoneTypes.DEEPSLATE, () -> Items.DEEPSLATE_LAPIS_ORE);
        ORE.replacement(GTCoreMaterials.Diamond, VanillaStoneTypes.STONE, () -> Items.DIAMOND_ORE);
        ORE.replacement(GTCoreMaterials.Diamond, VanillaStoneTypes.DEEPSLATE, () -> Items.DEEPSLATE_DIAMOND_ORE);
        ORE.replacement(GTCoreMaterials.Quartz, VanillaStoneTypes.NETHERRACK, () -> Items.NETHER_QUARTZ_ORE);
        if (GTAPI.isModLoaded("thermal")){
            INGOT.replacement(GTCoreMaterials.Signalum, () -> RegistryUtils.getItemFromID("thermal", "signalum_ingot"));
            DUST.replacement(GTCoreMaterials.Signalum, () -> RegistryUtils.getItemFromID("thermal", "signalum_dust"));
            NUGGET.replacement(GTCoreMaterials.Signalum, () -> RegistryUtils.getItemFromID("thermal", "signalum_nugget"));
            BLOCK.replacement(GTCoreMaterials.Signalum, () -> RegistryUtils.getItemFromID("thermal", "signalum_block"));
            GEAR.replacement(GTCoreMaterials.Signalum, () -> RegistryUtils.getItemFromID("thermal", "signalum_gear"));
            PLATE.replacement(GTCoreMaterials.Signalum, () -> RegistryUtils.getItemFromID("thermal", "signalum_plate"));
            INGOT.replacement(Lumium, () -> RegistryUtils.getItemFromID("thermal", "lumium_ingot"));
            DUST.replacement(Lumium, () -> RegistryUtils.getItemFromID("thermal", "lumium_dust"));
            NUGGET.replacement(Lumium, () -> RegistryUtils.getItemFromID("thermal", "lumium_nugget"));
            BLOCK.replacement(Lumium, () -> RegistryUtils.getItemFromID("thermal", "lumium_block"));
            GEAR.replacement(Lumium, () -> RegistryUtils.getItemFromID("thermal", "lumium_gear"));
            PLATE.replacement(Lumium, () -> RegistryUtils.getItemFromID("thermal", "lumium_plate"));
            INGOT.replacement(GTCoreMaterials.Enderium, () -> RegistryUtils.getItemFromID("thermal", "enderium_ingot"));
            DUST.replacement(GTCoreMaterials.Enderium, () -> RegistryUtils.getItemFromID("thermal", "enderium_dust"));
            NUGGET.replacement(GTCoreMaterials.Enderium, () -> RegistryUtils.getItemFromID("thermal", "enderium_nugget"));
            BLOCK.replacement(GTCoreMaterials.Enderium, () -> RegistryUtils.getItemFromID("thermal", "enderium_block"));
            GEAR.replacement(GTCoreMaterials.Enderium, () -> RegistryUtils.getItemFromID("thermal", "enderium_gear"));
            PLATE.replacement(GTCoreMaterials.Enderium, () -> RegistryUtils.getItemFromID("thermal", "enderium_plate"));
        }
        if (GTAPI.isModLoaded("bluepower")){
            DUST.replacement(Teslatite, () -> RegistryUtils.getItemFromID("bluepower", "teslatite_dust"));
            ORE.replacement(Teslatite, VanillaStoneTypes.STONE, () -> RegistryUtils.getItemFromID("bluepower", "teslatite_ore"));
            ORE.replacement(Teslatite, VanillaStoneTypes.DEEPSLATE, () -> RegistryUtils.getItemFromID("bluepower", "teslatite_deepslate"));
        }
    }

    public static void vanillaMaterials(MaterialEvent<?> event) {
        event.setMaterial(GTCoreMaterials.Iron).asMetal(1811).asOre(true).tool().toolDamage(IRON.getAttackDamageBonus()).toolSpeed(IRON.getSpeed()).toolDurability(256).toolQuality(IRON.getLevel()).build();
        MaterialTags.ORE_RGB.add(GTCoreMaterials.Iron, 0xe2c0aa);
        Data.setMaterialTier(GTCoreMaterials.Iron, 2);
        Data.setMaterialTier(GTCoreMaterials.Diamond, 3);
        Data.setMaterialTier(GTCoreMaterials.NetherizedDiamond, 4);
        event.setMaterial(GTCoreMaterials.Gold).asMetal(1337).asOre(true).tool().toolDamage(GOLD.getAttackDamageBonus()).toolSpeed(GOLD.getSpeed()).toolDurability(GOLD.getUses()).toolQuality(GOLD.getLevel()).toolEnchantments(of(Enchantments.SMITE, 3)).build().harvestLevel(2);
        event.setMaterial(GTCoreMaterials.Copper).asMetal(1357).asOre(true).harvestLevel(1);

        event.setMaterial(GTCoreMaterials.Glowstone).asDust();
        event.setMaterial(GTCoreMaterials.Sugar).asDust();
        event.setMaterial(GTCoreMaterials.Blaze).asDust().addHandleStat(-10, -0.5F, of(Enchantments.FIRE_ASPECT, 1));

        event.setMaterial(GTCoreMaterials.Flint).asDust(GTMaterialTypes.GEM, MaterialTags.FLINT)
                .tool().toolDamage(1.25f).toolSpeed(STONE.getSpeed()).toolDurability(128).toolQuality(1)
                .toolEnchantments(of(Enchantments.FIRE_ASPECT, 1)).allowedToolTypes(List.of(GTTools.PICKAXE, GTTools.AXE, GTTools.SHOVEL, GTTools.SWORD, GTTools.HOE, GTTools.KNIFE)).build();

        event.setMaterial(GTCoreMaterials.Charcoal).asDust(GTMaterialTypes.BLOCK);
        event.setMaterial(GTCoreMaterials.Coal).asGemBasic(false).asOre(0, 2, true, GTMaterialTypes.ORE_STONE);
        event.setMaterial(GTCoreMaterials.Diamond).asGemBasic(false, PICKAXE_HEAD, AXE_HEAD, SHOVEL_HEAD, HOE_HEAD, SWORD_BLADE).asOre(3, 7, true)
                .tool().toolDamage(DIAMOND.getAttackDamageBonus()).toolSpeed(DIAMOND.getSpeed()).toolDurability(1280).toolQuality(DIAMOND.getLevel()).build();
        event.setMaterial(GTCoreMaterials.Emerald).asGemBasic(false).asOre(3, 7, true).harvestLevel(2);
        event.setMaterial(GTCoreMaterials.EnderPearl).asGemBasic(false);
        event.setMaterial(GTCoreMaterials.EnderEye).asGemBasic(false);
        event.setMaterial(GTCoreMaterials.Lapis).asGemBasic(false).asOre(2, 5, true).harvestLevel(1);
        event.setMaterial(GTCoreMaterials.Redstone).asOre(1, 5, true, MOLTEN).harvestLevel(2);
        event.setMaterial(GTCoreMaterials.Quartz).asOre(1, 5, true, QUARTZ_LIKE_BLOCKS).harvestLevel(1);
        event.setMaterial(GTCoreMaterials.Netherite).asMetal(2246, PLATE, ROD);
        //.addTools(3.0F, 10, 500, NETHERITE.getLevel(), of(Enchantments.FIRE_ASPECT, 3)).addArmor(new int[]{0, 1, 1, 0}, 0.5F, 0.1F, 20);
        event.setMaterial(GTCoreMaterials.NetherizedDiamond).asGemBasic(false)
                .tool().toolDamage(4).toolSpeed(12).toolDurability(2560).toolQuality(NETHERITE.getLevel()).toolEnchantments(of(Enchantments.FIRE_ASPECT, 3, Enchantments.SHARPNESS, 4)).build()
                .addArmor(new int[]{1, 1, 2, 1}, 3.0F, 0.1F, 37, of(Enchantments.ALL_DAMAGE_PROTECTION, 4));
        event.setMaterial(GTCoreMaterials.NetheriteScrap).asDust(GTMaterialTypes.CRUSHED_ORE, GTMaterialTypes.RAW_ORE);

    }
}
