package org.gtreimagined.gtcore.client.model;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import muramasa.antimatter.client.IAntimatterModel;
import muramasa.antimatter.client.ModelUtils;
import muramasa.antimatter.client.RenderHelper;
import muramasa.antimatter.client.SimpleModelState;
import muramasa.antimatter.client.model.IModelConfiguration;
import muramasa.antimatter.texture.Texture;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.GTCore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class IconModel implements IAntimatterModel {
    private final UnbakedModel baseModel;
    static Map<String, Material> TEXTURE_MAP = null;
    static List<Map<String, List<BakedQuad>>> ICON_MODELS = null;
    public static final FaceBakery FACE_BAKERY = new FaceBakery();

    public IconModel(UnbakedModel baseModel, List<BlockElement> blockElements){
        this.baseModel = baseModel;
        if (TEXTURE_MAP == null) {
            TEXTURE_MAP = new Object2ObjectOpenHashMap<>();
            String[] icons = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "percent"};
            for (String icon : icons) {
                TEXTURE_MAP.put(icon, ModelUtils.getBlockMaterial(new Texture(GTCore.ID, "block/characters/" + icon)));
            }
        }
        if (ICON_MODELS == null){
            ICON_MODELS = new ObjectArrayList<>();
            String[] icons = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "percent"};
            for (int i = 0; i < blockElements.size(); i++) {
                BlockElement element = blockElements.get(i);
                Map<String, List<BakedQuad>> map = new Object2ObjectOpenHashMap<>();
                for (String icon : icons) {
                    if (i > 1 && icon.equals("percent")) continue;
                    List<BakedQuad> bakedQuads = new ArrayList<>();
                    for (var entry : element.faces.entrySet()){
                        Direction dir = entry.getKey();
                        BlockElementFace face = entry.getValue();
                        TextureAtlasSprite sprite = ModelUtils.getDefaultTextureGetter().apply(IconModel.TEXTURE_MAP.get(icon));
                        BakedQuad quad = FACE_BAKERY.bakeQuad(element.from, element.to, face, sprite, dir, new SimpleModelState(RenderHelper.faceRotation(Direction.SOUTH)), element.rotation, element.shade, new ResourceLocation(GTCore.ID, "item_storage"));
                        bakedQuads.add(quad);
                    }
                    map.put(icon, bakedQuads);
                }
                ICON_MODELS.add(map);
            }
        }
    }

    @Override
    public BakedModel bakeModel(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation) {
        BakedModel base = baseModel.bake(modelBakery, function, modelState, resourceLocation);
        return new IconBakedModel(Objects.requireNonNull(base));
    }

    @Override
    public Collection<Material> getMaterials(IModelConfiguration iModelConfiguration, Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        Set<Material> materials = new HashSet<>();
        materials.addAll(baseModel.getMaterials(function, set));
        materials.addAll(TEXTURE_MAP.values());
        return materials;
    }
}
