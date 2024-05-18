package io.github.gregtechintergalactical.gtcore.blockentity;


import io.github.gregtechintergalactical.gtcore.machine.MaterialMachine;
import muramasa.antimatter.gui.container.ContainerMachine;
import muramasa.antimatter.machine.MachineState;
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

import java.util.List;

//TODO needed?
//@Environment(value = EnvType.CLIENT, _interface = LidBlockEntity.class)
public class BlockEntityBarrel extends BlockEntityMaterial<BlockEntityBarrel> {
    protected float lidAngle;
    protected float prevLidAngle;
    protected int numPlayersUsing;
    private int ticksSinceSync;

    private final ContainerOpenersCounter manager = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level world, BlockPos pos, BlockState state) {
            BlockEntityBarrel.playSound(world, pos, state, SoundEvents.BARREL_OPEN);
        }

        @Override
        protected void onClose(Level world, BlockPos pos, BlockState state) {
            BlockEntityBarrel.playSound(world, pos, state, SoundEvents.BARREL_CLOSE);
        }

        @Override
        protected void openerCountChanged(Level world, BlockPos pos, BlockState state, int oldCount, int newCount) {
            world.blockEvent(pos, state.getBlock(), 1, newCount);
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            return player.containerMenu instanceof ContainerMachine<?> handler &&
                    handler.handler.handler instanceof BlockEntityBarrel chest && chest.getBlockPos().equals(BlockEntityBarrel.this.getBlockPos());
        }
    };


    public BlockEntityBarrel(MaterialMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private static void playSound(Level world, BlockPos pos, BlockState state, SoundEvent soundIn) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.5D;
        double d2 = (double) pos.getZ() + 0.5D;

        world.playSound((Player) null, d0, d1, d2, soundIn, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
    }
    @Override
    public void addOpenContainer(ContainerMachine<BlockEntityBarrel> c, Player player) {
        super.addOpenContainer(c, player);
        if (!remove && !player.isSpectator()) {
            if (manager.getOpenerCount() == 0){
                this.setMachineState(MachineState.ACTIVE);
            }
            this.manager.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void onContainerClose(ContainerMachine<BlockEntityBarrel> c, Player player) {
        super.onContainerClose(c, player);
        if (!remove && !player.isSpectator()) {
            manager.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
            if (manager.getOpenerCount() == 0){
                this.setMachineState(MachineState.IDLE);
            }
        }
    }
}
