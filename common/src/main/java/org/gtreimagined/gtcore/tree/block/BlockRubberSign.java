package org.gtreimagined.gtcore.tree.block;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.tree.item.ItemRubberSign;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IItemBlockProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;

public class BlockRubberSign extends TerraformSignBlock implements IAntimatterObject, IItemBlockProvider {

    public BlockRubberSign() {
        super(new ResourceLocation(GTCore.ID, "entity/signs/rubber"), Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD));
        AntimatterAPI.register(BlockRubberSign.class, this);
    }

    @Override
    public String getId() {
        return "rubber_sign";
    }
    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public BlockItem getItemBlock() {
        return new ItemRubberSign();
    }

    @Override
    public WoodType type() {
        return GTCoreBlocks.RUBBER_WOOD_TYPE;
    }
}
