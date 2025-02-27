package org.gtreimagined.gtcore.datagen;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockLootProvider;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.BlockGTHopper;
import org.gtreimagined.gtcore.block.BlockMaterialChest;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.machine.BlockMachineMaterial;
import org.gtreimagined.gtcore.machine.BlockMultiMachineMaterial;


public class GTCoreBlockLootProvider extends AntimatterBlockLootProvider {
    public GTCoreBlockLootProvider(String providerDomain, String providerName) {
        super(providerDomain, providerName);
    }

    @Override
    protected void loot() {
        super.loot();
        AntimatterAPI.all(BlockMachineMaterial.class, this::add);
        AntimatterAPI.all(BlockMultiMachineMaterial.class, this::add);
        AntimatterAPI.all(BlockMaterialChest.class, this::add);
        AntimatterAPI.all(BlockGTHopper.class, this::add);
        tables.put(GTCoreBlocks.RUBBER_LEAVES, b -> createLeavesDrops(GTCoreBlocks.RUBBER_LEAVES, GTCoreBlocks.RUBBER_SAPLING, 0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F));
        this.add(GTCoreBlocks.RUBBER_LOG);
        this.add(GTCoreBlocks.RUBBER_SAPLING);
        this.add(GTCoreBlocks.STRIPPED_RUBBER_LOG);
        this.add(GTCoreBlocks.RUBBER_WOOD);
        this.add(GTCoreBlocks.STRIPPED_RUBBER_WOOD);
        this.add(GTCoreBlocks.RUBBER_PLANKS);
        this.add(GTCoreBlocks.RUBBER_FENCE);
        this.add(GTCoreBlocks.RUBBER_FENCE_GATE);
        this.add(GTCoreBlocks.RUBBER_PRESSURE_PLATE);
        this.add(GTCoreBlocks.RUBBER_BUTTON);
        this.add(GTCoreBlocks.RUBBER_STAIRS);
        tables.put(GTCoreBlocks.RUBBER_SLAB, BlockLoot::createSlabItemTable);
        tables.put(GTCoreBlocks.RUBBER_DOOR, BlockLoot::createDoorTable);
        this.add(GTCoreBlocks.RUBBER_TRAPDOOR);
        tables.put(GTCoreBlocks.RUBBER_SIGN, b -> BlockLoot.createSingleItemTable(GTCoreBlocks.RUBBER_SIGN.asItem()));
        tables.put(GTCoreBlocks.RUBBER_WALL_SIGN, b -> BlockLoot.createSingleItemTable(GTCoreBlocks.RUBBER_SIGN.asItem()));
        this.add(GTCoreBlocks.SAP_BAG);
        if (AntimatterAPI.isModLoaded("tfc")) {
            this.add(AntimatterAPI.get(Block.class, "rubber_twig", GTCore.ID));
            this.add(AntimatterAPI.get(Block.class, "rubber_fallen_leaves", GTCore.ID));
        }
    }
}
