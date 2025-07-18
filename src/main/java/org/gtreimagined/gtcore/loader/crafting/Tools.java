package org.gtreimagined.gtcore.loader.crafting;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.GTCoreConfig;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtcore.data.GTCoreTools;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.recipe.RecipeBuilders;
import org.gtreimagined.gtlib.recipe.ingredient.PropertyIngredient;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gtlib.data.GTLibMaterials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.*;
import static org.gtreimagined.gtlib.material.MaterialTags.*;
import static org.gtreimagined.gtlib.recipe.RecipeBuilders.CROWBAR_BUILDER;
import static org.gtreimagined.gtlib.recipe.RecipeBuilders.PROBE_BUILDER;

public class Tools {
    public static void init(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider) {
        provider.removeRecipe(new ResourceLocation("farmersdelight", "flint_knife"));
        provider.removeRecipe(new ResourceLocation("farmersdelight", "iron_knife"));
        provider.removeRecipe(new ResourceLocation("farmersdelight", "golden_knife"));
        provider.removeRecipe(new ResourceLocation("farmersdelight", "diamond_knife"));

        toolPartRecipes(consumer, provider);
        vanillaToolRecipes(consumer, provider);

        if (GTAPI.isModLoaded(Ref.MOD_TOP)) {
            ARMOR.getAll().forEach((m, a) ->{
                provider.addToolRecipe(PROBE_BUILDER.get(m.getId() + "_" + GTTools.HELMET.getId()), consumer, Ref.ID, m.getId() + "_helmet_with_probe", "gt_armor", GTTools.HELMET.getToolStack(m), of('H', PropertyIngredient.builder("helmet").itemStacks(GTTools.HELMET.getToolStack(m).getItem()).build(), 'P', RegistryUtils.getItemFromID(Ref.MOD_TOP, "probe")), "HP");
            });

        }

        ARMOR.all().forEach(m ->{
            TagKey<Item> input = m.has(GEM) ? GEM.getMaterialTag(m) : PLATE.getMaterialTag(m);
            ImmutableMap.Builder<Character, Object> builder = ImmutableMap.builder();
            builder.put('I', input);
            if (!m.has(GEM)) builder.put('H', HAMMER.getTag());
            String[] strings = !m.has(GEM) ? new String[]{"III", "IHI"} : new String[]{"III", "I I"};
            provider.addStackRecipe(consumer, Ref.ID, "", "gt_armor", GTTools.HELMET.getToolStack(m),
                    builder.build(), strings);
            strings = !m.has(GEM) ? new String[]{"IHI", "III", "III"} : new String[]{"I I", "III", "III"};
            provider.addStackRecipe(consumer, Ref.ID, "", "gt_armor", GTTools.CHESTPLATE.getToolStack(m),
                    builder.build(), strings);
            strings = !m.has(GEM) ? new String[]{"III", "IHI", "I I"} : new String[]{"III", "I I", "I I"};
            provider.addStackRecipe(consumer, Ref.ID, "", "gt_armor", GTTools.LEGGINGS.getToolStack(m),
                    builder.build(), strings);
            strings = !m.has(GEM) ? new String[]{"I I", "IHI"} : new String[]{"I I", "I I"};
            provider.addStackRecipe(consumer, Ref.ID, "", "gt_armor", GTTools.BOOTS.getToolStack(m),
                    builder.build(), strings);
        });
       TOOLS.getAll().forEach((m, t) -> {
           TagKey<Item> rod = t.handleMaterial().has(ROD) ? ROD.getMaterialTag(t.handleMaterial()) : ROD.getMaterialTag(Wood);
           GTToolType[] toolHeadTypes = new GTToolType[]{PICKAXE, AXE, SWORD, SHOVEL, HOE, FILE, SAW, HAMMER, SCREWDRIVER, SCYTHE};
           Arrays.stream(toolHeadTypes).forEach(type -> {
               if (type == SCYTHE && GTAPI.isModLoaded("gtspartan")) return;
               if (t.toolTypes().contains(type)){
                   if (type.getMaterialTypeItem() == null) return;
                   if (m.has(type.getMaterialTypeItem())){
                       provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_" + type.getId() + "_from_" + type.getMaterialTypeItem().getId(), "gt_tools_from_tool_parts", type.getToolStack(m), of('T', type.getMaterialTypeItem().getMaterialTag(m), 'R', rod), "T", "R");
                   }
               }
           });
           boolean replaceVanilla = (m != Iron && m != Gold && m != Diamond) || GTCoreConfig.VANILLA_OVERRIDES.get();
           if (m.has(INGOT) || m.has(GEM) || m == Wood){
               TagKey<Item> plateGem = m.has(GEM) ? GEM.getMaterialTag(m) : m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
               TagKey<Item> ingotGem = m.has(GEM) ? GEM.getMaterialTag(m) : INGOT.getMaterialTag(m);

               if (t.toolTypes().contains(GTCoreTools.POCKET_MULTITOOL) && m != Wood){
                   if (m.has(RING) && m.has(FILE_HEAD) && m.has(SAW_BLADE) && m.has(SCREWDRIVER_TIP) && m.has(SWORD_BLADE)){
                       provider.addStackRecipe(consumer, GTCore.ID, "", "tools", GTCoreTools.POCKET_MULTITOOL.getToolStack(m),
                               ImmutableMap.<Character, Object>builder()
                                       .put('F', FILE_HEAD.getMaterialTag(m))
                                       .put('S', SCREWDRIVER_TIP.getMaterialTag(m))
                                       .put('s', SAW_BLADE.getMaterialTag(m))
                                       .put('P', m.has(GEM) ? GEM.getMaterialTag(m) : m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m))
                                       .put('R', RING.getMaterialTag(m))
                                       .put('W', SWORD_BLADE.getMaterialTag(m)).build(), "SsR", "FPW", "RW ");
                   }
               }
               if (t.toolTypes().contains(WRENCH)){
                   provider.addStackRecipe(consumer, GTCore.ID, "", "", WRENCH.getToolStack(m),
                           of('H', HAMMER.getTag(), 'P', plateGem), "PHP", "PPP", " P ");
               }
               if (t.toolTypes().contains(HAMMER)){
                   provider.addStackRecipe(consumer, GTCore.ID, "", "", HAMMER.getToolStack(m),
                           of('R', rod, 'P', ingotGem), "PP ", "PPR", "PP ");
               }
               if (t.toolTypes().contains(SOFT_HAMMER) && m.has(RUBBERTOOLS)){
                   TagKey<Item> ingotGem1 = m == Wood ? ItemTags.PLANKS : m.has(GEM) ? GEM.getMaterialTag(m) : INGOT.getMaterialTag(m);
                   provider.addStackRecipe(consumer, GTCore.ID, "", "", SOFT_HAMMER.getToolStack(m),
                           of('R', rod, 'P', ingotGem1), "PP ", "PPR", "PP ");
               }
               if (t.toolTypes().contains(MORTAR)){
                   provider.addStackRecipe(consumer, GTCore.ID, "", "", MORTAR.getToolStack(m),
                           of('S', TagUtils.getForgelikeItemTag("stone"), 'P', ingotGem), " P ", "SPS", "SSS");
               }
               if (t.toolTypes().contains(FILE)){
                   provider.addStackRecipe(consumer, GTCore.ID, "", "", FILE.getToolStack(m),
                           of('R', rod, 'P', plateGem), "P", "P", "R");
               }
               if (t.toolTypes().contains(SCREWDRIVER) && m.has(ROD)){
                   provider.addStackRecipe(consumer, GTCore.ID, "", "", SCREWDRIVER.getToolStack(m),
                           of('R', rod, 'P', ROD.getMaterialTag(m),'F', GTTools.FILE.getTag(), 'H', GTTools.HAMMER.getTag()), " FP", " PH", "R  ");
               }
               if (t.toolTypes().contains(PLUNGER) && m.has(ROD)){
                   RUBBERTOOLS.all().stream().filter(r -> r.has(PLATE) && r != Wood).forEach(r -> {
                       provider.addStackRecipe(consumer, GTCore.ID, m.getId() + "_plunger_with_" + r.getId(), "", PLUNGER.getToolStack(m),
                               of('P', ROD.getMaterialTag(m), 'R', PLATE.getMaterialTag(r),'F', GTTools.FILE.getTag(), 'W', WIRE_CUTTER.getTag()), "WRR", " PR", "P F");
                   });

               }
               if (t.toolTypes().contains(SAW)){
                   if (m.has(GEM)){
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", SAW.getToolStack(m),
                               of('R', rod, 'P', plateGem,'F', GTTools.FILE.getTag()), "PPR", "F  ");
                   } else {
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", SAW.getToolStack(m),
                               of('R', rod, 'P', plateGem,'F', GTTools.FILE.getTag(), 'H', GTTools.HAMMER.getTag()), "PPR", "FH ");
                   }
               }
               if (t.toolTypes().contains(SCYTHE) && !GTAPI.isModLoaded("gtspartan")){
                   if (m.has(FLINT)){
                       if (!GTAPI.isModLoaded("tfc")) {
                           provider.addStackRecipe(consumer, GTCore.ID, "", "", SCYTHE.getToolStack(m),
                                   of('R', rod, 'P', ingotGem), "PPP", "  R");
                       }
                   } else if (m.has(GEM)){
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", SCYTHE.getToolStack(m),
                               of('R', rod, 'P', plateGem, 'I', ingotGem,'F', GTTools.FILE.getTag()), "PPI", " FR", "  R");
                   } else {
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", SCYTHE.getToolStack(m),
                               of('R', rod, 'P', plateGem, 'I', ingotGem,'F', GTTools.FILE.getTag(), 'H', GTTools.HAMMER.getTag()), "PPI", "HFR", "  R");
                   }
               }
               if (t.toolTypes().contains(WIRE_CUTTER)){
                   ImmutableMap.Builder<Character, Object> builder = ImmutableMap.builder();
                   builder.put('R', rod).put('P', plateGem).put('F', GTTools.FILE.getTag()).put('H', GTTools.HAMMER.getTag()).put('S', SCREWDRIVER.getTag());
                   if (m.has(SCREW)) builder.put('W', SCREW.getMaterialTag(m));
                   String last = m.has(SCREW) ? "RWR" : "R R";
                   provider.addStackRecipe(consumer, GTCore.ID, "", "", WIRE_CUTTER.getToolStack(m),
                           builder.build(), "PFP", "HPS", last);
               }
               if (t.toolTypes().contains(BRANCH_CUTTER)){
                   ImmutableMap.Builder<Character, Object> builder = ImmutableMap.builder();
                   builder.put('R', rod).put('P', plateGem).put('F', GTTools.FILE.getTag()).put('S', SCREWDRIVER.getTag());
                   if (m.has(SCREW)) builder.put('W', SCREW.getMaterialTag(m));
                   String last = m.has(SCREW) ? "RWR" : "R R";
                   provider.addStackRecipe(consumer, GTCore.ID, "", "", BRANCH_CUTTER.getToolStack(m),
                           builder.build(), "PFP", "PSP", last);
               }
               if (t.toolTypes().contains(CROWBAR) && m.has(ROD)){
                   provider.addToolRecipe(CROWBAR_BUILDER.get(m.getId() + "_" + CROWBAR.getId()), consumer, GTCore.ID, "", "gt_crowbars", CROWBAR.getToolStack(m), of('H', GTTools.HAMMER.getTag(), 'C', PropertyIngredient.builder("secondary").itemTags(TagUtils.getForgelikeItemTag("dyes")).build(), 'R', ROD.getMaterialTag(m), 'F', GTTools.FILE.getTag()), "HCR", "CRC", "RCF");
               }

               if (t.toolTypes().contains(PICKAXE) && replaceVanilla){
                   if (m.has(FLINT)){
                       if (!GTAPI.isModLoaded("tfc")) {
                           provider.addStackRecipe(consumer, GTCore.ID, "", "", PICKAXE.getToolStack(m),
                                   of('R', rod, 'P', ingotGem), "PPP", " R ");
                       }
                   } else if (m.has(GEM)){
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", PICKAXE.getToolStack(m),
                               of('R', rod, 'P', plateGem, 'I', ingotGem,'F', GTTools.FILE.getTag()), "PII", "FR ", " R ");
                   } else {
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", PICKAXE.getToolStack(m),
                               of('R', rod, 'P', plateGem, 'I', ingotGem,'F', GTTools.FILE.getTag(), 'H', GTTools.HAMMER.getTag()), "PII", "FRH", " R ");
                   }
               }

               if (t.toolTypes().contains(AXE) && replaceVanilla){
                   if (m.has(FLINT)){
                       if (!GTAPI.isModLoaded("tfc")) {
                           provider.addStackRecipe(consumer, GTCore.ID, "", "", AXE.getToolStack(m),
                                   of('R', rod, 'P', ingotGem), "PP", "PR");
                       }
                   } else if (m.has(GEM)){
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", AXE.getToolStack(m),
                               of('R', rod, 'P', plateGem, 'I', ingotGem,'F', GTTools.FILE.getTag()), "PI", "PR", "FR");
                   } else {
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", AXE.getToolStack(m),
                               of('R', rod, 'P', plateGem, 'I', ingotGem,'F', GTTools.FILE.getTag(), 'H', GTTools.HAMMER.getTag()), "PIH", "PR ", "FR ");
                   }
               }

               if (t.toolTypes().contains(SHOVEL) && replaceVanilla){
                   if (m.has(FLINT)){
                       if (!GTAPI.isModLoaded("tfc")) {
                           provider.addStackRecipe(consumer, GTCore.ID, "", "", SHOVEL.getToolStack(m),
                                   of('R', rod, 'P', ingotGem), "P", "R");
                       }
                   } else if (m.has(GEM)){
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", SHOVEL.getToolStack(m),
                               of('R', rod, 'P', plateGem,'F', GTTools.FILE.getTag()), "FP", " R", " R");
                   } else {
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", SHOVEL.getToolStack(m),
                               of('R', rod, 'P', plateGem,'F', GTTools.FILE.getTag(), 'H', GTTools.HAMMER.getTag()), "FPH", " R ", " R ");
                   }
               }

               if (t.toolTypes().contains(SWORD) && replaceVanilla){
                   if (m.has(FLINT)){
                       if (!GTAPI.isModLoaded("tfc")) {
                           provider.addStackRecipe(consumer, GTCore.ID, "", "", SWORD.getToolStack(m),
                                   of('R', rod, 'P', ingotGem), "P", "P", "R");
                       }
                   } else if (m.has(GEM)){
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", SWORD.getToolStack(m),
                               of('R', rod, 'P', plateGem,'F', GTTools.FILE.getTag()), "FP", " P", " R");
                   } else {
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", SWORD.getToolStack(m),
                               of('R', rod, 'P', plateGem,'F', GTTools.FILE.getTag(), 'H', GTTools.HAMMER.getTag()), "FPH", " P ", " R ");
                   }
               }

               if (t.toolTypes().contains(HOE) && replaceVanilla){
                   if (m.has(FLINT)){
                       if (!GTAPI.isModLoaded("tfc")) {
                           provider.addStackRecipe(consumer, GTCore.ID, "", "", HOE.getToolStack(m),
                                   of('R', rod, 'P', ingotGem), "PP", " R");
                       }
                   } else if (m.has(GEM)){
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", HOE.getToolStack(m),
                               of('R', rod, 'P', plateGem, 'I', ingotGem,'F', GTTools.FILE.getTag()), "PI", "FR", " R");
                   } else {
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", HOE.getToolStack(m),
                               of('R', rod, 'P', plateGem, 'I', ingotGem,'F', GTTools.FILE.getTag(), 'H', GTTools.HAMMER.getTag()), "PIH", "FR ", " R ");
                   }
               }

               if (t.toolTypes().contains(KNIFE)){
                   if (m.has(FLINT)){
                       if (!GTAPI.isModLoaded("tfc")) {
                           provider.addStackRecipe(consumer, GTCore.ID, "", "", KNIFE.getToolStack(m),
                                   of('R', rod, 'P', ingotGem), "RP");
                       }
                   } else if (m.has(GEM)){
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", KNIFE.getToolStack(m),
                               of('R', rod, 'P', plateGem,'F', GTTools.FILE.getTag()), "FP", " R");
                   } else {
                       provider.addStackRecipe(consumer, GTCore.ID, "", "", KNIFE.getToolStack(m),
                               of('R', rod, 'P', plateGem,'F', GTTools.FILE.getTag(), 'H', GTTools.HAMMER.getTag()), "FP", "HR");
                   }
               }

           }
       });
    }

    private static void vanillaToolRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        provider.addItemRecipe(consumer, GTCore.ID, "stone_pickaxe", "tools", Items.STONE_PICKAXE, of('R', GTCoreTags.STONE_ROCKS, 'S', Items.STICK), "RRR", " S ");
        provider.addItemRecipe(consumer, GTCore.ID, "stone_axe", "tools", Items.STONE_AXE, of('R', GTCoreTags.STONE_ROCKS, 'S', Items.STICK), "RR", "RS");
        provider.addItemRecipe(consumer, GTCore.ID, "stone_shovel", "tools", Items.STONE_SHOVEL, of('R', GTCoreTags.STONE_ROCKS, 'S', Items.STICK), "R", "S");
        provider.addItemRecipe(consumer, GTCore.ID, "stone_hoe", "tools", Items.STONE_HOE, of('R', GTCoreTags.STONE_ROCKS, 'S', Items.STICK), "RR", " S");
        provider.addItemRecipe(consumer, GTCore.ID, "stone_sword", "tools", Items.STONE_SWORD, of('R', GTCoreTags.STONE_ROCKS, 'S', Items.STICK), "R", "R", "S");
    }

    private static void toolPartRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        PICKAXE_HEAD.all().forEach(m -> {
            if (m.has(GEM)){
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", PICKAXE_HEAD.get(m),
                        of('G', GEM.getMaterialTag(m), 'F', FILE.getTag()), "GGG", "F  ");
            } else if (m.has(INGOT)){
                TagKey<Item> plate = m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", PICKAXE_HEAD.get(m),
                        of('P', plate, 'I', INGOT.getMaterialTag(m), 'H', HAMMER.getTag(), 'F', FILE.getTag()), "PII", "F H");
            }
        });
        AXE_HEAD.all().forEach(m -> {
            if (m.has(GEM)){
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", AXE_HEAD.get(m),
                        of('G', GEM.getMaterialTag(m), 'F', FILE.getTag()), "GG", "G ", "F ");
            } else if (m.has(INGOT)){
                TagKey<Item> plate = m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", AXE_HEAD.get(m),
                        of('P', plate, 'I', INGOT.getMaterialTag(m), 'H', HAMMER.getTag(), 'F', FILE.getTag()), "PIH", "P  ", "F  ");
            }
        });
        SHOVEL_HEAD.all().forEach(m -> {
            if (m.has(GEM)){
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", SHOVEL_HEAD.get(m),
                        of('G', GEM.getMaterialTag(m), 'F', FILE.getTag()), "FG");
            } else if (m.has(INGOT)){
                TagKey<Item> plate = m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", SHOVEL_HEAD.get(m),
                        of('P', plate, 'H', HAMMER.getTag(), 'F', FILE.getTag()), "FPH");
            }
        });
        HOE_HEAD.all().forEach(m -> {
            if (m.has(GEM)){
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", HOE_HEAD.get(m),
                        of('G', GEM.getMaterialTag(m), 'F', FILE.getTag()), "GG", "F ");
            } else if (m.has(INGOT)){
                TagKey<Item> plate = m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", HOE_HEAD.get(m),
                        of('P', plate, 'I', INGOT.getMaterialTag(m), 'H', HAMMER.getTag(), 'F', FILE.getTag()), "PIH", "F  ");
            }
        });
        SWORD_BLADE.all().forEach(m -> {
            if (m.has(GEM)){
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", SWORD_BLADE.get(m),
                        of('G', GEM.getMaterialTag(m), 'F', FILE.getTag()), "FG", " G");
            } else if (m.has(INGOT)){
                TagKey<Item> plate = m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", SWORD_BLADE.get(m),
                        of('P', plate, 'H', HAMMER.getTag(), 'F', FILE.getTag()), "FPH", " P ");
            }
        });
        HAMMER_HEAD.all().forEach(m -> {
            if (!m.has(GEM) && !m.has(INGOT)) return;
            TagKey<Item> input = m.has(GEM) ? GEM.getMaterialTag(m) : INGOT.getMaterialTag(m);
            TagKey<Item> tool = m.has(GEM) ? FILE.getTag() : HAMMER.getTag();
            provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", HAMMER_HEAD.get(m),
                    of('I', input, 'H', tool), "II ", "IIH", "II ");
        });
        FILE_HEAD.all().forEach(m -> {
            if (!m.has(GEM) && !m.has(INGOT)) return;
            TagKey<Item> input = m.has(GEM) ? GEM.getMaterialTag(m) : m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
            TagKey<Item> tool = m.has(GEM) ? FILE.getTag() : KNIFE.getTag();
            provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", FILE_HEAD.get(m),
                    of('I', input, 'H', tool), "I ", "IH");
        });
        SAW_BLADE.all().forEach(m -> {
            if (m.has(GEM)){
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", SAW_BLADE.get(m),
                        of('G', GEM.getMaterialTag(m), 'F', FILE.getTag()), "GG", "F ");
            } else if (m.has(INGOT)){
                TagKey<Item> plate = m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
                provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", SAW_BLADE.get(m),
                        of('P', plate, 'H', HAMMER.getTag(), 'F', FILE.getTag()), "PP", "FH");
            }
        });
        SCREWDRIVER_TIP.all().forEach(m -> {
            if (!m.has(ROD)) return;
            provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", SCREWDRIVER_TIP.get(m),
                    of('R', ROD.getMaterialTag(m), 'F', FILE.getTag(), 'H', HAMMER.getTag()), "HR", "RF");
        });
        if (!GTAPI.isModLoaded("gtspartan")) {
            SCYTHE_BLADE.all().forEach(m -> {
                if (m.has(GEM)){
                    provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", SCYTHE_BLADE.get(m),
                            of('G', GEM.getMaterialTag(m), 'F', FILE.getTag()), "GGG", " F ");
                } else if (m.has(INGOT)){
                    TagKey<Item> plate = m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
                    provider.addItemRecipe(consumer, GTCore.ID, "", "tool_heads", SCYTHE_BLADE.get(m),
                            of('P', plate, 'I', INGOT.getMaterialTag(m), 'H', HAMMER.getTag(), 'F', FILE.getTag()), "PPI", "HF ");
                }
            });
        }
    }
}
