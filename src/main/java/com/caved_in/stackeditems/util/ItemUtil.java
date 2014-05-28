package com.caved_in.stackeditems.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.bukkit.Material.*;
import static org.bukkit.Material.BOW;

public class ItemUtil {
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

	private static Set<Material> validStackTypes = Sets.newHashSet(stackMaterials);

	public static Map<Material, ArrayList<ItemStack>> filterMaterials(ItemStack[] items) {
		Map<Material, ArrayList<ItemStack>> sortedMaterialItems = new HashMap<>();
		for (ItemStack itemStack : items) {
			if (itemStack == null) {
				continue;
			}
			Material type = itemStack.getType();

			if (!validStackTypes.contains(type)) {
				continue;
			}

			if (!sortedMaterialItems.containsKey(type)) {
				sortedMaterialItems.put(type, Lists.newArrayList(itemStack));
			} else {
				sortedMaterialItems.get(type).add(itemStack);
			}
		}
		return sortedMaterialItems;
	}
}
