package org.gtreimagined.gtcore.data;

import net.minecraft.world.item.enchantment.Enchantments;
import org.gtreimagined.gtlib.Data;
import org.gtreimagined.gtlib.data.GTLibMaterials;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.material.MaterialTags;

import java.util.List;

import static com.google.common.collect.ImmutableMap.of;
import static net.minecraft.world.item.Tiers.*;
import static net.minecraft.world.item.Tiers.NETHERITE;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.material.MaterialTags.MOLTEN;
import static org.gtreimagined.gtlib.material.MaterialTags.QUARTZ_LIKE_BLOCKS;

public class GTCoreMaterialEvents {


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
                .tool().toolDamage(DIAMOND.getAttackDamageBonus()).toolSpeed(DIAMOND.getSpeed()).toolDurability(DIAMOND.getUses()).toolQuality(DIAMOND.getLevel()).build();
        event.setMaterial(GTCoreMaterials.Emerald).asGemBasic(false).asOre(3, 7, true).harvestLevel(2);
        event.setMaterial(GTCoreMaterials.EnderPearl).asGemBasic(false);
        event.setMaterial(GTCoreMaterials.EnderEye).asGemBasic(false);
        event.setMaterial(GTCoreMaterials.Lapis).asGemBasic(false).asOre(2, 5, true).harvestLevel(1);
        event.setMaterial(GTCoreMaterials.Redstone).asOre(1, 5, true, MOLTEN).harvestLevel(2);
        event.setMaterial(GTCoreMaterials.Quartz).asOre(1, 5, true, QUARTZ_LIKE_BLOCKS).harvestLevel(1);
        event.setMaterial(GTCoreMaterials.Netherite).asMetal(2246, PLATE, ROD);
        //.addTools(3.0F, 10, 500, NETHERITE.getLevel(), of(Enchantments.FIRE_ASPECT, 3)).addArmor(new int[]{0, 1, 1, 0}, 0.5F, 0.1F, 20);
        event.setMaterial(GTCoreMaterials.NetherizedDiamond).asGemBasic(false)
                .tool().toolDamage(4).toolSpeed(12).toolDurability(NETHERITE.getUses()).toolQuality(NETHERITE.getLevel()).toolEnchantments(of(Enchantments.FIRE_ASPECT, 3, Enchantments.SHARPNESS, 4)).build()
                .addArmor(new int[]{1, 1, 2, 1}, 3.0F, 0.1F, 37, of(Enchantments.ALL_DAMAGE_PROTECTION, 4));
        event.setMaterial(GTCoreMaterials.NetheriteScrap).asDust(GTMaterialTypes.CRUSHED_ORE, GTMaterialTypes.RAW_ORE);

    }
}
