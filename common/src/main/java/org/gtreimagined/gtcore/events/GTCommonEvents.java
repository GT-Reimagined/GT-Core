package org.gtreimagined.gtcore.events;

import org.gtreimagined.gtcore.machine.BlockMachineMaterial;
import org.gtreimagined.gtcore.machine.BlockMultiMachineMaterial;
import muramasa.antimatter.block.BlockStorage;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialItem;
import muramasa.antimatter.material.MaterialTags;
import muramasa.antimatter.pipe.BlockFluidPipe;
import muramasa.antimatter.pipe.BlockPipe;
import muramasa.antimatter.tool.IAntimatterTool;
import muramasa.antimatter.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.machine.BlockMachineMaterial;
import org.gtreimagined.gtcore.machine.BlockMultiMachineMaterial;

import java.util.UUID;

public class GTCommonEvents {

    private static int BEAR_INVENTORY_COOL_DOWN = 5;

    public static final UUID BEAR_UUID = UUID.fromString("1964e3d1-6500-40e7-9ff2-e6161d41a8c2");

    public static void onPlayerTick(boolean end, boolean logicalServer, Player player){
        if (end && logicalServer && !player.isInvulnerable() && player.getInventory().contains(AntimatterMaterialTypes.INGOT_HOT.getTag()) && !Utils.isFullHazmatSuit(player)){
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
                                player2.sendMessage(new TextComponent("Stop making Holes in the Ground, Bear!"), player2.getUUID());
                            } else {
                                // Bear does not like being called these names, so lets annoy him. XD
                                switch (tEmptySlots) {
                                    case 0:
                                        player2.sendMessage(new TextComponent("Alright Buttercup, your Inventory is full, time to go home."), player2.getUUID());
                                        break;
                                    case 1:
                                        player2.sendMessage(new TextComponent("Your Inventory is starting to get full, Buttercup"), player2.getUUID());
                                        break;
                                    case 2:
                                        player2.sendMessage(new TextComponent("Your Inventory is starting to get full, Bean989Sr"), player2.getUUID());
                                        break;
                                    case 3:
                                        player2.sendMessage(new TextComponent("Your Inventory is starting to get full, Mr. Bear"), player2.getUUID());
                                        break;
                                }
                            }
                        } else if (player2.getUUID().equals(new UUID(0x06c2928890db44c5L, 0xa642db906b52eb59L))) {
                            ItemStack cookie = new ItemStack(Items.COOKIE);
                            ListTag list = new ListTag();
                            list.add(new CompoundTag());
                            cookie.getOrCreateTag().put("Enchantments", list);
                            cookie.setHoverName(new TextComponent("Jr. Cookie"));
                            if (!player2.addItem(cookie)){
                                player2.drop(cookie, true);
                            }
                            player2.sendMessage(new TextComponent("Have a Jr. Cookie. Please tell Fatass to clean his Inventory, or smack him with it."), player2.getUUID());
                        } else if (player2.getUUID().equals(new UUID(0xd84f965487ea46a9L, 0x881fc6aa45dd5af8L))) {
                            player2.sendMessage(new TextComponent("I'm not trying to tell you what to do, but please don't hurt Bear."), player2.getUUID());
                        } else if (player2.getUUID().equals(new UUID(0xf6728edb5a16449bL, 0xa8af8ae43bf79d63L))) {
                            player2.sendMessage(new TextComponent("Please moo at Bear989 to clean his inventory."), player2.getUUID());
                        } else if (player2.getUUID().equals(new UUID(0xd8c0b6bd45504970L, 0xb7c00c4f8d8187c6L))) {
                            player2.sendMessage(new TextComponent("Could you tell Bear989Sr very gently, that his Inventory is a fucking mess again?"), player2.getUUID());
                        } else if (player2.getUUID().equals(new UUID(0x91a59513e8ea45a4L, 0xb8afc275085b0451L))) {
                            player2.sendMessage(new TextComponent("Here is your special Message to make you tell Bear989Sr to clean his Inventory."), player2.getUUID());
                        } else if (player2.getUUID().equals(new UUID(0x7c042366854c4582L, 0x8d2c6831646ba5c7L))) {
                            player2.sendMessage(new TextComponent("Let the mother fucker know he's full of shit."), player2.getUUID());
                        } else {
                            if (player2.blockPosition().closerThan(player.blockPosition(), 100D)) {
                                player2.sendMessage(new TextComponent("There is this fella called Bear-Nine-Eight-Nine, needing be reminded of his Inventory being a major Pine."), player2.getUUID());
                            }
                        }
                    }
                }
            }
            if (!Utils.isFullHazmatSuit(player) && !player.isInvulnerable()) {
                for (ItemStack stack : player.getAllSlots()){
                    Material m = null;
                    if (stack.getItem() instanceof IAntimatterTool tool) m = tool.getPrimaryMaterial(stack);
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
}
