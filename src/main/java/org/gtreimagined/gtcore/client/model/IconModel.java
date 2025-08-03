package org.gtreimagined.gtcore.client.model;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.client.IGTModel;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.texture.Texture;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class IconModel implements IGTModel<IconModel> {
    private final UnbakedModel baseModel;
    static Map<String, Material> TEXTURE_MAP = null;
    static Map<String, TextureAtlasSprite> SPRITE_MAP = null;
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
    }

    @Override
    public BakedModel bakeModel(IGeometryBakingContext configuration, ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ItemOverrides overrides, ResourceLocation resourceLocation) {
        if (SPRITE_MAP == null) {
            SPRITE_MAP = new Object2ObjectOpenHashMap<>();
            for (String icon : TEXTURE_MAP.keySet()) {
                SPRITE_MAP.put(icon, function.apply(TEXTURE_MAP.get(icon)));
            }
        }
        BakedModel base = baseModel.bake(modelBakery, function, modelState, resourceLocation);
        return new IconBakedModel(Objects.requireNonNull(base));
    }

    @Override
    public Collection<Material> getMaterials(IGeometryBakingContext iModelConfiguration, Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        Set<Material> materials = new HashSet<>();
        materials.addAll(baseModel.getMaterials(function, set));
        materials.addAll(TEXTURE_MAP.values());
        return materials;
    }
}
