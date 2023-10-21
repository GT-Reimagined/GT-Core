package io.github.gregtechintergalactical.gtcore.data;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.block.BlockSapBag;
import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntitySapBag;
import io.github.gregtechintergalactical.gtcore.machine.DrumMachine;
import io.github.gregtechintergalactical.gtcore.machine.LockerMachine;
import io.github.gregtechintergalactical.gtcore.machine.WorkbenchMachine;
import io.github.gregtechintergalactical.gtcore.tree.block.*;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.block.BlockBasic;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.ore.CobbleStoneType;
import muramasa.antimatter.ore.StoneType;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

import static io.github.gregtechintergalactical.gtcore.data.GTCoreMaterials.*;

public class GTCoreBlocks {

    public static WoodType RUBBER_WOOD_TYPE = new WoodType("rubber"){};



    public static Block RUBBER_LEAVES;
    public static final BlockRubberLog RUBBER_LOG = new BlockRubberLog(GTCore.ID, "rubber_log");
    public static final BlockRubberLog STRIPPED_RUBBER_LOG = new BlockRubberLog(GTCore.ID, "stripped_rubber_log");
    public static final BlockRubberWood RUBBER_WOOD = new BlockRubberWood(GTCore.ID, "rubber_wood");
    public static final BlockRubberWood STRIPPED_RUBBER_WOOD = new BlockRubberWood(GTCore.ID, "stripped_rubber_wood");
    public static final BlockBasic RUBBER_PLANKS = new BlockBasic(GTCore.ID, "rubber_planks", BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.WOOD).strength(2.0F).sound(SoundType.WOOD)){
        @Override
        public Texture[] getTextures() {
            return new Texture[]{new Texture(domain, "block/tree/rubber_planks")};
        }
    };

    public static final BlockRubberSign RUBBER_SIGN = new BlockRubberSign();
    public static final BlockRubberWallSign RUBBER_WALL_SIGN = new BlockRubberWallSign();
    public static Block RUBBER_SAPLING;
    public static final BlockRubberButton RUBBER_BUTTON = new BlockRubberButton();
    public static final BlockRubberPressurePlate RUBBER_PRESSURE_PLATE = new BlockRubberPressurePlate();
    public static final BlockRubberDoor RUBBER_DOOR = new BlockRubberDoor();
    public static final BlockRubberTrapDoor RUBBER_TRAPDOOR = new BlockRubberTrapDoor();

    public static final BlockRubberSlab RUBBER_SLAB = new BlockRubberSlab();
    public static final BlockRubberStairs RUBBER_STAIRS = new BlockRubberStairs();

    public static final BlockRubberFence RUBBER_FENCE = new BlockRubberFence();
    public static final BlockRubberFenceGate RUBBER_FENCE_GATE = new BlockRubberFenceGate();

    public static final BlockSapBag SAP_BAG = new BlockSapBag();


    public static final BlockEntityType<?> SAP_BAG_BLOCK_ENTITY = BlockEntityType.Builder.of(BlockEntitySapBag::new, SAP_BAG).build(null);

    public static StoneType GRANITE_RED = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "red_granite", RedGranite, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(4.5F, 60.0F).setHarvestLevel(3);
    public static StoneType GRANITE_BLACK = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "black_granite", BlackGranite, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(4.5F, 60.0F).setHarvestLevel(3);
    public static StoneType MARBLE = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "marble", Marble, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75F,7.5F);

    public static StoneType KOMATIITE = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "komatiite", Komatiite, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(3.0F, 30.0F).setHarvestLevel(2);
    public static StoneType LIMESTONE = AntimatterAPI.register(StoneType.class,  new CobbleStoneType(GTCore.ID, "limestone", Limestone, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75F,7.5F);
    public static StoneType GREEN_SCHIST = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "green_schist", GreenSchist, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75F,7.5F);
    public static StoneType BLUE_SCHIST = AntimatterAPI.register(StoneType.class,  new CobbleStoneType(GTCore.ID, "blue_schist", BlueSchist, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75F,7.5F);
    public static StoneType KIMBERLITE = AntimatterAPI.register(StoneType.class,  new CobbleStoneType(GTCore.ID, "kimberlite", Kimberlite, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(3.0F, 30.0F).setHarvestLevel(2);
    public static StoneType QUARTZITE = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "quartzite", Quartzite, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75F,7.5F);
    

    public static void init() {
        if (!AntimatterAPI.isModLoaded("tfc")){
            RUBBER_LEAVES = new BlockRubberLeaves();
            RUBBER_SAPLING = new BlockRubberSapling();
        } else if (AntimatterPlatformUtils.isForge()){
            initTFC();
        }
        AntimatterAPI.register(BlockEntityType.class, "sap_bag", GTCore.ID, SAP_BAG_BLOCK_ENTITY);
    }

    @ExpectPlatform
    private static void initTFC(){
        throw new AssertionError();
    }

    public static DrumMachine createDrum(Material material, int maxCapacity){
        DrumMachine machine = AntimatterAPI.get(DrumMachine.class, material.getId() + "_drum", GTCore.ID);
        if (machine != null){
            return machine;
        }
        return new DrumMachine(GTCore.ID, material, maxCapacity);
    }

    public static WorkbenchMachine createWorkbench(Material material, boolean charge){
        WorkbenchMachine machine = AntimatterAPI.get(WorkbenchMachine.class, material.getId() + (charge ? "_charging" : "") + "_workbench", GTCore.ID);
        if (machine != null){
            return machine;
        }
        return new WorkbenchMachine(GTCore.ID, material, charge);
    }

    public static LockerMachine createLocker(Material material, boolean charge){
        LockerMachine machine = AntimatterAPI.get(LockerMachine.class, material.getId() + (charge ? "_charging" : "") + "_locker", GTCore.ID);
        if (machine != null){
            return machine;
        }
        return new LockerMachine(GTCore.ID, material, charge);
    }
}
