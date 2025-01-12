package org.gtreimagined.gtcore.datagen;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.AntimatterStoneTypes;
import muramasa.antimatter.datagen.providers.AntimatterBlockTagProvider;
import muramasa.antimatter.util.TagUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.BlockMaterialChest;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.machine.BlockMachineMaterial;
import org.gtreimagined.gtcore.machine.BlockMultiMachineMaterial;

import static muramasa.antimatter.material.MaterialTags.WOOD;
import static org.gtreimagined.gtcore.data.GTCoreBlocks.*;


public class GTCoreBlockTagProvider extends AntimatterBlockTagProvider {

    public GTCoreBlockTagProvider(String providerDomain, String providerName, boolean replace) {
        super(providerDomain, providerName, replace);
    }

    @Override
    public void processTags(String domain) {
        super.processTags(domain);
        this.tag(TagUtils.getForgelikeBlockTag("wg_stone")).add(BLACK_GRANITE.getState().getBlock(), RED_GRANITE.getState().getBlock(), MARBLE.getState().getBlock(), AntimatterStoneTypes.BASALT.getState().getBlock(), KOMATIITE.getState().getBlock(), LIMESTONE.getState().getBlock(), GREEN_SCHIST.getState().getBlock(), BLUE_SCHIST.getState().getBlock(), KIMBERLITE.getState().getBlock(), QUARTZITE.getState().getBlock(), SLATE.getState().getBlock(), SHALE.getState().getBlock());
        this.tag(TagUtils.getBlockTag(new ResourceLocation("minecraft","base_stone_overworld"))).add(BLACK_GRANITE.getState().getBlock(), RED_GRANITE.getState().getBlock(), MARBLE.getState().getBlock(), AntimatterStoneTypes.BASALT.getState().getBlock(), KOMATIITE.getState().getBlock(), LIMESTONE.getState().getBlock(), GREEN_SCHIST.getState().getBlock(), BLUE_SCHIST.getState().getBlock(), KIMBERLITE.getState().getBlock(), QUARTZITE.getState().getBlock(), SLATE.getState().getBlock(), SHALE.getState().getBlock());
        AntimatterAPI.all(BlockMachineMaterial.class, cas -> {
            if (cas.getMaterial().has(WOOD)){
                this.tag(AntimatterDefaultTools.AXE.getToolType()).add(cas);
            } else {
                this.tag(AntimatterDefaultTools.WRENCH.getToolType()).add(cas);
            }
        });
        AntimatterAPI.all(BlockMaterialChest.class, cas -> {
            if (cas.getMaterial().has(WOOD)){
                this.tag(AntimatterDefaultTools.AXE.getToolType()).add(cas);
            } else {
                this.tag(AntimatterDefaultTools.WRENCH.getToolType()).add(cas);
            }
        });
        AntimatterAPI.all(BlockMultiMachineMaterial.class, cas -> {
            if (cas.getMaterial().has(WOOD)){
                this.tag(AntimatterDefaultTools.AXE.getToolType()).add(cas);
            } else {
                this.tag(AntimatterDefaultTools.WRENCH.getToolType()).add(cas);
            }
        });
        /*AntimatterAPI.all(BlockNonSolidMachine.class, Ref.ID, cas -> {
            this.tag(AntimatterDefaultTools.WRENCH.getToolType()).add(cas);
        });
        AntimatterAPI.all(BlockRedstoneMachine.class, Ref.ID, cas -> {
            this.tag(AntimatterDefaultTools.WRENCH.getToolType()).add(cas);
        });*/
        this.tag(BlockTags.LEAVES).add(GTCoreBlocks.RUBBER_LEAVES);
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(GTCoreBlocks.RUBBER_LEAVES);
        this.tag(BlockTags.SAPLINGS).add(GTCoreBlocks.RUBBER_SAPLING);
        this.tag(BlockTags.PLANKS).add(GTCoreBlocks.RUBBER_PLANKS);
        this.tag(BlockTags.WOODEN_SLABS).add(GTCoreBlocks.RUBBER_SLAB);
        this.tag(BlockTags.WOODEN_STAIRS).add(GTCoreBlocks.RUBBER_STAIRS);
        this.tag(BlockTags.WOODEN_FENCES).add(GTCoreBlocks.RUBBER_FENCE);
        this.tag(BlockTags.FENCE_GATES).add(GTCoreBlocks.RUBBER_FENCE_GATE);
        this.tag(BlockTags.WOODEN_DOORS).add(GTCoreBlocks.RUBBER_DOOR);
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(GTCoreBlocks.RUBBER_TRAPDOOR);
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(GTCoreBlocks.RUBBER_PRESSURE_PLATE);
        this.tag(BlockTags.WOODEN_BUTTONS).add(GTCoreBlocks.RUBBER_BUTTON);
        this.tag(BlockTags.STANDING_SIGNS).add(GTCoreBlocks.RUBBER_SIGN);
        this.tag(BlockTags.WALL_SIGNS).add(GTCoreBlocks.RUBBER_WALL_SIGN);
        this.tag(TagUtils.getBlockTag(new ResourceLocation(GTCore.ID, "rubber_logs"))).add(GTCoreBlocks.RUBBER_LOG, GTCoreBlocks.STRIPPED_RUBBER_LOG, GTCoreBlocks.RUBBER_WOOD, GTCoreBlocks.STRIPPED_RUBBER_WOOD);
        this.tag(BlockTags.LOGS_THAT_BURN).addTag(TagUtils.getBlockTag(new ResourceLocation(GTCore.ID, "rubber_logs")));
        if (AntimatterAPI.isModLoaded("tfc")){
            this.tag(BlockTags.WOODEN_FENCES).add(AntimatterAPI.get(Block.class, "rubber_log_fence", GTCore.ID));
            this.tag(TagUtils.getBlockTag(new ResourceLocation("tfc", "twigs"))).add(AntimatterAPI.get(Block.class, "rubber_twig", GTCore.ID));
            this.tag(TagUtils.getBlockTag(new ResourceLocation("tfc", "fallen_leaves"))).add(AntimatterAPI.get(Block.class, "rubber_fallen_leaves", GTCore.ID));
            this.tag(TagUtils.getBlockTag(new ResourceLocation("tfc", "mineable_with_sharp_tool"))).add(AntimatterAPI.get(Block.class, "rubber_fallen_leaves", GTCore.ID), GTCoreBlocks.RUBBER_LEAVES);
            this.tag(TagUtils.getBlockTag(new ResourceLocation("tfc", "mineable_with_blunt_tool"))).addTag(TagUtils.getBlockTag(new ResourceLocation(GTCore.ID, "rubber_logs")));
            this.tag(TagUtils.getBlockTag(new ResourceLocation("tfc", "lit_by_dropped_torch"))).add(AntimatterAPI.get(Block.class, "rubber_fallen_leaves", GTCore.ID));
            this.tag(TagUtils.getBlockTag(new ResourceLocation("tfc", "converts_to_humus"))).add(AntimatterAPI.get(Block.class, "rubber_fallen_leaves", GTCore.ID));
            this.tag(TagUtils.getBlockTag(new ResourceLocation("tfc", "can_be_snow_piled"))).add(AntimatterAPI.get(Block.class, "rubber_twig", GTCore.ID), AntimatterAPI.get(Block.class, "rubber_fallen_leaves", GTCore.ID));
            this.tag(TagUtils.getBlockTag(new ResourceLocation("tfc", "single_block_replaceable"))).add(AntimatterAPI.get(Block.class, "rubber_twig", GTCore.ID), AntimatterAPI.get(Block.class, "rubber_fallen_leaves", GTCore.ID));
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(AntimatterAPI.get(Block.class, "rubber_twig", GTCore.ID));
            this.tag(BlockTags.MINEABLE_WITH_HOE).add(AntimatterAPI.get(Block.class, "rubber_fallen_leaves", GTCore.ID));
            this.tag(TagUtils.getBlockTag(new ResourceLocation("replaceable_by_trees"))).add(AntimatterAPI.get(Block.class, "rubber_twig", GTCore.ID), AntimatterAPI.get(Block.class, "rubber_fallen_leaves", GTCore.ID));
        }
    }
}
