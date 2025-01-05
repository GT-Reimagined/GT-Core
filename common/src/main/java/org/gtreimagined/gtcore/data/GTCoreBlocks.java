package org.gtreimagined.gtcore.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.BlockCasing;
import org.gtreimagined.gtcore.block.BlockSapBag;
import org.gtreimagined.gtcore.blockentity.BlockEntityPlasticBin;
import org.gtreimagined.gtcore.blockentity.BlockEntitySapBag;
import org.gtreimagined.gtcore.blockentity.BlockEntityTrashCan;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.block.BlockBasic;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.machine.MachineFlag;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.types.BasicMachine;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.ore.CobbleStoneType;
import muramasa.antimatter.ore.StoneType;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.gtreimagined.gtcore.block.BlockSapBag;
import org.gtreimagined.gtcore.blockentity.BlockEntityPlasticBin;
import org.gtreimagined.gtcore.blockentity.BlockEntitySapBag;
import org.gtreimagined.gtcore.blockentity.BlockEntityTrashCan;
import org.gtreimagined.gtcore.machine.BarrelMachine;
import org.gtreimagined.gtcore.machine.ChestMachine;
import org.gtreimagined.gtcore.machine.DrumMachine;
import org.gtreimagined.gtcore.machine.HopperMachine;
import org.gtreimagined.gtcore.machine.LockerMachine;
import org.gtreimagined.gtcore.machine.MassStorageMachine;
import org.gtreimagined.gtcore.machine.MaterialMachine;
import org.gtreimagined.gtcore.machine.WorkbenchMachine;
import org.gtreimagined.gtcore.tree.block.BlockRubberButton;
import org.gtreimagined.gtcore.tree.block.BlockRubberDoor;
import org.gtreimagined.gtcore.tree.block.BlockRubberFence;
import org.gtreimagined.gtcore.tree.block.BlockRubberFenceGate;
import org.gtreimagined.gtcore.tree.block.BlockRubberLeaves;
import org.gtreimagined.gtcore.tree.block.BlockRubberLog;
import org.gtreimagined.gtcore.tree.block.BlockRubberPressurePlate;
import org.gtreimagined.gtcore.tree.block.BlockRubberSapling;
import org.gtreimagined.gtcore.tree.block.BlockRubberSign;
import org.gtreimagined.gtcore.tree.block.BlockRubberSlab;
import org.gtreimagined.gtcore.tree.block.BlockRubberStairs;
import org.gtreimagined.gtcore.tree.block.BlockRubberTrapDoor;
import org.gtreimagined.gtcore.tree.block.BlockRubberWallSign;
import org.gtreimagined.gtcore.tree.block.BlockRubberWood;
import org.jetbrains.annotations.Nullable;

