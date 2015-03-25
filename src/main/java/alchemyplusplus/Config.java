package alchemyplusplus;

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

public class Config
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

    // Diffuser radius in active mode is multiplied by
    public static int DiffusingRadiusMultiplier = 2;

    // Base diffusion rate of fluid use
    public static int DiffusingRate = 20;

    // Diffuser in active mode rate multiplier
    public static int DiffusingRateMultiplier = 2;

    // Max number of effects per diffuser
    public static int EffectsLimit = 0;

    // Currently unused
    public static boolean HardcoreMode = false;

    //Should consume potion source blocks
    public static boolean consumeSourceBlocks = true;

    //Chance to consume a potion source block
    public static float consumeSourceBlocksChance = 0.33F;

    //Zombie stuff - don't worry your pretty little head about these
    public static boolean hostileAnimals = false;
    public static boolean zombieMode = true;
    public static int infectionTimer = 180;

    public static void init()
    {
        if (config == null)
        {
            config = new Configuration(new File("config/AlchemyPlusPlus.cfg"));
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
        config.addCustomCategoryComment(Config.CATEGORY_DIFFUSER, StatCollector.translateToLocal("config.diffuser.description"));
        config.addCustomCategoryComment(Config.CATEGORY_ZOMBIE, StatCollector.translateToLocal("config.zombie.description"));

        prop = config.get(Configuration.CATEGORY_GENERAL, "debug", Config.DebugMode);
        prop.comment = StatCollector.translateToLocal("config.debug.description");
        prop.setLanguageKey("config.debug.tooltip");
        DebugMode = prop.getBoolean();
        AlchemyPlusPlus.logger.debugMode = DebugMode;
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "potionBucketCrafting", Config.PotionBucketCrafting);
        prop.comment = StatCollector.translateToLocal("config.potionbucket.description");
        prop.setLanguageKey("config.potionbucket.tooltip");
        PotionBucketCrafting = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Config.CATEGORY_ZOMBIE, "zombieMode", Config.zombieMode);
        prop.comment = StatCollector.translateToLocal("config.zombieMode.description");
        prop.setLanguageKey("config.zombieMode.tooltip");
        zombieMode = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Config.CATEGORY_ZOMBIE, "hostileAnimals", Config.hostileAnimals);
        prop.comment = StatCollector.translateToLocal("config.hostileAnimals.description");
        prop.setLanguageKey("config.hostileAnimals.tooltip");
        hostileAnimals = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Config.CATEGORY_DIFFUSER, "diffusingRadius", Config.DiffusingRadius);
        prop.comment = StatCollector.translateToLocal("config.diffuser.radius.description");
        prop.setLanguageKey("config.diffuser.radius");
        DiffusingRadius = prop.getInt();
        configList.add(prop.getName());

        prop = config.get(Config.CATEGORY_DIFFUSER, "diffusingRadiusMultiplier", Config.DiffusingRadiusMultiplier);
        prop.comment = StatCollector.translateToLocal("config.diffuser.radius.multiplier.description");
        prop.setLanguageKey("config.diffuser.radius.multiplier");
        DiffusingRadiusMultiplier = prop.getInt();
        configList.add(prop.getName());

        prop = config.get(Config.CATEGORY_DIFFUSER, "diffusingRate", Config.DiffusingRate);
        prop.comment = StatCollector.translateToLocal("config.diffuser.rate.description");
        prop.setLanguageKey("config.diffuser.rate");
        DiffusingRate = prop.getInt();
        configList.add(prop.getName());

        prop = config.get(Config.CATEGORY_DIFFUSER, "diffusingRateMultiplier", Config.DiffusingRateMultiplier);
        prop.comment = StatCollector.translateToLocal("config.diffuser.rate.multiplier.description");
        prop.setLanguageKey("config.diffuser.rate.multiplier");
        DiffusingRateMultiplier = prop.getInt();
        configList.add(prop.getName());

        if (config.hasChanged())
        {
            config.save();
        }
    }

    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(config.getCategory(Config.CATEGORY_DIFFUSER)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Config.CATEGORY_ZOMBIE)).getChildElements());
        return list;
    }
}
