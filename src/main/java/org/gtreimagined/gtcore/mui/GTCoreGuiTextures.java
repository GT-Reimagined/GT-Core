package org.gtreimagined.gtcore.mui;

import brachy.modularui.drawable.UITexture;
import org.gtreimagined.gtcore.GTCore;

public class GTCoreGuiTextures {
    public static final UITexture TRASH_CAN = UITexture.builder().location(GTCore.ID, "textures/gui/icons/trash_can.png").imageSize(64, 68).build();
    public static final UITexture BLUEPRINT_SLOT_OVERLAY = UITexture.fullImage(GTCore.ID, "textures/gui/slots/overlay/blueprint.png");
    public static final UITexture CRAFTING_OUTPUT_SLOT_OVERLAY = UITexture.fullImage(GTCore.ID, "textures/gui/slots/overlay/crafting_output.png");
    public static final UITexture ELECTRIC_TOOL_SLOT_OVERLAY = UITexture.fullImage(GTCore.ID, "textures/gui/slots/overlay/electric_tool.png");
    public static final UITexture EXPORT_SLOT_OVERLAY = UITexture.fullImage(GTCore.ID, "textures/gui/slots/overlay/export.png");
    public static final UITexture PARK_SLOT_OVERLAY = UITexture.fullImage(GTCore.ID, "textures/gui/slots/overlay/park.png");
    public static final UITexture BLANK_SLOT = UITexture.fullImage(GTCore.ID, "textures/gui/slots/blank.png");

    public static final UITexture TO_INV_BUTTON = UITexture.fullImage(GTCore.ID, "textures/gui/button/to_inv.png");
    public static final UITexture TO_PLAYER_BUTTON = UITexture.fullImage(GTCore.ID, "textures/gui/button/to_player.png");

}
