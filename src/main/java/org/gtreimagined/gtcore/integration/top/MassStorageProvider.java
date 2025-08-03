package org.gtreimagined.gtcore.integration.top;

import mcjty.theoneprobe.api.CompoundText;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;
import mcjty.theoneprobe.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.integration.top.TheOneProbePlugin;
import org.gtreimagined.gtlib.util.Utils;

import java.awt.*;

public class MassStorageProvider implements IProbeInfoProvider {

    public static void createTopProvider(){
        TheOneProbePlugin.addProbeInfoProvider(MassStorageProvider::new);
    }

    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(GTCore.ID, "mass_storage");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
        BlockEntity blockEntity = level.getBlockEntity(iProbeHitData.getPos());
        if (blockEntity instanceof BlockEntityMassStorage massStorage){
            var handler = massStorage.itemHandler.map(i -> {
                var h = i.getHandler(SlotTypes.UNLIMITED);
                if (h.getStackInSlot(0).isEmpty()) return i.getHandler(SlotType.DISPLAY);
                return h;
            }).orElse(null);
            if (handler != null){
                IProbeInfo vertical = iProbeInfo.vertical(iProbeInfo.defaultLayoutStyle().borderColor(Config.chestContentsBorderColor).spacing(0));
                IProbeInfo horizontal = vertical.horizontal((new LayoutStyle()).spacing(10).alignment(ElementAlignment.ALIGN_CENTER));
                ItemStack stack = handler.getStackInSlot(0);
                if (stack.getCount() > 0){
                    MutableComponent text = Utils.literal("").append(stack.getHoverName());
                    if (stack.hasCustomHoverName()) text.withStyle(ChatFormatting.ITALIC);
                    horizontal.item(stack, new ItemStyle().width(16).height(16)).text(CompoundText.create().text(text));
                }
            }
        }
    }
}
