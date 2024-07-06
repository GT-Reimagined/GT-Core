package io.github.gregtechintergalactical.gtcore.data;

import com.google.common.collect.ImmutableMap;
import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.cover.CoverRedstoneTorch;
import io.github.gregtechintergalactical.gtcore.cover.CoverSelectorTag;
import io.github.gregtechintergalactical.gtcore.item.ItemFertilizer;
import io.github.gregtechintergalactical.gtcore.item.ItemHazmatArmor;
import io.github.gregtechintergalactical.gtcore.item.ItemMagnifyingGlass;
import io.github.gregtechintergalactical.gtcore.item.ItemMatch;
import io.github.gregtechintergalactical.gtcore.item.ItemPowerUnit;
import io.github.gregtechintergalactical.gtcore.item.ItemRadaway;
import io.github.gregtechintergalactical.gtcore.item.ItemSelectorTag;
import io.github.gregtechintergalactical.gtcore.item.ItemTape;
import io.github.gregtechintergalactical.gtcore.tree.item.ItemRubberBoat;
import muramasa.antimatter.Ref;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.item.ItemMultiTextureBattery;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.antimatter.texture.Texture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;

import static io.github.gregtechintergalactical.gtcore.data.GTCoreMaterials.*;
import static muramasa.antimatter.data.AntimatterMaterials.Diamond;

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
    public static ItemBasic<?> IridiumAlloyIngot = new ItemBasic<>(GTCore.ID, "iridium_alloy_ingot").tip("Used to make Iridium Plates");
    public static ItemBasic<?> IridiumReinforcedPlate = new ItemBasic<>(GTCore.ID, "iridium_reinforced_plate").tip("GT2s Most Expensive Component");
    public static ItemBasic<?> IridiumNeutronReflector = new ItemBasic<>(GTCore.ID, "iridium_neutron_reflector").tip("Indestructible");
    public static ItemBasic<?> Radaway = new ItemRadaway().tip("Pill that cures Effects of Radiation");
    public static ItemBasic<?> EmptyWaxPill = new ItemBasic<>(GTCore.ID, "empty_wax_pill").tip("Placebo");
    public static ItemBasic<?> GTCredit = new ItemBasic<>(GTCore.ID, "gt_credit");
    public static ItemHazmatArmor UniversalHazardSuitMask = new ItemHazmatArmor(EquipmentSlot.HEAD, "universal_hazard_suit_mask");
    public static ItemHazmatArmor UniversalHazardSuitShirt = new ItemHazmatArmor(EquipmentSlot.CHEST, "universal_hazard_suit_shirt");
    public static ItemHazmatArmor UniversalHazardSuitPants = new ItemHazmatArmor(EquipmentSlot.LEGS, "universal_hazard_suit_pants");
    public static ItemHazmatArmor UniversalHazardSuitBoots = new ItemHazmatArmor(EquipmentSlot.FEET, "universal_hazard_suit_boots");

    public static ItemBasic<?> LighterEmpty = new ItemBasic<>(GTCore.ID, "lighter_empty");
    public static ItemBasic<ItemMatch> Match = new ItemMatch(GTCore.ID, "match", new Item.Properties().tab(Ref.TAB_ITEMS));
    public static ItemBasic<ItemMatch> MatchBook = new ItemMatch(GTCore.ID, "match_book", new Item.Properties().tab(Ref.TAB_ITEMS).defaultDurability(64));
    public static ItemBasic<ItemMatch> Lighter = new ItemMatch(GTCore.ID, "lighter", new Item.Properties().tab(Ref.TAB_ITEMS).defaultDurability(100));
    public static ItemMagnifyingGlass GlassMagnifyingGlass = new ItemMagnifyingGlass(Glass, 64);
    public static ItemMagnifyingGlass DiamondMagnifyingGlass = new ItemMagnifyingGlass(Diamond, 1561);

    public static ItemBasic<?> TapeEmpty = new ItemTape(GTCore.ID, "tape_empty");
    public static ItemBasic<?> Tape = new ItemTape(GTCore.ID, "tape", 10000);
    public static ItemBasic<?> DuctTapeEmpty = new ItemTape(GTCore.ID, "duct_tape_empty");
    public static ItemBasic<?> DuctTape = new ItemTape(GTCore.ID, "duct_tape", 100000);
    public static ItemBasic<?> FALDuctTapeEmpty = new ItemTape(GTCore.ID, "fal_duct_tape_empty");
    public static ItemBasic<?> FALDuctTape = new ItemTape(GTCore.ID, "fal_duct_tape", 10000000);

    public static ItemBasic<?> GlassTube = new ItemBasic<>(GTCore.ID, "glass_tube", "circuits/");

    public static ItemBasic<?> VacuumTube = new ItemBasic<>(GTCore.ID, "vacuum_tube", "circuits/");

    public static ItemBasic<?> NandChip = new ItemBasic<>(GTCore.ID, "nand_chip").tip("A very simple circuit");
    public static ItemBasic<?> AdvCircuitParts = new ItemBasic<>(GTCore.ID, "advanced_circuit_parts").tip("Used for making Advanced Circuits");
    public static ItemBasic<?> ComplexCircuitParts = new ItemBasic<>(GTCore.ID, "complex_circuit_parts").tip("Used for making Complex Circuits");
    public static ItemBasic<?> EtchedWiringMV = new ItemBasic<>(GTCore.ID, "copper_etched_wiring").tip("Circuit board parts");
    public static ItemBasic<?> EtchedWiringHV = new ItemBasic<>(GTCore.ID, "gold_etched_wiring").tip("Circuit board parts");
    public static ItemBasic<?> EtchedWiringEV = new ItemBasic<>(GTCore.ID, "platinum_etched_wiring").tip("Circuit board parts");
    public static ItemBasic<?> EngravedCrystalChip = new ItemBasic<>(GTCore.ID, "engraved_crystal_chip").tip("Needed for Circuits");
    public static ItemBasic<?> EngravedLapotronChip = new ItemBasic<>(GTCore.ID, "engraved_lapotron_chip").tip("Needed for Circuits");

    public static ItemBasic<?> Transistor = new ItemBasic<>(GTCore.ID, "transistor").tip("An integral part to Circuitry");
    public static ItemBasic<?> SMDTransistor = new ItemBasic<>(GTCore.ID, "smd_transistor").tip("An integral part to Circuitry");
    public static ItemBasic<?> Resistor = new ItemBasic<>(GTCore.ID, "resistor").tip("An integral part to Circuitry");
    public static ItemBasic<?> SMDResistor = new ItemBasic<>(GTCore.ID, "smd_resistor").tip("An integral part to Circuitry");
    public static ItemBasic<?> Diode = new ItemBasic<>(GTCore.ID, "diode").tip("An integral part to Circuitry");
    public static ItemBasic<?> SMDDiode = new ItemBasic<>(GTCore.ID, "smd_diode").tip("An integral part to Circuitry");
    public static ItemBasic<?> Capacitor = new ItemBasic<>(GTCore.ID, "capacitor").tip("An integral part to Circuitry");
    public static ItemBasic<?> SMDCapacitor = new ItemBasic<>(GTCore.ID, "smd_capacitor").tip("An integral part to Circuitry");

    public static ItemBasic<?> CircuitBoardBasic = new ItemBasic<>(GTCore.ID, "basic_circuit_board", "circuits/");
    public static ItemBasic<?> CircuitBoardAdvanced = new ItemBasic<>(GTCore.ID, "advanced_circuit_board", "circuits/");
    public static ItemBasic<?> CircuitBoardComplex = new ItemBasic<>(GTCore.ID, "complex_circuit_board", "circuits/");
    public static ItemBasic<?> CircuitBoardEmpty = new ItemBasic<>(GTCore.ID, "empty_circuit_board", "circuits/");
    public static ItemBasic<?> CircuitBoardProcessor = new ItemBasic<>(GTCore.ID, "processor_circuit_board", "circuits/");
    public static ItemBasic<?> CircuitBoardProcessorEmpty = new ItemBasic<>(GTCore.ID, "empty_processor_circuit_board", "circuits/");

    public static ItemBasic<?> CircuitBoardCoated = new ItemBasic<>(GTCore.ID, "coated_circuit_board", "circuits/").tip("The most basic Board");
    public static ItemBasic<?> CircuitBoardPhenolic = new ItemBasic<>(GTCore.ID, "phenolic_circuit_board", "circuits/").tip("A basic Board");
    public static ItemBasic<?> CircuitBoardPlastic = new ItemBasic<>(GTCore.ID, "plastic_circuit_board", "circuits/").tip("An advanced Board");
    public static ItemBasic<?> CircuitBoardEpoxy = new ItemBasic<>(GTCore.ID, "epoxy_circuit_board", "circuits/").tip("4th Tier Board");
    public static ItemBasic<?> CircuitBoardFiber = new ItemBasic<>(GTCore.ID, "fiber_reinforced_circuit_board", "circuits/").tip("5th Tier Board");
    public static ItemBasic<?> CircuitBoardMultiFiber = new ItemBasic<>(GTCore.ID, "multilayer_fiber_reinforced_circuit_board", "circuits/").tip("6th Tier Board");
    public static ItemBasic<?> CircuitBoardWetware = new ItemBasic<>(GTCore.ID, "wetware_circuit_board", "circuits/").tip("7th Tier Board");

    public static ItemBasic<?> CircuitBasic = new ItemBasic<>(GTCore.ID, "basic_circuit", "circuits/").tip("A Basic Circuit");
    public static ItemBasic<?> CircuitGood = new ItemBasic<>(GTCore.ID, "good_circuit", "circuits/").tip("A Good Circuit");
    public static ItemBasic<?> CircuitAdv = new ItemBasic<>(GTCore.ID, "advanced_circuit", "circuits/").tip("A Advanced Circuit");
    public static ItemBasic<?> CircuitComplex = new ItemBasic<>(GTCore.ID, "complex_circuit", "circuits/").tip("A Complex Circuit");
    public static ItemBasic<?> CircuitDataStorage = new ItemBasic<>(GTCore.ID, "data_storage_circuit", "circuits/");
    public static ItemBasic<?> CircuitDataControl = new ItemBasic<>(GTCore.ID, "data_control_circuit", "circuits/").tip("An Elite Circuit"); //could be considered BIO
    public static ItemBasic<?> CircuitEnergyFlow = new ItemBasic<>(GTCore.ID, "energy_flow_circuit", "circuits/").tip("A Master Circuit"); //maybe name futuristic
    //public static ItemBasic<?> CircuitElite = new ItemBasic<>(GTCore.ID, "elite_circuit", "circuits/").tip("A Elite Circuit");
    public static ItemBasic<?> CircuitFuturistic = new ItemBasic<>(GTCore.ID, "futuristic_circuit", "circuits/").tip("A Futuristic Circuit");
    public static ItemBasic<?> Circuit3D = new ItemBasic<>(GTCore.ID, "3d_circuit", "circuits/").tip("A 3-Dimensional Circuit");
    public static ItemBasic<?> CircuitInfinite = new ItemBasic<>(GTCore.ID, "infinite_circuit", "circuits/").tip("A Circuit with Infinite Power Throughput");
    public static ItemBasic<?> DataOrb = new ItemBasic<>(GTCore.ID, "data_orb", "circuits/");

    public static ItemBasic<?> MotorLV = new ItemBasic<>(GTCore.ID, "motor_lv");
    public static ItemBasic<?> MotorMV = new ItemBasic<>(GTCore.ID, "motor_mv");
    public static ItemBasic<?> MotorHV = new ItemBasic<>(GTCore.ID, "motor_hv");
    public static ItemBasic<?> MotorEV = new ItemBasic<>(GTCore.ID, "motor_ev");
    public static ItemBasic<?> MotorIV = new ItemBasic<>(GTCore.ID, "motor_iv");

    public static ItemPowerUnit PowerUnitLV = new ItemPowerUnit(GTCore.ID, "power_unit_lv", Aluminium);
    public static ItemPowerUnit PowerUnitMV = new ItemPowerUnit(GTCore.ID, "power_unit_mv", StainlessSteel);
    public static ItemPowerUnit PowerUnitHV = new ItemPowerUnit(GTCore.ID, "power_unit_hv", Titanium);
    public static ItemPowerUnit SmallPowerUnit = new ItemPowerUnit(GTCore.ID, "small_power_unit", Aluminium);
    public static ItemPowerUnit JackhammerPowerUnit = new ItemPowerUnit(GTCore.ID, "jackhammer_power_unit", Material.NULL);

    public static ItemBasic<?> RawLapotronCrustal = new ItemBasic<>(GTCore.ID, "raw_lapotron_crystal");
    public static ItemBasic<?> BatteryHullSmall = new ItemBasic<>(GTCore.ID, "small_battery_hull").tip("An empty LV Battery Hull");
    public static ItemBasic<?> BatteryHullMedium = new ItemBasic<>(GTCore.ID, "medium_battery_hull").tip("An empty MV Battery Hull");
    public static ItemBasic<?> BatteryHullLarge = new ItemBasic<>(GTCore.ID, "large_battery_hull").tip("An empty HV Battery Hull");
    public static ItemBasic<?> BatteryRE = new ItemMultiTextureBattery(GTCore.ID, "re_battery", Tier.LV, 10000, true).tip("Reusable Battery");
    public static ItemBasic<?> BatterySmallAcid = new ItemMultiTextureBattery(GTCore.ID, "small_acid_battery", Tier.LV, 18000, false).tip("Single Use");
    public static ItemBasic<?> BatterySmallMercury = new ItemMultiTextureBattery(GTCore.ID, "small_mercury_battery", Tier.LV, 32000, false).tip("Single Use");
    public static ItemBasic<?> BatterySmallCadmium = new ItemMultiTextureBattery(GTCore.ID, "small_cadmium_battery", Tier.LV,75000, 2, true).tip("Reusable");
    public static ItemBasic<?> BatterySmallLithium = new ItemMultiTextureBattery(GTCore.ID, "small_lithium_battery", Tier.LV, 100000, true).tip("Reusable");
    public static ItemBasic<?> BatterySmallSodium = new ItemMultiTextureBattery(GTCore.ID, "small_sodium_battery", Tier.LV, 50000, true).tip("Reusable");
    public static ItemBasic<?> BatteryMediumAcid = new ItemMultiTextureBattery(GTCore.ID, "medium_acid_battery", Tier.MV, 72000, false).tip("Single Use");
    public static ItemBasic<?> BatteryMediumMercury = new ItemMultiTextureBattery(GTCore.ID, "medium_mercury_battery", Tier.MV, 128000, false).tip("Single Use");
    public static ItemBasic<?> BatteryMediumCadmium = new ItemMultiTextureBattery(GTCore.ID, "medium_cadmium_battery", Tier.MV, 300000, 2, true).tip("Reusable");
    public static ItemBasic<?> BatteryMediumLithium = new ItemMultiTextureBattery(GTCore.ID, "medium_lithium_battery", Tier.MV, 400000, true).tip("Reusable");
    public static ItemBasic<?> BatteryMediumSodium = new ItemMultiTextureBattery(GTCore.ID, "medium_sodium_battery", Tier.MV,200000, true).tip("Reusable");
    public static ItemBasic<?> BatteryLargeAcid = new ItemMultiTextureBattery(GTCore.ID, "large_acid_battery", Tier.HV, 288000, false).tip("Single Use");
    public static ItemBasic<?> BatteryLargeMercury = new ItemMultiTextureBattery(GTCore.ID, "large_mercury_battery", Tier.HV, 512000, false).tip("Single Use");
    public static ItemBasic<?> BatteryLargeCadmium = new ItemMultiTextureBattery(GTCore.ID, "large_cadmium_battery", Tier.HV, 1200000, 2, true).tip("Reusable");
    public static ItemBasic<?> BatteryLargeLithium = new ItemMultiTextureBattery(GTCore.ID, "large_lithium_battery", Tier.HV, 1600000, true).tip("Reusable");
    public static ItemBasic<?> BatteryLargeSodium = new ItemMultiTextureBattery(GTCore.ID, "large_sodium_battery", Tier.HV, 800000, true).tip("Reusable");
    public static ItemBasic<?> EnergyCrystal = new ItemMultiTextureBattery(GTCore.ID, "energy_crystal", Tier.HV, 1_000_000, true);
    public static ItemBasic<?> LapotronCrystal = new ItemMultiTextureBattery(GTCore.ID, "lapotron_crystal", Tier.EV, 10_000_000, true);
    public static ItemBasic<?> BatteryEnergyOrb = new ItemMultiTextureBattery(GTCore.ID, "lapotronic_energy_orb", Tier.IV, 100_000_000, true);
    public static ItemBasic<?> BatteryEnergyOrbCluster = new ItemMultiTextureBattery(GTCore.ID, "lapotronic_energy_orb_cluster", Tier.LUV, 1_000_000_000, true);

    public static ItemBasic<?> EmptyShape = new ItemBasic<>(GTCore.ID, "empty_shape_plate", "molds/").tip("Raw plate to make Molds and Extruder Shapes");
    public static ItemBasic<?> MoldPlate = new ItemBasic<>(GTCore.ID, "plate_mold", "molds/").tip("Mold for making Plates");
    public static ItemBasic<?> MoldCasing = new ItemBasic<>(GTCore.ID, "casing_mold", "molds/").tip("Mold for making Item Casings");
    public static ItemBasic<?> MoldGear = new ItemBasic<>(GTCore.ID, "gear_mold", "molds/").tip("Mold for making Gears");
    public static ItemBasic<?> MoldGearSmall = new ItemBasic<>(GTCore.ID, "small_gear_mold", "molds/").tip("Mold for making Small Gears");
    public static ItemBasic<?> MoldCoinage = new ItemBasic<>(GTCore.ID, "coinage_mold", "molds/").tip("Secure Mold for making Coins (Don't lose it!)");
    public static ItemBasic<?> MoldBottle = new ItemBasic<>(GTCore.ID, "bottle_mold", "molds/").tip("Mold for making Bottles");
    public static ItemBasic<?> MoldIngot = new ItemBasic<>(GTCore.ID, "ingot_mold", "molds/").tip("Mold for making Ingots");
    public static ItemBasic<?> MoldLongRod = new ItemBasic<>(GTCore.ID, "long_rod_mold", "molds/").tip("Mold for making long rods");
    public static ItemBasic<?> MoldBall = new ItemBasic<>(GTCore.ID, "ball_mold", "molds/").tip("Mold for making Balls");
    public static ItemBasic<?> MoldBlock = new ItemBasic<>(GTCore.ID, "block_mold", "molds/").tip("Mold for making Blocks");
    public static ItemBasic<?> MoldNugget = new ItemBasic<>(GTCore.ID, "nugget_mold", "molds/").tip("Mold for making Nuggets");
    public static ItemBasic<?> MoldAnvil = new ItemBasic<>(GTCore.ID, "anvil_mold", "molds/").tip("Mold for making Anvils");
    public static ItemBasic<?> ShapePlate = new ItemBasic<>(GTCore.ID, "plate_shape", "molds/").tip("Shape for making Plates");
    public static ItemBasic<?> ShapeCasing = new ItemBasic<>(GTCore.ID, "casing_shape", "molds/").tip("Shape for making Item Casings");
    public static ItemBasic<?> ShapeRod = new ItemBasic<>(GTCore.ID, "rod_shape", "molds/").tip("Shape for making Rods");
    public static ItemBasic<?> ShapeLongRod = new ItemBasic<>(GTCore.ID, "long_rod_shape", "molds/").tip("Shape for making Long Rods");
    public static ItemBasic<?> ShapeBolt = new ItemBasic<>(GTCore.ID, "bolt_shape", "molds/").tip("Shape for making Bolts");
    public static ItemBasic<?> ShapeRing = new ItemBasic<>(GTCore.ID, "ring_shape", "molds/").tip("Shape for making Rings");
    public static ItemBasic<?> ShapeFoil = new ItemBasic<>(GTCore.ID, "foil_shape", "molds/").tip("Shape for making Foils");
    public static ItemBasic<?> ShapeCell = new ItemBasic<>(GTCore.ID, "cell_shape", "molds/").tip("Shape for making Cells");
    public static ItemBasic<?> ShapeBottle = new ItemBasic<>(GTCore.ID, "bottle_shape", "molds/").tip("Shape for making Bottles");
    public static ItemBasic<?> ShapeIngot = new ItemBasic<>(GTCore.ID, "ingot_shape", "molds/").tip("Shape for making Ingots");
    public static ItemBasic<?> ShapeWire = new ItemBasic<>(GTCore.ID, "wire_shape", "molds/").tip("Shape for making Wires");
    public static ItemBasic<?> ShapeFineWire = new ItemBasic<>(GTCore.ID, "fine_wire_shape", "molds/").tip("Shape for making Fine Wires");
    public static ItemBasic<?> ShapeTinyPlate = new ItemBasic<>(GTCore.ID, "tiny_plate_shape", "molds/").tip("Shape for making Tiny Plates");
    public static ItemBasic<?> ShapePipeTiny = new ItemBasic<>(GTCore.ID, "tiny_pipe_shape", "molds/").tip("Shape for making Tiny Pipes");
    public static ItemBasic<?> ShapePipeSmall = new ItemBasic<>(GTCore.ID, "small_pipe_shape", "molds/").tip("Shape for making Small Pipes");
    public static ItemBasic<?> ShapePipeNormal = new ItemBasic<>(GTCore.ID, "normal_pipe_shape", "molds/").tip("Shape for making Normal Pipes");
    public static ItemBasic<?> ShapePipeLarge = new ItemBasic<>(GTCore.ID, "large_pipe_shape", "molds/").tip("Shape for making Large Pipes");
    public static ItemBasic<?> ShapePipeHuge = new ItemBasic<>(GTCore.ID, "huge_pipe_shape", "molds/").tip("Shape for making Huge Pipes");
    public static ItemBasic<?> ShapeBlock = new ItemBasic<>(GTCore.ID, "block_shape", "molds/").tip("Shape for making Blocks");
    public static ItemBasic<?> ShapeBladeSword = new ItemBasic<>(GTCore.ID, "sword_blade_shape", "molds/").tip("Shape for making Sword Blades");
    public static ItemBasic<?> ShapeHeadPickaxe = new ItemBasic<>(GTCore.ID, "pickaxe_head_shape", "molds/").tip("Shape for making Pickaxe Heads");
    public static ItemBasic<?> ShapeHeadShovel = new ItemBasic<>(GTCore.ID, "shovel_head_shape", "molds/").tip("Shape for making Shovel Heads");
    public static ItemBasic<?> ShapeHeadAxe = new ItemBasic<>(GTCore.ID, "axe_head_shape", "molds/").tip("Shape for making Axe Heads");
    public static ItemBasic<?> ShapeHeadHoe = new ItemBasic<>(GTCore.ID, "hoe_head_shape", "molds/").tip("Shape for making Hoe Heads");
    public static ItemBasic<?> ShapeHeadHammer = new ItemBasic<>(GTCore.ID, "hammer_head_shape", "molds/").tip("Shape for making Hammer Heads");
    public static ItemBasic<?> ShapeHeadFile = new ItemBasic<>(GTCore.ID, "file_head_shape", "molds/").tip("Shape for making File Heads");
    public static ItemBasic<?> ShapeBladeSaw = new ItemBasic<>(GTCore.ID, "saw_blade_shape", "molds/").tip("Shape for making Saw Heads");
    public static ItemBasic<?> ShapeGear = new ItemBasic<>(GTCore.ID, "gear_shape", "molds/").tip("Shape for making Gears");
    public static ItemBasic<?> ShapeGearSmall = new ItemBasic<>(GTCore.ID, "small_gear_shape", "molds/").tip("Shape for making Small Gears");

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
                coverBuilder.put(i, CoverFactory.builder(CoverSelectorTag::new)
                        .addTextures(new Texture(GTCore.ID, "block/cover/selector_tags/" + i), new Texture(GTCore.ID, "block/cover/selector_tags/underlay"))
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
