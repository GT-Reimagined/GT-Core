package io.github.gregtechintergalactical.gtcore.loader.crafting;

import com.google.common.collect.ImmutableMap;
import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.GTCoreConfig;
import io.github.gregtechintergalactical.gtcore.GTCoreConfig.CircuitRecipeMode;
import io.github.gregtechintergalactical.gtcore.data.GTCoreItems;
import io.github.gregtechintergalactical.gtcore.data.GTCoreMaterials;
import io.github.gregtechintergalactical.gtcore.data.GTCoreTags;
import muramasa.antimatter.Antimatter;
import muramasa.antimatter.Ref;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.material.SubTag;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.util.TagUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static io.github.gregtechintergalactical.gtcore.GTCoreConfig.CircuitRecipeMode.*;
import static io.github.gregtechintergalactical.gtcore.data.GTCoreCables.*;
import static io.github.gregtechintergalactical.gtcore.data.GTCoreItems.*;
import static io.github.gregtechintergalactical.gtcore.data.GTCoreMaterials.*;
import static muramasa.antimatter.data.AntimatterDefaultTools.WRENCH;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.antimatter.machine.Tier.MV;

public class CircuitRecipes {
    public static void initRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider){
        CircuitRecipeMode recipeMode = GTCoreConfig.CIRCUIT_RECIPE_MODE.get();
        if (recipeMode == PUP || recipeMode == GT5U){ // make the condition the harder circuits config
            provider.addStackRecipe(output, GTCore.ID, "circuit_basic_v", "parts", new ItemStack(CircuitBasic, 1), of('C', VacuumTube, 'R', VacuumTube, 'I', VacuumTube), "CRC", "CIC", "CRC");
        } else {
            boolean gt5 = recipeMode == GT5;
            if (gt5){
                provider.addItemRecipe(output, GTCore.ID, "", "circuits", NandChip,
                        ImmutableMap.of('C', ITEM_CASING.getMaterialTag(Steel), 'R', WIRE_RED_ALLOY.getBlockItem(PipeSize.VTINY), 'T', WIRE_TIN.getBlockItem(PipeSize.VTINY)), "CR", "RT");
                provider.addStackRecipe(output, GTCore.ID, "", "board_basic", new ItemStack(CircuitBoardCoated, 3),
                        ImmutableMap.<Character, Object>builder()
                                .put('R', GTCoreItems.StickyResin)
                                .put('P', PLATE.getMaterialTag(Wood))
                                .build(),
                        " R ", "PPP", " R ");
            }
            TagKey<Item> copperCable = TagUtils.getItemTag(new ResourceLocation(Ref.ID, SubTag.COPPER_CABLE.getId()+"_"+ PipeSize.VTINY.getId()));
            Object nandChip = gt5 ? NandChip : PLATE.getMaterialTag(RedAlloy);
            Object circuitBoard = gt5 ? CircuitBoardCoated : PLATE.getMaterialTag(WroughtIron);
            provider.addItemRecipe(output, GTCore.ID, "circuit_basic_h", "circuits", CircuitBasic,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', copperCable)
                            .put('N', nandChip)
                            .put('S', circuitBoard)
                            .build(), "CCC", "NSN", "CCC");
            provider.addItemRecipe(output, GTCore.ID, "circuit_basic_v", "circuits", CircuitBasic,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', copperCable)
                            .put('N', nandChip)
                            .put('S', circuitBoard)
                            .build(), "CNC", "CSC", "CNC");

            if (GTCoreConfig.ADVANCED_CIRCUIT_CRAFTING.get()){
                provider.addItemRecipe(output, GTCore.ID, "advanced_circuit_1", "circuits", CircuitAdv,
                        ImmutableMap.<Character, Object>builder()
                                .put('R', PLATE.getMaterialTag(RedAlloy))
                                .put('L', (gt5 ? PLATE : DUST).getMaterialTag(Lapis))
                                .put('G', DUST.getMaterialTag(Glowstone))
                                .put('C', GTCoreTags.CIRCUITS_BASIC).build(), "RLR", "GCG", "RLR");
                provider.addItemRecipe(output, GTCore.ID, "advanced_circuit_2", "circuits", CircuitAdv,
                        ImmutableMap.<Character, Object>builder()
                                .put('R', PLATE.getMaterialTag(RedAlloy))
                                .put('L', (gt5 ? PLATE : DUST).getMaterialTag(Lapis))
                                .put('G', DUST.getMaterialTag(Glowstone))
                                .put('C', GTCoreTags.CIRCUITS_BASIC).build(), "RGR", "LCL", "RGR");
            }
        }
    }

    public static void initCircuits(){
        CircuitRecipeMode recipeMode = GTCoreConfig.CIRCUIT_RECIPE_MODE.get();
        if (recipeMode == PUP || recipeMode == GT5U){ // make the condition the harder circuits config

        } else {
            boolean gt5 = recipeMode == GT5;

            if (GTCoreConfig.GOOD_CIRCUITS.get()){ // make the condition the good circuits config

            }
        }
    }
}
