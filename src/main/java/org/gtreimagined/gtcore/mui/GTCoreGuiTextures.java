package org.gtreimagined.gtcore.mui;

import brachy.modularui.drawable.UITexture;
import org.gtreimagined.gtcore.GTCore;

public class GTCoreGuiTextures {
    public static class IDs {
        public static final String BRONZE_BACKGROUND = GTCore.ID + ":bronze_background";
        public static final String STEEL_BACKGROUND = GTCore.ID + ":steel_background";
        public static final String PRIMITIVE_BACKGROUND = GTCore.ID + ":primitive_background";
        public static final String BRONZE_ITEM_SLOT = GTCore.ID + ":bronze_item_slot";
        public static final String STEEL_ITEM_SLOT = GTCore.ID + ":steel_item_slot";
        public static final String PRIMITIVE_ITEM_SLOT = GTCore.ID + ":primitive_item_slot";
        public static final String BRONZE_FLUID_SLOT = GTCore.ID + ":bronze_fluid_slot";
        public static final String STEEL_FLUID_SLOT = GTCore.ID + ":steel_fluid_slot";
        public static final String PRIMITIVE_FLUID_SLOT = GTCore.ID + ":primitive_fluid_slot";
    }

    public static final UITexture TRASH_CAN = UITexture.builder().location(GTCore.ID, "textures/gui/icons/trash_can.png").imageSize(64, 68).build();
    public static final UITexture BRONZE_BACKGROUND = UITexture.builder().name(IDs.BRONZE_BACKGROUND).location(GTCore.ID, "textures/gui/background/bronze.png").build();
    public static final UITexture STEEL_BACKGROUND = UITexture.builder().name(IDs.STEEL_BACKGROUND).location(GTCore.ID, "textures/gui/background/steel.png").build();
    public static final UITexture PRIMITIVE_BACKGROUND = UITexture.builder().name(IDs.PRIMITIVE_BACKGROUND).location(GTCore.ID, "textures/gui/background/primitive.png").build();
    public static final UITexture BLUEPRINT_SLOT_OVERLAY = UITexture.builder().location(GTCore.ID, "textures/gui/slots/overlay/blueprint.png").defaultColorType().build();
    public static final UITexture CRAFTING_OUTPUT_SLOT_OVERLAY = UITexture.builder().location(GTCore.ID, "textures/gui/slots/overlay/crafting_output.png").defaultColorType().build();
    public static final UITexture ELECTRIC_TOOL_SLOT_OVERLAY = UITexture.builder().location(GTCore.ID, "textures/gui/slots/overlay/electric_tool.png").defaultColorType().build();
    public static final UITexture EXPORT_SLOT_OVERLAY = UITexture.builder().location(GTCore.ID, "textures/gui/slots/overlay/export.png").defaultColorType().build();
    public static final UITexture PARK_SLOT_OVERLAY = UITexture.builder().location(GTCore.ID, "textures/gui/slots/overlay/park.png").defaultColorType().build();
    public static final UITexture BLANK_SLOT = UITexture.builder().location(GTCore.ID, "textures/gui/slots/blank.png").colorType(null).build();

    public static final UITexture BRONZE_ITEM_SLOT = UITexture.builder().name(IDs.BRONZE_ITEM_SLOT).location(GTCore.ID, "textures/gui/slots/item_bronze.png").build();
    public static final UITexture STEEL_ITEM_SLOT = UITexture.builder().name(IDs.STEEL_ITEM_SLOT).location(GTCore.ID, "textures/gui/slots/item_steel.png").build();
    public static final UITexture PRIMITIVE_ITEM_SLOT = UITexture.builder().name(IDs.PRIMITIVE_ITEM_SLOT).location(GTCore.ID, "textures/gui/slots/item_primitive.png").build();
    public static final UITexture BRONZE_FLUID_SLOT = UITexture.builder().name(IDs.BRONZE_FLUID_SLOT).location(GTCore.ID, "textures/gui/slots/fluid_bronze.png").build();
    public static final UITexture STEEL_FLUID_SLOT = UITexture.builder().name(IDs.STEEL_FLUID_SLOT).location(GTCore.ID, "textures/gui/slots/fluid_steel.png").build();
    public static final UITexture PRIMITIVE_FLUID_SLOT = UITexture.builder().name(IDs.PRIMITIVE_FLUID_SLOT).location(GTCore.ID, "textures/gui/slots/fluid_primitive.png").build();

    public static final UITexture TO_INV_BUTTON = UITexture.builder().location(GTCore.ID, "textures/gui/button/to_inv.png").defaultColorType().build();
    public static final UITexture TO_PLAYER_BUTTON = UITexture.builder().location(GTCore.ID, "textures/gui/button/to_player.png").defaultColorType().build();
    public static final UITexture BRONZE_MACHINE_STATE = UITexture.builder().location(GTCore.ID, "textures/gui/icons/bronze_machine_state.png").imageSize(36, 18).build();
    public static final UITexture STEEL_MACHINE_STATE = UITexture.builder().location(GTCore.ID, "textures/gui/icons/steel_machine_state.png").imageSize(36, 18).build();

}
