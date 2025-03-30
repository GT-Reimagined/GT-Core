package org.gtreimagined.gtcore.data;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.integration.jeirei.renderer.InfoRenderers;
import org.gtreimagined.gtlib.machine.BlockMachine;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.RecipeProxies;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.Proxy;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import org.gtreimagined.gtlib.recipe.map.RecipeMap;

import java.util.List;
import java.util.function.BiFunction;


@SuppressWarnings("unchecked")
public class RecipeMaps {

    public static BiFunction<Integer, Integer, Proxy> DISSASSEMBLER_PROXY = (power, duration) -> new Proxy(RecipeType.CRAFTING, getDefaultCrafting(power, duration));
    public static RecipeMap<RecipeBuilder> STEAM_SMELTING = GTAPI.register(RecipeMap.class,
            new RecipeMap<>(GTCore.ID, "steam_furnace", new RecipeBuilder())
                    .setProxy(RecipeProxies.FURNACE_PROXY.apply(8, 160)).setGuiTier(Tier.BRONZE));
    public static RecipeMap<RecipeBuilder> ASSEMBLING = GTAPI.register(RecipeMap.class,
            new RecipeMap<>(GTCore.ID, "assembler", new RecipeBuilder()));

    private static BiFunction<Recipe<?>, RecipeBuilder, IRecipe> getDefaultCrafting(int power, int duration) {
        return (t, b) -> {
            if (!(t instanceof ShapedRecipe shapedRecipe)) return null;
            List<Ingredient> ingredients = t.getIngredients();
            if (!(t.getResultItem().getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BlockMachine)) return null;
            List<ItemStack> list = new ObjectArrayList<>();
            for (Ingredient i : ingredients){
                for (ItemStack stack : i.getItems()){
                    if (!stack.isEmpty() && !stack.isDamageableItem()){
                        list.add(stack);
                        break;
                    }
                }
            }
            ItemStack craftingOut = shapedRecipe.getResultItem();
            if (list.isEmpty()) return null;
            RecipeIngredient ing = RecipeIngredient.of(craftingOut);
            IRecipe recipe = b.recipeMapOnly().ii(ing)
                    .io(list.toArray(new ItemStack[0])).hide().add(t.getId().getPath(), duration, power, 0, 1);
            recipe.setMapId(b.getMap().getId());
            return recipe;
        };
    }

    static {
    }

    public static void init(){

    }

    public static void clientMaps() {
        STEAM_SMELTING.setInfoRenderer(InfoRenderers.STEAM_RENDERER);
    }
}
