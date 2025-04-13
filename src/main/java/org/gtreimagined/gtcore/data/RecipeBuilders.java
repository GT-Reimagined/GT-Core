package org.gtreimagined.gtcore.data;


import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;

public class RecipeBuilders {
    public static class BlastingBuilder extends RecipeBuilder {
        public BlastingBuilder temperature(int temperature) {
            this.special = temperature;
            return this;
        }
    }
}
