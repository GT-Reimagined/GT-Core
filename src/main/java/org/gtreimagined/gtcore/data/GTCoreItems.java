package org.gtreimagined.gtcore.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.cover.CoverSelectorTag;
import org.gtreimagined.gtcore.item.ItemFertilizer;
import org.gtreimagined.gtcore.item.ItemHazmatArmor;
import org.gtreimagined.gtcore.item.ItemMagnifyingGlass;
import org.gtreimagined.gtcore.item.ItemMatch;
import org.gtreimagined.gtcore.item.ItemMixedMetal;
import org.gtreimagined.gtcore.item.ItemPowerUnit;
import org.gtreimagined.gtcore.item.ItemRadaway;
import org.gtreimagined.gtcore.item.ItemSelectorTag;
import org.gtreimagined.gtcore.item.ItemTape;
import org.gtreimagined.gtcore.tree.item.ItemRubberBoat;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.texture.Texture;

import static org.gtreimagined.gtlib.data.GTLibMaterials.Diamond;


public class GTCoreItems {

    public static final ImmutableMap<Integer, RecipeIngredient> SELECTOR_TAG_INGREDIENTS;
    public static final ImmutableMap<Integer, Item> SELECTOR_TAG_ITEMS;
    public static final ImmutableMap<Integer, CoverFactory> SELECTOR_TAG_COVERS;

    public static ItemBasic<?> StickyResin = new ItemBasic<>(GTCore.ID, "sticky_resin");
    public static ItemRubberBoat RubberBoat = new ItemRubberBoat();


    public static ItemBasic<?> CompressedFireClay = new ItemBasic<>(GTCore.ID, "compressed_fire_clay").tip("Brick Shaped");
    public static ItemBasic<?> FireBrick = new ItemBasic<>(GTCore.ID, "fire_brick").tip("Heat Resistant");
    public static ItemBasic<?> Biochaff = new ItemBasic<>(GTCore.ID, "biochaff");
    public static ItemBasic<?> Plantball = new ItemBasic<>(GTCore.ID, "plantball");
    public static ItemFertilizer Fertilizer = new ItemFertilizer();
    public static ItemBasic<?> CarbonFibre = new ItemBasic<>(GTCore.ID, "raw_carbon_fibre");
    public static ItemBasic<?> CarbonMesh = new ItemBasic<>(GTCore.ID, "carbon_mesh");
    public static ItemBasic<?> CoalBall = new ItemBasic<>(GTCore.ID, "coal_ball");
    public static ItemBasic<?> CompressedCoalBall = new ItemBasic<>(GTCore.ID, "compressed_coal_ball");
    public static ItemBasic<?> CoalChunk = new ItemBasic<>(GTCore.ID, "coal_chunk");
    public static ItemBasic<?> DiamondSawBlade = new ItemBasic<>(GTCore.ID, "diamond_saw_blade");
    public static ItemBasic<?> DiamondGrindHead = new ItemBasic<>(GTCore.ID, "diamond_grind_head");
    public static ItemBasic<?> TungstenGrindHead = new ItemBasic<>(GTCore.ID, "tungsten_grind_head");
    public static ItemMixedMetal MixedMetalIngot = new ItemMixedMetal();
    public static ItemBasic<?> AdvancedAlloy = new ItemBasic<>(GTCore.ID, "advanced_alloy");
    public static ItemBasic<?> IridiumAlloyIngot = new ItemBasic<>(GTCore.ID, "iridium_alloy_ingot").tip("Used to make Iridium Plates");
    public static ItemBasic<?> IridiumReinforcedPlate = new ItemBasic<>(GTCore.ID, "iridium_reinforced_plate").tip("GT2s Most Expensive Component");
    public static ItemBasic<?> IridiumNeutronReflector = new ItemBasic<>(GTCore.ID, "iridium_neutron_reflector").tip("Indestructible");
    public static ItemBasic<?> Radaway = new ItemRadaway().tip("Pill that cures Effects of Radiation");
    public static ItemBasic<?> EmptyWaxPill = new ItemBasic<>(GTCore.ID, "empty_wax_pill").tip("Placebo");
    public static ItemBasic<?> GTCredit = new ItemBasic<>(GTCore.ID, "gt_credit");
    public static ItemBasic<?> EmptyBlueprint = new ItemBasic<>(GTCore.ID, "empty_blueprint");
    public static ItemBasic<?> Blueprint = new ItemBasic<>(GTCore.ID, "blueprint");
    public static ItemHazmatArmor UniversalHazardSuitMask = new ItemHazmatArmor(EquipmentSlot.HEAD, "universal_hazard_suit_mask");
    public static ItemHazmatArmor UniversalHazardSuitShirt = new ItemHazmatArmor(EquipmentSlot.CHEST, "universal_hazard_suit_shirt");
    public static ItemHazmatArmor UniversalHazardSuitPants = new ItemHazmatArmor(EquipmentSlot.LEGS, "universal_hazard_suit_pants");
    public static ItemHazmatArmor UniversalHazardSuitBoots = new ItemHazmatArmor(EquipmentSlot.FEET, "universal_hazard_suit_boots");

