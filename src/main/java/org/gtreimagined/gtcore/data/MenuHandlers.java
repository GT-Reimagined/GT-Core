package org.gtreimagined.gtcore.data;

import net.minecraft.world.entity.player.Inventory;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityMaterial;
import org.gtreimagined.gtcore.gui.ContainerWorkbench;
import org.gtreimagined.gtlib.capability.IGuiHandler;
import org.gtreimagined.gtlib.gui.MenuHandlerMachine;

public class MenuHandlers {
    public static MenuHandlerMachine<? extends BlockEntityMaterial, ? extends ContainerWorkbench> WORKBENCH_HANDLER = new MenuHandlerMachine(GTCore.ID, "container_workbench") {
        @Override
        public ContainerWorkbench getMenu(IGuiHandler tile, Inventory playerInv, int windowId) {
            return tile instanceof BlockEntityMaterial ? new ContainerWorkbench((BlockEntityMaterial) tile, playerInv, this, windowId) : null;
        }

        @Override
        public String screenDomain() {
            return GTCore.ID;
        }

        @Override
        public String screenID() {
            return "workbench";
        }
    };

    public static void init(){

    }
}
