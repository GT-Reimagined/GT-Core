package org.gtreimagined.gtcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.tree.ResinState;
import org.gtreimagined.gtcore.tree.block.BlockRubberLog;
import org.gtreimagined.gtlib.blockentity.BlockEntityBase;

public class BlockEntitySapBag extends BlockEntityBase<BlockEntitySapBag> {
    ItemStack sap = ItemStack.EMPTY;
    Direction facing = Direction.NORTH;
    public BlockEntitySapBag(BlockPos pos, BlockState state) {
        super(GTCoreBlocks.SAP_BAG_BLOCK_ENTITY, pos, state);
    }

    public void checkRubber() {
        BlockState state = level.getBlockState(this.getBlockPos().relative(facing));
        if (state.getBlock() == GTCoreBlocks.RUBBER_LOG || state.getBlock() == GTCoreBlocks.STRIPPED_RUBBER_LOG){
            if(state.getValue(ResinState.INSTANCE) ==  ResinState.FILLED && state.getValue(BlockRubberLog.RESIN_FACING) == facing.getOpposite()){
                boolean successful = false;
                int amount = (1 + level.random.nextInt(3));
                if (sap.isEmpty()){
                    setSap(new ItemStack(GTCoreItems.StickyResin, amount));
                    successful = true;
                } else if (sap.getCount() < 64){
                    growSap(amount);
                    successful = true;
                }
                if (successful){
                    level.setBlockAndUpdate(this.getBlockPos().relative(facing), state.setValue(ResinState.INSTANCE, ResinState.EMPTY));
                }
            }
        }
    }

    public void onBlockUpdate(){
        checkRubber();
    }

    public void setFacing(Direction facing){
        this.facing = facing;
    }

    public Direction getFacing() {
        return facing;
    }

    public ItemStack getSap() {
        return sap;
    }

    public void setSap(ItemStack sap) {
        this.sidedSync(true);
        this.sap = sap;
    }

    public void growSap(int amount){
        this.sidedSync(true);
        this.sap.grow(amount);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("F", facing.get3DDataValue());
        tag.put("S", sap.save(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.facing = Direction.from3DDataValue(nbt.getInt("F"));
        this.sap = ItemStack.of(nbt.getCompound("S"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putInt("F", facing.get3DDataValue());
        tag.put("S", sap.save(new CompoundTag()));
        return tag;
    }
}
