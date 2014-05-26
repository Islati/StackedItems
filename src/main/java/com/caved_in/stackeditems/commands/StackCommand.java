package com.caved_in.stackeditems.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.Material.*;

public class StackCommand {
	private static final Material[] stackMaterials = new Material[]{
			POTION,
			DIAMOND_SWORD,
			GOLD_SWORD,
			IRON_SWORD,
			STONE_SWORD,
			WOOD_SWORD,
			IRON_HELMET,
			IRON_LEGGINGS,
			IRON_CHESTPLATE,
			IRON_BOOTS,
			GOLD_HELMET,
			GOLD_CHESTPLATE,
			GOLD_LEGGINGS,
			GOLD_BOOTS,
			DIAMOND_HELMET,
			DIAMOND_CHESTPLATE,
			DIAMOND_LEGGINGS,
			DIAMOND_BOOTS,
			LEATHER_HELMET,
			LEATHER_CHESTPLATE,
			LEATHER_LEGGINGS,
			LEATHER_BOOTS,
			WOOD_AXE,
			STONE_AXE,
			IRON_AXE,
			GOLD_AXE,
			DIAMOND_AXE,
			WOOD_SPADE,
			STONE_SPADE,
			IRON_SPADE,
			GOLD_SPADE,
			DIAMOND_SPADE,
			WOOD_HOE,
			STONE_HOE,
			IRON_HOE,
			GOLD_HOE,
			DIAMOND_HOE,
			BOW
	};

	@Command(name = "/stack", permission = "tlc.commands.stack")
	public void onStackCommand(Player player, String[] args) {
		PlayerInventory inventory = player.getInventory();
		Map<Material, List<ItemStack>> sortedMaterialItems = filterMaterials(inventory.getContents());
		Set<List<ItemStack>> listSet = new LinkedHashSet<>();
		for(Map.Entry<Material, List<ItemStack>> entry: sortedMaterialItems.entrySet()) {
			List<ItemStack> itemStacks = entry.getValue();
			if (itemStacks.size() == 0) {
				continue;
			}

			listSet.addAll(itemStacks.stream().map(itemStack -> findSimilarEnchantedItems(itemStack, itemStacks)).collect(Collectors.toList()));
		}

		for(List<ItemStack> sameItemStack : listSet) {
			int listSize = sameItemStack.size();
			Players.sendMessage(player, "Found " + listSize + " similar items in a list  --->");
			if (listSize == 0) {
				continue;
			}
			ItemStack foundItem = sameItemStack.get(0);
			Players.sendMessage(player, Messages.itemInfo(foundItem));
		}
	}

	private static Map<Material, List<ItemStack>> filterMaterials(ItemStack[] items) {
		Map<Material, List<ItemStack>> sortedMaterialItems = new HashMap<>();
		for (ItemStack itemStack : items) {
			if (itemStack == null) {
				continue;
			}
			Material type = itemStack.getType();
			if (!sortedMaterialItems.containsKey(type)) {
				sortedMaterialItems.put(type, Arrays.asList(itemStack));
			} else {
				sortedMaterialItems.get(type).add(itemStack);
			}
		}
		return sortedMaterialItems;
	}

	private static List<ItemStack> findSimilarEnchantedItems(ItemStack item, List<ItemStack> itemsToCheck) {
		return itemsToCheck.stream().filter(itemStack -> hasSameEnchantments(item, itemStack)).collect(Collectors.toList());
	}

	private static boolean hasSameEnchantments(ItemStack item, ItemStack toCheck) {
		if (!Items.hasEnchants(item) || !Items.hasEnchants(toCheck)) {
			return false;
		}

		Map<Enchantment, Integer> itemEnchants = item.getEnchantments();
		Map<Enchantment, Integer> checkEnchants = item.getEnchantments();
		for (Map.Entry<Enchantment, Integer> checkEntry : checkEnchants.entrySet()) {
			if (itemEnchants.containsKey(checkEntry.getKey())) {
				if (!itemEnchants.get(checkEntry.getKey()).equals(checkEntry.getValue())) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
}