    public static ItemBasic<?> LighterEmpty = new ItemBasic<>(GTCore.ID, "lighter_empty");
    public static ItemBasic<ItemMatch> Match = new ItemMatch(GTCore.ID, "match", new Item.Properties().tab(Ref.TAB_ITEMS));
    public static ItemBasic<ItemMatch> MatchBook = new ItemMatch(GTCore.ID, "match_book", new Item.Properties().tab(Ref.TAB_ITEMS).defaultDurability(64));
    public static ItemBasic<ItemMatch> Lighter = new ItemMatch(GTCore.ID, "lighter", new Item.Properties().tab(Ref.TAB_ITEMS).defaultDurability(100));
    public static ItemMagnifyingGlass GlassMagnifyingGlass = new ItemMagnifyingGlass(GTCoreMaterials.Glass, 64);
    public static ItemMagnifyingGlass DiamondMagnifyingGlass = new ItemMagnifyingGlass(Diamond, 1561);

    public static ItemBasic<?> TapeEmpty = new ItemTape(GTCore.ID, "tape_empty");
    public static ItemBasic<?> Tape = new ItemTape(GTCore.ID, "tape", 10000);
    public static ItemBasic<?> DuctTapeEmpty = new ItemTape(GTCore.ID, "duct_tape_empty");
    public static ItemBasic<?> DuctTape = new ItemTape(GTCore.ID, "duct_tape", 100000);
    public static ItemBasic<?> FALDuctTapeEmpty = new ItemTape(GTCore.ID, "fal_duct_tape_empty");
    public static ItemBasic<?> FALDuctTape = new ItemTape(GTCore.ID, "fal_duct_tape", 10000000);

    public static ItemBasic<?> MotorLV = new ItemBasic<>(GTCore.ID, "motor_lv");
    public static ItemBasic<?> MotorMV = new ItemBasic<>(GTCore.ID, "motor_mv");
    public static ItemBasic<?> MotorHV = new ItemBasic<>(GTCore.ID, "motor_hv");
    public static ItemBasic<?> MotorEV = new ItemBasic<>(GTCore.ID, "motor_ev");
    public static ItemBasic<?> MotorIV = new ItemBasic<>(GTCore.ID, "motor_iv");

    public static ItemPowerUnit PowerUnitLV = new ItemPowerUnit(GTCore.ID, "power_unit_lv", GTCoreMaterials.Aluminium);
    public static ItemPowerUnit PowerUnitMV = new ItemPowerUnit(GTCore.ID, "power_unit_mv", GTCoreMaterials.StainlessSteel);
    public static ItemPowerUnit PowerUnitHV = new ItemPowerUnit(GTCore.ID, "power_unit_hv", GTCoreMaterials.Titanium);
    public static ItemPowerUnit SmallPowerUnit = new ItemPowerUnit(GTCore.ID, "small_power_unit", GTCoreMaterials.Aluminium);
    public static ItemPowerUnit JackhammerPowerUnit = new ItemPowerUnit(GTCore.ID, "jackhammer_power_unit", Material.NULL);

