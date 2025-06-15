package org.gtreimagined.gtcore.machine;

import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.Shapes;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityBookShelf;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.Utils;

import java.util.function.Supplier;

import static org.gtreimagined.gtlib.machine.MachineFlag.GUI;
import static org.gtreimagined.gtlib.machine.MachineFlag.ITEM;

public class BookShelfMachine extends MaterialMachine {
    Supplier<Block> woodBlockSupplier;
    String woodType;
    private BookShelfMachine(String domain, String id, Material material){
        super(domain, id, material);
        setTiers(Tier.NONE);
        setTile(BlockEntityBookShelf::new);
        addFlags(GUI, ITEM);
        setCustomModel((a,s,d) -> new ResourceLocation(GTCore.ID, "block/machine/overlay/bookshelf/" + d.getSerializedName()));
        Texture empty = new Texture(GTCore.ID, "block/machine/empty");
        setOverlayTextures((machine, machineState, tier, i) -> new Texture[]{
                empty, empty, empty,empty, empty, empty
        });
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 7; x++) {
                add(SlotType.STORAGE, 26 + (18 * x), 8 + (18 * y));
            }
        }
        setCustomShape((s, w, p, c) -> s.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis() == Axis.Z ? GTCoreBlocks.BOOKSHELF_Z_SHAPE : GTCoreBlocks.BOOKSHELF_X_SHAPE);
        GTAPI.register(BookShelfMachine.class, this);
    }

    public BookShelfMachine(Material material) {
        this(GTCore.ID, material.getId() + "_bookshelf", material);
        setBaseTexture(new Texture(GTCore.ID, "block/machine/base/bookshelf"));
    }

    public BookShelfMachine(String woodType, Texture woodTexture, Supplier<Block> woodBlockSupplier) {
        this(GTCore.ID, woodType + "_bookshelf", Material.NULL);
        setBaseTexture(woodTexture);
        this.woodType = woodType;
        this.woodBlockSupplier = woodBlockSupplier;
    }

    public BookShelfMachine(String woodType, String modid, Texture woodTexture, Supplier<Block> woodBlockSupplier) {
        this(GTCore.ID, modid + "/" + woodType + "_bookshelf", Material.NULL);
        setBaseTexture(woodTexture);
        this.woodType = woodType;
        this.woodBlockSupplier = woodBlockSupplier;
    }

    @Override
    public String getLang(String lang) {
       return woodBlockSupplier != null ? "Wooden Bookshelf (" + Utils.lowerUnderscoreToUpperSpaced(this.woodType) + ")" : "Bookshelf (" + this.getMaterial().getDisplayNameString() + ")";
    }

    public Supplier<Block> getWoodBlockSupplier() {
        return woodBlockSupplier;
    }
}