import static org.gtreimagined.gtcore.data.GTCoreMaterials.*;

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

    public static MaterialMachine WOOD_ITEM_BARREL;
    public static MaterialMachine PLASTIC_STORAGE_BOX;

    @Nullable
    public static MaterialMachine IRONWOOD_ITEM_BARREL = null;

    public static BasicMachine ENDER_GARBAGE_BIN = new BasicMachine(GTCore.ID, "ender_garbage_bin").baseTexture(new Texture(GTCore.ID, "block/machine/base/ender_garbage_bin")).setTiers(Tier.NONE).custom().itemModelParent(new ResourceLocation(GTCore.ID, "block/ender_garbage_bin_base")).addFlags(MachineFlag.ITEM, MachineFlag.FLUID, MachineFlag.UNCULLED, MachineFlag.GUI).removeFlags(MachineFlag.COVERABLE, MachineFlag.EU).allowFrontIO().noCovers().setTile(BlockEntityTrashCan::new);

    public static HopperMachine LEAD_HOPPER = new HopperMachine(GTCore.ID, "lead_hopper", Lead);

    public static StoneType RED_GRANITE = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "red_granite", RedGranite, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(4.5F, 60.0F).setHarvestLevel(3);
    public static StoneType BLACK_GRANITE = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "black_granite", BlackGranite, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(4.5F, 60.0F).setHarvestLevel(3);
    public static StoneType MARBLE = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "marble", Marble, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75F,7.5F);

    public static StoneType KOMATIITE = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "komatiite", Komatiite, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(3.0F, 30.0F).setHarvestLevel(2);
    public static StoneType LIMESTONE = AntimatterAPI.register(StoneType.class,  new CobbleStoneType(GTCore.ID, "limestone", Limestone, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75F,7.5F);
    public static StoneType GREEN_SCHIST = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "green_schist", GreenSchist, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75F,7.5F);
    public static StoneType BLUE_SCHIST = AntimatterAPI.register(StoneType.class,  new CobbleStoneType(GTCore.ID, "blue_schist", BlueSchist, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75F,7.5F);
    public static StoneType KIMBERLITE = AntimatterAPI.register(StoneType.class,  new CobbleStoneType(GTCore.ID, "kimberlite", Kimberlite, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(3.0F, 30.0F).setHarvestLevel(2);
    public static StoneType QUARTZITE = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "quartzite", Quartzite, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75F,7.5F);
    public static StoneType SHALE = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "shale", Shale, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75f, 7.5f);
    public static StoneType SLATE = AntimatterAPI.register(StoneType.class, new CobbleStoneType(GTCore.ID, "slate", Slate, "block/stone/", SoundType.STONE, true)).setHardnessAndResistance(0.75f, 7.5f);

    public static final BlockCasing REINFORCED_GLASS = new BlockCasing(GTCore.ID, "reinforced_glass", Block.Properties.of(net.minecraft.world.level.material.Material.GLASS).strength(15.0f, 150.0f).sound(SoundType.GLASS).requiresCorrectToolForDrops().noOcclusion().isValidSpawn(((blockState, blockGetter, blockPos, object) -> false)).isRedstoneConductor(GTCoreBlocks::isntSolid).isSuffocating(GTCoreBlocks::isntSolid).isViewBlocking(GTCoreBlocks::isntSolid));
    public static final BlockCasing REINFORCED_STONE = new BlockCasing(GTCore.ID, "reinforced_stone", Block.Properties.of(net.minecraft.world.level.material.Material.STONE).strength(80.0f, 150.0f).sound(SoundType.STONE).requiresCorrectToolForDrops());

    public static Boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
        return false;
    }

    public static void init() {
        if (!AntimatterAPI.isModLoaded("tfc")){
            RUBBER_LEAVES = new BlockRubberLeaves();
            RUBBER_SAPLING = new BlockRubberSapling();
        } else if (AntimatterPlatformUtils.INSTANCE.isForge()){
            initTFC();
        }
        AntimatterAPI.register(BlockEntityType.class, "sap_bag", GTCore.ID, SAP_BAG_BLOCK_ENTITY);
    }

    public static void initItemBarrels(){
        WOOD_ITEM_BARREL = new MassStorageMachine(GTCore.ID, AntimatterMaterials.Wood, "item_storage", 5000).addFlags(MachineFlag.GUI);
        PLASTIC_STORAGE_BOX = new MassStorageMachine(GTCore.ID, Plastic, "storage_box", 128).setTile((type, pos, state) -> new BlockEntityPlasticBin((MassStorageMachine) type, pos, state)).addTooltipInfo((blockMachine, itemStack, blockGetter, list, tooltipFlag) -> {
            list.remove(1);
            list.add(1, Utils.translatable("machine.mass_storage.capacity", "Variable"));
        });
        if (AntimatterAPI.isModLoaded("twilightforest")){
            IRONWOOD_ITEM_BARREL = new MassStorageMachine(GTCore.ID, Ironwood, "item_storage", 10000).addFlags(MachineFlag.GUI);
        }
    }

    private static void initTFC(){
        IGTBlockInit.INSTANCE.init();
    }

    public static DrumMachine createDrum(Material material, int maxCapacity){
        DrumMachine machine = AntimatterAPI.get(DrumMachine.class, material.getId() + "_drum", GTCore.ID);
        if (machine != null){
            return machine;
        }
        return new DrumMachine(GTCore.ID, material, maxCapacity);
    }

    public static MassStorageMachine createMassStorage(Material material, int capacity){
        MassStorageMachine machine = AntimatterAPI.get(MassStorageMachine.class, material.getId() + "_mass_storage", GTCore.ID);
        if (machine != null){
            return machine;
        }
        return new MassStorageMachine(GTCore.ID, material, "mass_storage", capacity);
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

    public static ChestMachine createChest(Material material){
        return createChest(material, true);
    }

    public static ChestMachine createChest(Material material, boolean addSlots){
        ChestMachine machine = AntimatterAPI.get(ChestMachine.class, material.getId() + "_chest", GTCore.ID);
        if (machine != null){
            return machine;
        }
        return new ChestMachine(GTCore.ID, material, addSlots);
    }

    public static BarrelMachine createBarrel(Material material){
        return createBarrel(material, true);
    }

    public static BarrelMachine createBarrel(Material material, boolean addSlots){
        BarrelMachine machine = AntimatterAPI.get(BarrelMachine.class, material.getId() + "_barrel", GTCore.ID);
        if (machine != null){
            return machine;
        }
        return new BarrelMachine(GTCore.ID, material, addSlots);
    }
}
