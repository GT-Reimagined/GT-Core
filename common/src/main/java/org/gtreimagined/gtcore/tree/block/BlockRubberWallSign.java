package org.gtreimagined.gtcore.tree.block;

import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IItemBlockProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;

public class BlockRubberWallSign extends TerraformWallSignBlock implements IAntimatterObject, IItemBlockProvider {
    public BlockRubberWallSign() {
        super(new ResourceLocation(GTCore.ID, "entity/signs/rubber"), Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD));
        AntimatterAPI.register(BlockRubberWallSign.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_wall_sign";
    }

    @Override
    public boolean generateItemBlock() {
        return false;
    }

    @Override
    public WoodType type() {
        return GTCoreBlocks.RUBBER_WOOD_TYPE;
    }
}
