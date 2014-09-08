package alchemyplusplus.utility;

import alchemyplusplus.gui.CreativeTab;
import java.io.File;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class ConfigManager
{
    // Major thanks to micdoodle for configuration help

    // Defining the block ID variables
    public static int appBlockAlchemicalApparatus;
    public static int appBlockDiffuser;
    public static int appBlockDistillery;
    public static int appBlockExtractor;
    public static int appBlockFlesh;
    public static int appBlockLiquidMixer;
    public static int appBlockPotionContainer;
    // Configuration variables
    public static Configuration appConfig;
    private static boolean appConfigLoaded;
    public static CreativeTabs appCreativeTab;
    public static boolean appDebugMode;
    // General settings
    public static boolean appHardcoreModeEnabled;

    // Defining the item ID variables
    public static int appItemBottleStand;
    public static int appItemConfusion;
    public static int appItemDiffuser;
    public static int appItemDistillery;
    public static int appItemExtractor;
    public static int appItemFesteringFlesh;
    public static int appItemFilter;
    public static int appItemFishOil;
    public static int appItemIronPowder;
    public static int appItemLiquidMixer;
    public static int appItemPotionBottle;
    public static int appPotionWoodAlcohol;
    public static int appItemSpringyCord;
    public static int appItemSquidEye;

    public static boolean appVanillaBrewingOverride;
    public static boolean appVanillaPotionOverride;

    // Set the starting block and item IDs
    private static int dynamicBlockID = 400;
    private static int dynamicItemID = 2300;

    public static void initialSetup(File config)
    {

        if (!appConfigLoaded)
        {
            appConfig = new Configuration(config);
        }

        try
        {

            // And so it begins...
            appConfig.load();

            // Setting the general options
            appHardcoreModeEnabled = appConfig.get(Configuration.CATEGORY_GENERAL, "appHardcoreModeEnabled", false, "Enable hardcore game mechanic, making the game more challenging").getBoolean(false);
            appVanillaBrewingOverride = appConfig.get(Configuration.CATEGORY_GENERAL, "appVanillaBrewingOverride", false, "Enable this to override vanilla brewing mechanics").getBoolean(false);
            appVanillaPotionOverride = appConfig.get(Configuration.CATEGORY_GENERAL, "appVanillaPotionOverride", false, "Enable this to override vanilla potion mechanics").getBoolean(false);
            appDebugMode = appConfig.get(Configuration.CATEGORY_GENERAL, "appDebugMode", false, "Enable extra logging (May spam your console. You've been warned.)").getBoolean(false);

            // That wasn't so bad, now was it?
            setupCreativeTab();

        } catch (final Exception e)
        {
            System.err.println("Problem loading config");

        } finally
        {
            if (appConfig.hasChanged())
            {
                appConfig.save();
            }

            appConfigLoaded = true;
        }

    }

    public static void setupCreativeTab()
    {
        // Set the creative tab
        appCreativeTab = new CreativeTab("AlchemyPlusPlus");
    }
}
