package org.gtreimagined.gtcore.blockentity;


import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.gtreimagined.gtcore.machine.MaterialMachine;
import org.gtreimagined.gtlib.gui.container.ContainerMachine;

import java.util.List;

//TODO needed?
public class BlockEntityChest extends BlockEntityMaterial<BlockEntityChest> implements LidBlockEntity {
    protected float lidAngle;
    protected float prevLidAngle;
    protected int numPlayersUsing;
    private int ticksSinceSync;

    private final ChestLidController lidController;
    private final ContainerOpenersCounter manager = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level world, BlockPos pos, BlockState state) {
            BlockEntityChest.playSound(world, pos, state, SoundEvents.CHEST_OPEN);
        }

        @Override
        protected void onClose(Level world, BlockPos pos, BlockState state) {
            BlockEntityChest.playSound(world, pos, state, SoundEvents.CHEST_CLOSE);
        }

        @Override
        protected void openerCountChanged(Level world, BlockPos pos, BlockState state, int oldCount, int newCount) {
            world.blockEvent(pos, state.getBlock(), 1, newCount);
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            return player.containerMenu instanceof ContainerMachine<?> handler &&
                    handler.handler.handler instanceof BlockEntityChest chest && chest.getBlockPos().equals(BlockEntityChest.this.getBlockPos());
        }
    };


    public BlockEntityChest(MaterialMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        lidController = new ChestLidController();
    }

    @Override
    public void clientTick(Level level, BlockPos pos, BlockState state) {
        super.clientTick(level, pos, state);
    }

    @Override
    protected void tick(Level level, BlockPos pos, BlockState state) {
        super.tick(level, pos, state);
        lidController.tickLid();
    }

    @Override
    public boolean triggerEvent(int event, int value) {
        if (event == 1) {
            lidController.shouldBeOpen(value > 0);
            return true;
        }
        return super.triggerEvent(event, value);
    }


    @Override
    public boolean canPlayerOpenGui(Player playerEntity) {
        return super.canPlayerOpenGui(playerEntity) && !isChestBlockedAt(playerEntity.level, this.getBlockPos());
    }

    public static boolean isChestBlockedAt(LevelAccessor p_220108_0_, BlockPos p_220108_1_) {
        return isBlockedChestByBlock(p_220108_0_, p_220108_1_) || isCatSittingOnChest(p_220108_0_, p_220108_1_);
    }

    private static boolean isBlockedChestByBlock(BlockGetter pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        return pLevel.getBlockState(blockpos).isRedstoneConductor(pLevel, blockpos);
    }

    private static boolean isCatSittingOnChest(LevelAccessor pLevel, BlockPos pPos) {
        List<Cat> list = pLevel.getEntitiesOfClass(Cat.class, new AABB((double)pPos.getX(), (double)(pPos.getY() + 1), (double)pPos.getZ(), (double)(pPos.getX() + 1), (double)(pPos.getY() + 2), (double)(pPos.getZ() + 1)));
        if (!list.isEmpty()) {
            for(Cat catentity : list) {
                if (catentity.isInSittingPose()) {
                    return true;
                }
            }
        }

        return false;
    }

    private static void playSound(Level world, BlockPos pos, BlockState state, SoundEvent soundIn) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.5D;
        double d2 = (double) pos.getZ() + 0.5D;

        world.playSound((Player) null, d0, d1, d2, soundIn, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public float getOpenNess(float partialTicks) {
        return lidController.getOpenness(partialTicks);
        //return Mth.lerp(partialTicks, this.prevLidAngle, this.lidAngle);
    }

    @Override
    public void addOpenContainer(ContainerMachine<BlockEntityChest> c, Player player) {
        super.addOpenContainer(c, player);
        if (!remove && !player.isSpectator()) {
            this.manager.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void onContainerClose(ContainerMachine<BlockEntityChest> c, Player player) {
        super.onContainerClose(c, player);
        if (!remove && !player.isSpectator()) {
            manager.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }
}
