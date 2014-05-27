package com.caved_in.stackeditems;

import com.caved_in.commons.command.CommandController;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.stackeditems.commands.StackCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class StackedItems extends JavaPlugin {

	public static final int MAX_STACK_SIZE = 12;

	@Override
	public void onEnable() {
		initConfiguration();
	}

	@Override
	public void onDisable() {
		CommandController.registerCommands(this,new StackCommand());
		saveConfiguration();
		Plugins.unregisterHooks(this);
	}

	private void initConfiguration() {

	}

	private void saveConfiguration() {

	}
}
