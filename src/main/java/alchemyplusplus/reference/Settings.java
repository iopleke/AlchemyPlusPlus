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

	public static String CATEGORY_DIFFUSER = "diffuser";

	// Determines if the mod will print debug info
	public static boolean DebugMode = false;

	// Override vanilla potion brewing mechanics
	public static boolean BrewingOverride = false;

	// Base diffusion radius
	public static int DiffusingRadius = 20;

	// Max number of effects per diffuser
	public static int EffectsLimit = 0;

	// Currently unused
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
		config.addCustomCategoryComment(Settings.CATEGORY_DIFFUSER, StatCollector.translateToLocal("config.diffuser.description"));

		prop = config.get(Configuration.CATEGORY_GENERAL, "debug", Settings.DebugMode);
		prop.comment = StatCollector.translateToLocal("config.debug.description");
		prop.setLanguageKey("config.debug.tooltip");
		DebugMode = prop.getBoolean();
		configList.add(prop.getName());

		prop = config.get(Settings.CATEGORY_DIFFUSER, "diffusingRadius", Settings.DiffusingRadius);
		prop.comment = StatCollector.translateToLocal("config.diffuser.radius.description");
		prop.setLanguageKey("config.diffuser.radius");
		DiffusingRadius = prop.getInt();
		configList.add(prop.getName());

		if (config.hasChanged())
		{
			config.save();
		}
	}

	public static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(config.getCategory(Settings.CATEGORY_DIFFUSER)).getChildElements());
		list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		return list;
	}
}
