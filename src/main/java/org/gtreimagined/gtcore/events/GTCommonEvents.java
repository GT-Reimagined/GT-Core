package org.gtreimagined.gtcore.events;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.gtreimagined.gtcore.BookRegistration;
import org.gtreimagined.gtcore.machine.BlockMachineMaterial;
import org.gtreimagined.gtcore.machine.BlockMultiMachineMaterial;
import org.gtreimagined.gtlib.block.BlockStorage;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialItem;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.pipe.BlockFluidPipe;
import org.gtreimagined.gtlib.pipe.BlockPipe;
import org.gtreimagined.gtlib.tool.IGTTool;
import org.gtreimagined.gtlib.util.Utils;

import java.awt.*;
import java.util.UUID;

public class GTCommonEvents {

    private static int BEAR_INVENTORY_COOL_DOWN = 5;

    public static final UUID BEAR_UUID = UUID.fromString("1964e3d1-6500-40e7-9ff2-e6161d41a8c2");
    public static final UUID TRINS_UUID = UUID.fromString("7c042366-854c-4582-8d2c-6831646ba5c7");

    public static void onPlayerTick(boolean end, boolean logicalServer, Player player){
        if (end && logicalServer && !player.isInvulnerable() && player.getInventory().contains(GTMaterialTypes.HOT_INGOT.getTag()) && !Utils.isFullHazmatSuit(player)){
            BlockFluidPipe.applyTemperatureDamage(player, 1700, 1.0f, 1.0f);
        }
        if (end && logicalServer && player.tickCount % 120 == 0){
            if (player.getUUID().equals(BEAR_UUID)) {
                ItemStack tStack;
                int tEmptySlots = 36;
                int tFullSlots = 0;
                for (int i = 0; i < 36; i++) {
                    tStack = player.getInventory().getItem(i);

                    if (!tStack.isEmpty()) {
                        tEmptySlots--;
                        tFullSlots++;
                    }
                }

                // This Code is to tell Bear and all the people around him that he should clean up his always cluttered Inventory.
                if (--BEAR_INVENTORY_COOL_DOWN < 0 && tEmptySlots < 4) {
                    InventoryMenu playerContainer = player.inventoryMenu;
                    BEAR_INVENTORY_COOL_DOWN = 100;
                    for (int i = 0; i < player.level.players().size(); i++) {
                        Player player2 = player.level.players().get(i);
                        if (player2 == null) continue;
                        if (player2 == player) {
                            if (player2.blockPosition().getY() < 30) {
                                player2.displayClientMessage(Utils.literal("Stop making Holes in the Ground, Bear!"), false);
                            } else {
                                // Bear does not like being called these names, so lets annoy him. XD
                                switch (tEmptySlots) {
                                    case 0 -> {
                                        player2.displayClientMessage(Utils.literal("Alright Buttercup, your Inventory is full, time to go home."), false);
                                    }
                                    case 1 -> {
                                        player2.displayClientMessage(Utils.literal("Your Inventory is starting to get full, Buttercup"), false);
                                    }
                                    case 2 -> {
                                        player2.displayClientMessage(Utils.literal("Your Inventory is starting to get full, Bean989Sr"), false);
                                    }
                                    case 3 -> {
                                        player2.displayClientMessage(Utils.literal("Your Inventory is starting to get full, Mr. Bear"), false);
                                    }
                                }
                            }
                        } else if (player2.getUUID().equals(new UUID(0x06c2928890db44c5L, 0xa642db906b52eb59L))) {
                            ItemStack cookie = new ItemStack(Items.COOKIE);
                            ListTag list = new ListTag();
                            list.add(new CompoundTag());
                            cookie.getOrCreateTag().put("Enchantments", list);
                            cookie.setHoverName(Utils.literal("Jr. Cookie"));
                            if (!player2.addItem(cookie)){
                                player2.drop(cookie, true);
                            }
                            player2.displayClientMessage(Utils.literal("Have a Jr. Cookie. Please tell Fatass to clean his Inventory, or smack him with it."), false);
                        } else if (player2.getUUID().equals(new UUID(0xd84f965487ea46a9L, 0x881fc6aa45dd5af8L))) {
                            player2.displayClientMessage(Utils.literal("I'm not trying to tell you what to do, but please don't hurt Bear."), false);
                        } else if (player2.getUUID().equals(new UUID(0xf6728edb5a16449bL, 0xa8af8ae43bf79d63L))) {
                            player2.displayClientMessage(Utils.literal("Please moo at Bear989 to clean his inventory."), false);
                        } else if (player2.getUUID().equals(new UUID(0xd8c0b6bd45504970L, 0xb7c00c4f8d8187c6L))) {
                            player2.displayClientMessage(Utils.literal("Could you tell Bear989Sr very gently, that his Inventory is a fucking mess again?"), false);
                        } else if (player2.getUUID().equals(new UUID(0x91a59513e8ea45a4L, 0xb8afc275085b0451L))) {
                            player2.displayClientMessage(Utils.literal("Here is your special Message to make you tell Bear989Sr to clean his Inventory."), false);
                        } else if (player2.getUUID().equals(new UUID(0x7c042366854c4582L, 0x8d2c6831646ba5c7L))) {
                            player2.displayClientMessage(Utils.literal("Let the mother fucker know he's full of shit."), false);
                        } else {
                            if (player2.blockPosition().closerThan(player.blockPosition(), 100D)) {
                                player2.displayClientMessage(Utils.literal("There is this fella called Bear-Nine-Eight-Nine, needing be reminded of his Inventory being a major Pine."), false);
                            }
                        }
                    }
                }
            }
            if (!Utils.isFullHazmatSuit(player) && !player.isInvulnerable()) {
                for (ItemStack stack : player.getAllSlots()){
                    Material m = null;
                    if (stack.getItem() instanceof IGTTool tool) m = tool.getPrimaryMaterial(stack);
                    if (stack.getItem() instanceof MaterialItem item) m = item.getMaterial();
                    if (stack.getItem() instanceof BlockItem blockItem){
                        if (blockItem.getBlock() instanceof BlockStorage storage) m = storage.getMaterial();
                        if (blockItem.getBlock() instanceof BlockMachineMaterial machineMaterial) m = machineMaterial.getMaterial();
                        if (blockItem.getBlock() instanceof BlockMultiMachineMaterial machineMaterial) m = machineMaterial.getMaterial();
                        if (blockItem.getBlock() instanceof BlockPipe<?> pipe) m = pipe.getType().getMaterial();
                    }
                    if (m != null && m.has(MaterialTags.RADIOACTIVE)){
                        int level = MaterialTags.RADIOACTIVE.getInt(m);
                        if (level > 0){
                            Utils.applyRadioactivity(player, level, stack.getCount());
                        }
                    }
                }
            }
        }
    }

    public static void onTooltipAdd(ItemTooltipEvent event){
        if (BookRegistration.getTextureMap().containsKey(event.getItemStack().getItem())){
            event.getToolTip().add(1, Utils.translatable("tooltip.gtcore.bookshelf_item").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
