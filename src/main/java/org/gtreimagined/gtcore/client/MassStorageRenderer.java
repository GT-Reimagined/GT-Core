package org.gtreimagined.gtcore.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix3f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.util.CodeUtils;


//Credit to mekanism for the item rendering
public class MassStorageRenderer<T extends BlockEntityMassStorage> implements BlockEntityRenderer<T> {

    private static final Matrix3f FAKE_NORMALS;

    static {
        Vector3f NORMAL = new Vector3f(1, 1, 1);
        NORMAL.normalize();
        FAKE_NORMALS = new Matrix3f(new Quaternion(NORMAL, 0, true));
    }

    @Override
    public void render(T tile, float pPartialTicks, PoseStack matrix, MultiBufferSource pBuffer, int light, int overlayLight) {
        if (tile.getMachineState() == MachineState.ACTIVE) return;
        Level level = tile.getLevel();
        if (level == null || Minecraft.getInstance().player != null && !tile.getBlockPos().closerThan(Minecraft.getInstance().player.getOnPos(), 32)) {
            return;
        }
        var display = tile.itemHandler.map(i -> i.getHandler(SlotType.DISPLAY)).orElse(null);
        var storage = tile.itemHandler.map(i -> i.getHandler(SlotTypes.UNLIMITED)).orElse(null);
        if (display != null && storage != null) {
            ItemStack stack = display.getStackInSlot(0);
            if (stack.isEmpty()) stack = storage.getStackInSlot(0);
            if (!stack.isEmpty()) {
                Direction facing = tile.getFacing();
                BlockPos coverPos = tile.getBlockPos().relative(facing);
                BlockState state = level.getBlockState(coverPos);
                if (state.isAir() || !state.canOcclude() || !state.isFaceSturdy(level, coverPos, facing.getOpposite())){
                    matrix.pushPose();
                    matrix.last().normal().load(FAKE_NORMALS);
                    switch (facing) {
                        case NORTH -> {
                            matrix.translate(0.73, 0.83, -0.0001);
                            matrix.mulPose(Vector3f.YP.rotationDegrees(180));
                        }
                        case SOUTH -> matrix.translate(0.27, 0.83, 1.0001);
                        case WEST -> {
                            matrix.translate(-0.0001, 0.83, 0.27);
                            matrix.mulPose(Vector3f.YP.rotationDegrees(-90));
                        }
                        case EAST -> {
                            matrix.translate(1.0001, 0.83, 0.73);
                            matrix.mulPose(Vector3f.YP.rotationDegrees(90));
                        }
                    }
                    float scale = 0.03125F;
                    float scaler = 0.9F;
                    matrix.scale(scale * scaler, scale * scaler, 0.0001F);
                    matrix.translate(8, -16, 8);
                    matrix.scale(16, 16, 16);
                    //Calculate lighting based on the light at the block the bin is facing
                    light = LevelRenderer.getLightColor(level, tile.getBlockPos().relative(facing));
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.GUI, light, overlayLight, matrix, pBuffer,
                            CodeUtils.bindInt(tile.getBlockPos().asLong()));
                    matrix.popPose();
                }


            }
        }
    }
}
