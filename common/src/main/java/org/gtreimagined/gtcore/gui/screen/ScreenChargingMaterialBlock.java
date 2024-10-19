package org.gtreimagined.gtcore.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import org.gtreimagined.gtcore.blockentity.BlockEntityMaterial;
import muramasa.antimatter.gui.container.ContainerMachine;
import muramasa.antimatter.gui.screen.ScreenMachine;
import muramasa.antimatter.machine.MachineFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.gtreimagined.gtcore.blockentity.BlockEntityMaterial;

public class ScreenChargingMaterialBlock<T extends BlockEntityMaterial<T>> extends ScreenMachine<T, ContainerMachine<T>> {
    public ScreenChargingMaterialBlock(ContainerMachine<T> container, Inventory inv, Component name, String location) {
        super(container, inv, name);
    }

    @Override
    protected void drawTitle(PoseStack stack, int mouseX, int mouseY) {
    }
}
