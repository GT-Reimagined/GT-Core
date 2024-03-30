package io.github.gregtechintergalactical.gtcore.data;

import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.recipe.IRecipe;
import muramasa.antimatter.recipe.map.RecipeBuilder;
import muramasa.antimatter.recipe.map.RecipeMap;

public class RecipeBuilders {

    public static class SteamBuilder extends RecipeBuilder {
        RecipeMap recipeMap;
        public SteamBuilder(RecipeMap map){
            this.recipeMap = map;
        }

        @Override
        public IRecipe add(String domain, String id) {
            return addRecipeToSteamMap(this.recipeMap, super.add(domain, id), domain, id);
        }
    }

    public static class SmeltingBuilder extends RecipeBuilder {
        @Override
        public IRecipe add(String domain, String id) {
            return addRecipeToSteamMap(RecipeMaps.STEAM_SMELTING, super.add(domain, id), domain, id);
        }
    }

    public static class BlastingBuilder extends RecipeBuilder {
        public BlastingBuilder temperature(int temperature) {
            this.special = temperature;
            return this;
        }
    }

    public static IRecipe addRecipeToSteamMap(RecipeMap map, IRecipe recipe, String domain, String id) {
        try {
            if (recipe.getPower() > 0 && (recipe.getPower()-1)*2 <= Tier.LV.getVoltage()) {
                map.RB().ii(recipe.getInputItems()).io(recipe.getOutputItems(false)).outputChances(recipe.getOutputChances()).add(domain, id,recipe.getDuration()* 2L, recipe.getPower() * 2, 0, 1);
            }
        } catch (Exception e) {
            System.out.println("bleh");
        }
        return recipe;
    }
}
