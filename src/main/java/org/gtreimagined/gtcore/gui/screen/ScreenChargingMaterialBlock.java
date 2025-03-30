package org.gtreimagined.gtcore.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.gtreimagined.gtcore.blockentity.BlockEntityMaterial;
import org.gtreimagined.gtlib.gui.container.ContainerMachine;
import org.gtreimagined.gtlib.gui.screen.ScreenMachine;

public class ScreenChargingMaterialBlock<T extends BlockEntityMaterial<T>> extends ScreenMachine<T, ContainerMachine<T>> {
    public ScreenChargingMaterialBlock(ContainerMachine<T> container, Inventory inv, Component name, String location) {
        super(container, inv, name);
    }

    @Override
    protected void drawTitle(PoseStack stack, int mouseX, int mouseY) {
    }
}
