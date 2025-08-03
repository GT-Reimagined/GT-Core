package org.gtreimagined.gtcore.tree.block;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.tree.item.ItemRubberSign;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IItemBlockProvider;

public class BlockRubberSign extends TerraformSignBlock implements IGTObject, IItemBlockProvider {

    public BlockRubberSign() {
        super(Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), GTCoreBlocks.RUBBER_WOOD_TYPE);
        GTAPI.register(BlockRubberSign.class, this);
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
