package org.gtreimagined.gtcore.proxy;

import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import muramasa.antimatter.client.ClientPlatformHelper;
import org.gtreimagined.gtcore.block.BlockMaterialChest;
import org.gtreimagined.gtcore.blockentity.BlockEntityChest;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.client.MassStorageRenderer;
import org.gtreimagined.gtcore.client.MaterialChestRenderer;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.machine.BlockMachineMaterial;
import org.gtreimagined.gtcore.machine.BlockMultiMachineMaterial;
import org.gtreimagined.gtcore.machine.ChestMachine;
import org.gtreimagined.gtcore.machine.MassStorageMachine;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.client.ModelUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.gtreimagined.gtcore.blockentity.BlockEntityChest;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.client.MassStorageRenderer;
import org.gtreimagined.gtcore.client.MaterialChestRenderer;
import org.gtreimagined.gtcore.machine.BlockMachineMaterial;
import org.gtreimagined.gtcore.machine.BlockMultiMachineMaterial;
import org.gtreimagined.gtcore.machine.ChestMachine;
import org.gtreimagined.gtcore.machine.MassStorageMachine;

import java.util.function.Consumer;

public class ClientHandler {

    public static void init(){
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new Material(Sheets.SIGN_SHEET, GTCoreBlocks.RUBBER_SIGN.getTexture()));
        AntimatterAPI.all(BlockMachineMaterial.class, b -> {
            if (b.getType() instanceof MassStorageMachine) {
                ClientPlatformHelper.INSTANCE.registerBlockEntityRenderer((BlockEntityType<BlockEntityMassStorage>) b.getType().getTileType(), c -> new MassStorageRenderer<>());
            }
        });

        AntimatterAPI.all(BlockMaterialChest.class, b -> {
            if (b.getType() instanceof ChestMachine) {
                ClientPlatformHelper.INSTANCE.registerBlockEntityRenderer((BlockEntityType<BlockEntityChest>) b.getType().getTileType(), MaterialChestRenderer::new);
            }
        });
        AntimatterAPI.runLaterClient(() -> {
            AntimatterAPI.all(BlockMachineMaterial.class, b -> ModelUtils.INSTANCE.setRenderLayer(b, RenderType.cutout()));
            AntimatterAPI.all(BlockMultiMachineMaterial.class, b -> ModelUtils.INSTANCE.setRenderLayer(b, RenderType.cutout()));
            AntimatterAPI.all(BlockMaterialChest.class, b -> ModelUtils.INSTANCE.setRenderLayer(b, RenderType.cutout()));
            ModelUtils.INSTANCE.setRenderLayer(GTCoreBlocks.RUBBER_SAPLING, RenderType.cutout());
            ModelUtils.INSTANCE.setRenderLayer(GTCoreBlocks.RUBBER_LEAVES, RenderType.cutout());
            ModelUtils.INSTANCE.setRenderLayer(GTCoreBlocks.RUBBER_TRAPDOOR, RenderType.cutout());
            ModelUtils.INSTANCE.setRenderLayer(GTCoreBlocks.RUBBER_DOOR, RenderType.cutout());
            ModelUtils.INSTANCE.setRenderLayer(GTCoreBlocks.SAP_BAG, RenderType.cutout());
        });
    }

    public static void onStitch(TextureAtlas atlas, Consumer<ResourceLocation> spriteFunction) {
        if (!atlas.location().equals(Sheets.CHEST_SHEET)) {
            return;
        }

        spriteFunction.accept(MaterialChestRenderer.MATERIAL_CHEST_BASE);
        spriteFunction.accept(MaterialChestRenderer.MATERIAL_CHEST_OVERLAY);
    }
}
