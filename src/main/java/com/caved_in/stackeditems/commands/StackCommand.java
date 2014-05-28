package com.caved_in.stackeditems.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.SubCommand;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.stackeditems.StackedItems;
import com.caved_in.stackeditems.config.Configuration;
import com.caved_in.stackeditems.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class StackCommand {

	@Command(name = "stack", permission = "tlc.commands.stack")
	public void onStackCommand(Player player, String[] args) {
		if (args.length > 0) {
			return;
		}

		int MAX_STACK_SIZE = StackedItems.getConfiguration().getStackSize();
		PlayerInventory inventory = player.getInventory();
		Map<Material, ArrayList<ItemStack>> sortedMaterialItems = ItemUtil.filterMaterials(inventory.getContents());
		Set<List<ItemStack>> listSet = new LinkedHashSet<>();
		for(Map.Entry<Material, ArrayList<ItemStack>> entry: sortedMaterialItems.entrySet()) {
			List<ItemStack> itemStacks = entry.getValue();
			if (itemStacks.size() == 0) {
				continue;
			}

			for(ItemStack itemStack : itemStacks) {
				listSet.add(Items.findSimilarEnchantedItems(itemStack,itemStacks));
			}
		}

		for(List<ItemStack> sameItemStack : listSet) {
			int itemAmount = 0;

			int listSize = sameItemStack.size();
			if (listSize == 1 || listSize == 0) {
				continue;
			}

			for(ItemStack itemStack : sameItemStack) {
				itemAmount += itemStack.getAmount();
				inventory.remove(itemStack);
			}

			ItemStack item = sameItemStack.get(0).clone();
			inventory.remove(item);
			if (itemAmount >= MAX_STACK_SIZE) {
				item.setAmount(MAX_STACK_SIZE);
				int stackAmount = (int)Math.round(itemAmount / MAX_STACK_SIZE);
				for(int i = 0; i < stackAmount; i++) {
					inventory.addItem(item);
				}
				itemAmount -= (stackAmount * MAX_STACK_SIZE);
				item.setAmount(itemAmount);
				inventory.addItem(item);

			} else {
				inventory.addItem(item);
			}
		}

		Players.sendMessage(player, "&aAll similar items have been stacked!");
	}

	@SubCommand(name = "set", parent = "stack", permission = "tlc.commands.stack.set")
	public void onStackSetCommand(Player player, String[] args) {
		if (args.length < 2) {
			Players.sendMessage(player, Messages.invalidCommandUsage("stack size"));
			return;
		}

		Configuration configuration = StackedItems.getConfiguration();
		int stackSize = StringUtil.getNumberAt(args,1,configuration.getStackSize());
		configuration.setStackSize(stackSize);
		Players.sendMessage(player, String.format("&aMax stack size changed to &e%s",stackSize));
	}
}
