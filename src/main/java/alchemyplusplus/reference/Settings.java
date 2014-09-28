package alchemyplusplus.reference;

import alchemyplusplus.AlchemyPlusPlus;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Settings
{
	// Config file
	public static Configuration config;

	// Determines if the mod will print debug info
	public static boolean DebugMode = false;

	// Override vanilla potion brewing mechanics
	public static boolean BrewingOverride = false;

	public static boolean HardcoreMode = false;

	public static void init(File configFile)
	{
		if (config == null)
		{
			config = new Configuration(configFile);
			loadConfig();
		}
	}

	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.modID.equalsIgnoreCase(AlchemyPlusPlus.ID))
		{
			loadConfig();
		}
	}

	private static void loadConfig()
	{
		Property prop;
		List<String> configList = new ArrayList<String>();

		config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, StatCollector.translateToLocal("config.general.description"));

		prop = config.get(Configuration.CATEGORY_GENERAL, "debug", Settings.DebugMode);
		prop.comment = StatCollector.translateToLocal("config.debug.description");
		prop.setLanguageKey("config.debug.tooltip");
		DebugMode = prop.getBoolean();
		configList.add(prop.getName());

		if (config.hasChanged())
		{
			config.save();
		}
	}

	public static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		return list;
	}
}
