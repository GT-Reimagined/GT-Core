package org.gtreimagined.gtcore.tree.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.registration.ITextureProvider;
import org.gtreimagined.gtlib.texture.Texture;

public class BlockRubberWood extends RotatedPillarBlock implements IModelProvider, IGTObject, ITextureProvider {
    protected final String domain, id;
    public BlockRubberWood(String domain, String id, Properties properties) {
        super(properties);
        this.domain = domain;
        this.id = id;
        GTAPI.register(getClass(), this);
    }

    public BlockRubberWood(String domain, String id){
        this(domain, id, Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD));
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(domain,"block/tree/" + getId().replace("wood", "log"))};
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (this == GTCoreBlocks.RUBBER_WOOD){
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof DiggerItem diggerItem && diggerItem.getDestroySpeed(stack, state) > 1.0f){
                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide){
                    BlockState target = GTCoreBlocks.STRIPPED_RUBBER_WOOD.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
                    level.setBlockAndUpdate(pos, target);
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
