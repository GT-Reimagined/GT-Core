package io.github.gregtechintergalactical.gtutility.data;

import io.github.gregtechintergalactical.gtutility.GTUtility;
import io.github.gregtechintergalactical.gtutility.blockentity.BlockEntityMaterial;
import io.github.gregtechintergalactical.gtutility.gui.ContainerWorkbench;
import muramasa.antimatter.capability.IGuiHandler;
import muramasa.antimatter.gui.MenuHandlerMachine;
import net.minecraft.world.entity.player.Inventory;

public class MenuHandlers {
    public static MenuHandlerMachine<? extends BlockEntityMaterial, ? extends ContainerWorkbench> WORKBENCH_HANDLER = new MenuHandlerMachine(GTUtility.ID, "container_workbench") {
        @Override
        public ContainerWorkbench getMenu(IGuiHandler tile, Inventory playerInv, int windowId) {
            return tile instanceof BlockEntityMaterial ? new ContainerWorkbench((BlockEntityMaterial) tile, playerInv, this, windowId) : null;
        }

        @Override
        public String screenDomain() {
            return GTUtility.ID;
        }

        @Override
        public String screenID() {
            return "workbench";
        }
    };

    public static void init(){

    }
}
