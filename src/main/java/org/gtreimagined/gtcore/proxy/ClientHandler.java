package org.gtreimagined.gtcore.proxy;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.loading.FMLPaths;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.BlockCasing;
import org.gtreimagined.gtcore.block.BlockGTHopper;
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
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.client.ModelUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class ClientHandler {

    public static void init(){
        Sheets.addWoodType(GTCoreBlocks.RUBBER_WOOD_TYPE);
        GTAPI.all(BlockMachineMaterial.class, b -> {
            if (b.getType() instanceof MassStorageMachine) {
                BlockEntityRenderers.register((BlockEntityType<BlockEntityMassStorage>) b.getType().getTileType(), c -> new MassStorageRenderer<>());
            }
        });

        GTAPI.all(BlockMaterialChest.class, b -> {
            if (b.getType() instanceof ChestMachine) {
                BlockEntityRenderers.register((BlockEntityType<BlockEntityChest>) b.getType().getTileType(), MaterialChestRenderer::new);
            }
        });
        GTAPI.runLaterClient(() -> {
            GTAPI.all(BlockMachineMaterial.class, b -> ModelUtils.setRenderLayer(b, RenderType.cutout()));
            GTAPI.all(BlockMultiMachineMaterial.class, b -> ModelUtils.setRenderLayer(b, RenderType.cutout()));
            GTAPI.all(BlockMaterialChest.class, b -> ModelUtils.setRenderLayer(b, RenderType.cutout()));
            GTAPI.all(BlockGTHopper.class, b -> ModelUtils.setRenderLayer(b, RenderType.cutout()));
            GTAPI.all(BlockCasing.class, b -> ModelUtils.setRenderLayer(b, RenderType.cutout()));
            ModelUtils.setRenderLayer(GTCoreBlocks.RUBBER_SAPLING, RenderType.cutout());
            ModelUtils.setRenderLayer(GTCoreBlocks.RUBBER_LEAVES, RenderType.cutout());
            ModelUtils.setRenderLayer(GTCoreBlocks.RUBBER_TRAPDOOR, RenderType.cutout());
            ModelUtils.setRenderLayer(GTCoreBlocks.RUBBER_DOOR, RenderType.cutout());
            ModelUtils.setRenderLayer(GTCoreBlocks.SAP_BAG, RenderType.cutout());
            writeResourcePack("Alternate-Stone-Textures", "alternate-stone-textures");
        });
    }

    public static void onStitch(TextureAtlas atlas, Consumer<ResourceLocation> spriteFunction) {
        if (!atlas.location().equals(Sheets.CHEST_SHEET)) {
            return;
        }

        spriteFunction.accept(MaterialChestRenderer.MATERIAL_CHEST_BASE);
        spriteFunction.accept(MaterialChestRenderer.MATERIAL_CHEST_OVERLAY);
    }

    private static void writeResourcePack(String writeName, String readName){
        File dir = new File(FMLPaths.CONFIGDIR.get().getParent().toFile(), "resourcepacks");
        File target = new File(dir, writeName + ".zip");


        //if(!target.exists())
        try {
            dir.mkdirs();
            InputStream in = GTCore.class.getResourceAsStream("/assets/" + GTCore.ID + "/" + readName + ".zip");
            FileOutputStream out = new FileOutputStream(target);

            byte[] buf = new byte[16384];
            int len = 0;
            while((len = in.read(buf)) > 0)
                out.write(buf, 0, len);

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
