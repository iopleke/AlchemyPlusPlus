package alchemyplusplus.utility;

import alchemyplusplus.gui.CreativeTab;
import java.io.File;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;

public class ConfigManager
{
    // Configuration variables
    public static Configuration appConfig;
    private static boolean appConfigLoaded;
    public static boolean appDebugMode;
    
    // General settings
    public static boolean appHardcoreModeEnabled;

    public static boolean appVanillaBrewingOverride;
    public static boolean appVanillaPotionOverride;

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

}
