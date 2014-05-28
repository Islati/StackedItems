package com.caved_in.stackeditems;

import com.caved_in.commons.Commons;
import com.caved_in.commons.command.CommandController;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.stackeditems.commands.StackCommand;
import com.caved_in.stackeditems.config.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;

public class StackedItems extends JavaPlugin {
	private static final Serializer serializer = new Persister();

	private static Configuration config;
	private static String pluginLocation = "plugins/StackedItems/";
	private static String configLocation = "plugins/StackedItems/Config.xml";


	@Override
	public void onEnable() {
		if (Plugins.makeDataFolder(this)) {
			Commons.debug("Made default data folder for StackedItems");
		}

		initConfiguration();

		if (config == null) {
			config = new Configuration();
		}

		CommandController.registerCommands(this,new StackCommand());
	}

	@Override
	public void onDisable() {
		saveConfiguration();
		Plugins.unregisterHooks(this);
	}

	private void initConfiguration() {
		File configFile = new File(configLocation);
		if (!configFile.exists()) {
			try {
				if (configFile.createNewFile()) {
					Commons.debug("Created default config file for StackedItems");
				}
				serializer.write(new Configuration(),configFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			config = serializer.read(Configuration.class,configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void saveConfiguration() {
		File configFile = new File(configLocation);
		try {
			serializer.write(config,configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Configuration getConfiguration() {
		return config;
	}
}