    public static ItemBasic<?> EmptyShape = new ItemBasic<>(GTCore.ID, "empty_shape_plate", "molds/").tip("Raw plate to make Molds and Extruder Shapes");
    public static ItemBasic<?> PlateMold = new ItemBasic<>(GTCore.ID, "plate_mold", "molds/").tip("Mold for making Plates");
    public static ItemBasic<?> CasingMold = new ItemBasic<>(GTCore.ID, "casing_mold", "molds/").tip("Mold for making Item Casings");
    public static ItemBasic<?> GearMold = new ItemBasic<>(GTCore.ID, "gear_mold", "molds/").tip("Mold for making Gears");
    public static ItemBasic<?> SmallGearMold = new ItemBasic<>(GTCore.ID, "small_gear_mold", "molds/").tip("Mold for making Small Gears");
    public static ItemBasic<?> CoinageMold = new ItemBasic<>(GTCore.ID, "coinage_mold", "molds/").tip("Secure Mold for making Coins (Don't lose it!)");
    public static ItemBasic<?> BottleMold = new ItemBasic<>(GTCore.ID, "bottle_mold", "molds/").tip("Mold for making Bottles");
    public static ItemBasic<?> IngotMold = new ItemBasic<>(GTCore.ID, "ingot_mold", "molds/").tip("Mold for making Ingots");
    public static ItemBasic<?> LongRodMold = new ItemBasic<>(GTCore.ID, "long_rod_mold", "molds/").tip("Mold for making long rods");
    public static ItemBasic<?> BallMold = new ItemBasic<>(GTCore.ID, "ball_mold", "molds/").tip("Mold for making Balls");
    public static ItemBasic<?> BlockMold = new ItemBasic<>(GTCore.ID, "block_mold", "molds/").tip("Mold for making Blocks");
    public static ItemBasic<?> NuggetMold = new ItemBasic<>(GTCore.ID, "nugget_mold", "molds/").tip("Mold for making Nuggets");
    public static ItemBasic<?> AnvilMold = new ItemBasic<>(GTCore.ID, "anvil_mold", "molds/").tip("Mold for making Anvils");
    public static ItemBasic<?> PlateShape = new ItemBasic<>(GTCore.ID, "plate_shape", "molds/").tip("Shape for making Plates");
    public static ItemBasic<?> CasingShape = new ItemBasic<>(GTCore.ID, "casing_shape", "molds/").tip("Shape for making Item Casings");
    public static ItemBasic<?> RodShape = new ItemBasic<>(GTCore.ID, "rod_shape", "molds/").tip("Shape for making Rods");
    public static ItemBasic<?> LongRodShape = new ItemBasic<>(GTCore.ID, "long_rod_shape", "molds/").tip("Shape for making Long Rods");
    public static ItemBasic<?> BoltShape = new ItemBasic<>(GTCore.ID, "bolt_shape", "molds/").tip("Shape for making Bolts");
    public static ItemBasic<?> RingShape = new ItemBasic<>(GTCore.ID, "ring_shape", "molds/").tip("Shape for making Rings");
    public static ItemBasic<?> FoilShape = new ItemBasic<>(GTCore.ID, "foil_shape", "molds/").tip("Shape for making Foils");
    public static ItemBasic<?> CellShape = new ItemBasic<>(GTCore.ID, "cell_shape", "molds/").tip("Shape for making Cells");
    public static ItemBasic<?> BottleShape = new ItemBasic<>(GTCore.ID, "bottle_shape", "molds/").tip("Shape for making Bottles");
    public static ItemBasic<?> IngotShape = new ItemBasic<>(GTCore.ID, "ingot_shape", "molds/").tip("Shape for making Ingots");
    public static ItemBasic<?> WireShape = new ItemBasic<>(GTCore.ID, "wire_shape", "molds/").tip("Shape for making Wires");
    public static ItemBasic<?> FineWireShape = new ItemBasic<>(GTCore.ID, "fine_wire_shape", "molds/").tip("Shape for making Fine Wires");
    public static ItemBasic<?> TinyPlateShape = new ItemBasic<>(GTCore.ID, "tiny_plate_shape", "molds/").tip("Shape for making Tiny Plates");
    public static ItemBasic<?> TinyPipeShape = new ItemBasic<>(GTCore.ID, "tiny_pipe_shape", "molds/").tip("Shape for making Tiny Pipes");
    public static ItemBasic<?> SmallPipeShape = new ItemBasic<>(GTCore.ID, "small_pipe_shape", "molds/").tip("Shape for making Small Pipes");
    public static ItemBasic<?> NormalPipeShape = new ItemBasic<>(GTCore.ID, "normal_pipe_shape", "molds/").tip("Shape for making Normal Pipes");
    public static ItemBasic<?> LargePipeShape = new ItemBasic<>(GTCore.ID, "large_pipe_shape", "molds/").tip("Shape for making Large Pipes");
    public static ItemBasic<?> HugePipeShape = new ItemBasic<>(GTCore.ID, "huge_pipe_shape", "molds/").tip("Shape for making Huge Pipes");
    public static ItemBasic<?> BlockShape = new ItemBasic<>(GTCore.ID, "block_shape", "molds/").tip("Shape for making Blocks");
    public static ItemBasic<?> SwordBladeShape = new ItemBasic<>(GTCore.ID, "sword_blade_shape", "molds/").tip("Shape for making Sword Blades");
    public static ItemBasic<?> PickaxeHeadShape = new ItemBasic<>(GTCore.ID, "pickaxe_head_shape", "molds/").tip("Shape for making Pickaxe Heads");
    public static ItemBasic<?> ShovelHeadShape = new ItemBasic<>(GTCore.ID, "shovel_head_shape", "molds/").tip("Shape for making Shovel Heads");
    public static ItemBasic<?> AxeHeadShape = new ItemBasic<>(GTCore.ID, "axe_head_shape", "molds/").tip("Shape for making Axe Heads");
    public static ItemBasic<?> HoeHeadShape = new ItemBasic<>(GTCore.ID, "hoe_head_shape", "molds/").tip("Shape for making Hoe Heads");
    public static ItemBasic<?> HammerHeadShape = new ItemBasic<>(GTCore.ID, "hammer_head_shape", "molds/").tip("Shape for making Hammer Heads");
    public static ItemBasic<?> FileHeadShape = new ItemBasic<>(GTCore.ID, "file_head_shape", "molds/").tip("Shape for making File Heads");
    public static ItemBasic<?> SawBladeShape = new ItemBasic<>(GTCore.ID, "saw_blade_shape", "molds/").tip("Shape for making Saw Heads");
    public static ItemBasic<?> KnifeBladeShape = new ItemBasic<>(GTCore.ID, "knife_blade_shape", "molds/").tip("Shape for making Knife Blades");
    public static ItemBasic<?> ChiselHeadShape = new ItemBasic<>(GTCore.ID, "chisel_head_shape", "molds/").tip("Shape for making Chisel Heads");
    public static ItemBasic<?> JavelinHeadShape = new ItemBasic<>(GTCore.ID, "javelin_head_shape", "molds/").tip("Shape for making Javelin Heads");
    public static ItemBasic<?> PropickHeadShape = new ItemBasic<>(GTCore.ID, "propick_head_shape", "molds/").tip("Shape for making Prospector's Pick Heads");
    public static ItemBasic<?> MaceHeadShape = new ItemBasic<>(GTCore.ID, "mace_head_shape", "molds/").tip("Shape for making Mace Heads");
    public static ItemBasic<?> GearShape = new ItemBasic<>(GTCore.ID, "gear_shape", "molds/").tip("Shape for making Gears");
    public static ItemBasic<?> SmallGearShape = new ItemBasic<>(GTCore.ID, "small_gear_shape", "molds/").tip("Shape for making Small Gears");

