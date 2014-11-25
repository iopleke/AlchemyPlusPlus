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

    public static final String CATEGORY_DIFFUSER = "diffuser";

    public static final String CATEGORY_ZOMBIE = "zombiesplusplus";

    // Determines if the mod will print debug info
    public static boolean DebugMode = false;

    // Override vanilla potion brewing mechanics
    public static boolean BrewingOverride = false;

    // Allow players to craft potion buckets
    public static boolean PotionBucketCrafting = true;

    // Base diffusion radius
    public static int DiffusingRadius = 20;

    // Base diffusion rate of fluid use
    public static int DiffusingRate = 10;

    // Max number of effects per diffuser
    public static int EffectsLimit = 0;

    // Currently unused
    public static boolean HardcoreMode = false;

    // @TODO - beat @hilburn with a dead fish for not commenting on these
    public static boolean hostileAnimals = false;
    public static boolean zombieMode = true;
    public static int infectionTimer = 180;

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
        config.addCustomCategoryComment(Settings.CATEGORY_ZOMBIE, StatCollector.translateToLocal("config.zombie.description"));

        prop = config.get(Configuration.CATEGORY_GENERAL, "debug", Settings.DebugMode);
        prop.comment = StatCollector.translateToLocal("config.debug.description");
        prop.setLanguageKey("config.debug.tooltip");
        DebugMode = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "potionBucketCrafting", Settings.PotionBucketCrafting);
        prop.comment = StatCollector.translateToLocal("config.potionbucket.description");
        prop.setLanguageKey("config.potionbucket.tooltip");
        PotionBucketCrafting = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Settings.CATEGORY_ZOMBIE, "zombieMode", Settings.zombieMode);
        prop.comment = StatCollector.translateToLocal("config.zombieMode.description");
        prop.setLanguageKey("config.zombieMode.tooltip");
        zombieMode = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Settings.CATEGORY_ZOMBIE, "hostileAnimals", Settings.hostileAnimals);
        prop.comment = StatCollector.translateToLocal("config.hostileAnimals.description");
        prop.setLanguageKey("config.hostileAnimals.tooltip");
        hostileAnimals = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Settings.CATEGORY_DIFFUSER, "diffusingRadius", Settings.DiffusingRadius);
        prop.comment = StatCollector.translateToLocal("config.diffuser.radius.description");
        prop.setLanguageKey("config.diffuser.radius");
        DiffusingRadius = prop.getInt();
        configList.add(prop.getName());

        prop = config.get(Settings.CATEGORY_DIFFUSER, "diffusingRate", Settings.DiffusingRate);
        prop.comment = StatCollector.translateToLocal("config.diffuser.rate.description");
        prop.setLanguageKey("config.diffuser.rate");
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
        list.addAll(new ConfigElement(config.getCategory(Settings.CATEGORY_ZOMBIE)).getChildElements());
        return list;
    }
}
