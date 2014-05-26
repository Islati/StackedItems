package com.caved_in.stackeditems;

import com.caved_in.commons.plugin.Plugins;
import org.bukkit.plugin.java.JavaPlugin;

public class StackedItems extends JavaPlugin {

	public static final int MAX_STACK_SIZE = 12;

	@Override
	public void onEnable() {
		initConfiguration();
	}

	@Override
	public void onDisable() {
		saveConfiguration();
		Plugins.unregisterHooks(this);
	}

	private void initConfiguration() {

	}

	private void saveConfiguration() {

	}
}