    static {
        {
            ImmutableMap.Builder<Integer, RecipeIngredient> ingredientBuilder = ImmutableMap.builder();
            ImmutableMap.Builder<Integer, Item> itemBuilder = ImmutableMap.builder();
            ImmutableMap.Builder<Integer, CoverFactory> coverBuilder = ImmutableMap.builder();
            for (int i = 0; i <= 24; i++) {
                ItemSelectorTag selectorTag = new ItemSelectorTag(GTCore.ID, "selector_tag_"+i,i);
                ingredientBuilder.put(i, RecipeIngredient.of(selectorTag,1).setNoConsume());
                itemBuilder.put(i, selectorTag);
                if (i > 15) continue;
                int finalI = i;
                coverBuilder.put(i, CoverFactory.builder((source, tier, side, factory) -> new CoverSelectorTag(source, tier, side, factory, finalI))
                        .addTextures(new Texture(GTCore.ID, "block/cover/selector_tag/" + i), new Texture(GTCore.ID, "block/cover/selector_tag/underlay"))
                        .item((c, t) -> selectorTag).build(GTCore.ID, "selector_tag_"+i));
            }
            SELECTOR_TAG_INGREDIENTS = ingredientBuilder.build();
            SELECTOR_TAG_ITEMS = itemBuilder.build();
            SELECTOR_TAG_COVERS = coverBuilder.build();
        }
    }

    public static void init(){

    }
}
