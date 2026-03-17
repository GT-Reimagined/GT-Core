package org.gtreimagined.gtcore.mui;

import brachy.modularui.drawable.ColorType;
import brachy.modularui.drawable.UITexture;
import org.gtreimagined.gtcore.GTCore;

public class GTCoreGuiTextures {
    public static final UITexture TRASH_CAN = UITexture.builder().location(GTCore.ID, "textures/gui/icons/trash_can.png").imageSize(64, 68).build();
    public static final UITexture BLUEPRINT_SLOT_OVERLAY = UITexture.builder().location(GTCore.ID, "textures/gui/slots/overlay/blueprint.png").defaultColorType().build();
    public static final UITexture CRAFTING_OUTPUT_SLOT_OVERLAY = UITexture.builder().location(GTCore.ID, "textures/gui/slots/overlay/crafting_output.png").defaultColorType().build();
    public static final UITexture ELECTRIC_TOOL_SLOT_OVERLAY = UITexture.builder().location(GTCore.ID, "textures/gui/slots/overlay/electric_tool.png").defaultColorType().build();
    public static final UITexture EXPORT_SLOT_OVERLAY = UITexture.builder().location(GTCore.ID, "textures/gui/slots/overlay/export.png").defaultColorType().build();
    public static final UITexture PARK_SLOT_OVERLAY = UITexture.builder().location(GTCore.ID, "textures/gui/slots/overlay/park.png").defaultColorType().build();
    public static final UITexture BLANK_SLOT = UITexture.builder().location(GTCore.ID, "textures/gui/slots/blank.png").colorType(null).build();

    public static final UITexture TO_INV_BUTTON = UITexture.builder().location(GTCore.ID, "textures/gui/button/to_inv.png").defaultColorType().build();
    public static final UITexture TO_PLAYER_BUTTON = UITexture.builder().location(GTCore.ID, "textures/gui/button/to_player.png").defaultColorType().build();

}
