package alchemyplusplus.utility;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.reference.Settings;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigManager
{
    // Configuration variables
    public static Configuration appConfig;


    public static void init(File configFile)
    {
        if (appConfig == null)
        {
            appConfig = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {

        Settings.Debug.appDebugMode = appConfig.getBoolean("appDebugMode", Configuration.CATEGORY_GENERAL, false, "Enable Debug Mode");
        Settings.General.appHardcoreModeEnabled = appConfig.getBoolean("appHardcoreModeEnabled", Configuration.CATEGORY_GENERAL, false, "Enable Hardcore Mode");
        Settings.General.appVanillaBrewingOverride = appConfig.getBoolean("appVanillaBrewingOverride", Configuration.CATEGORY_GENERAL, false, "Override Vanilla brewing");
        Settings.General.appVanillaPotionOverride = appConfig.getBoolean("appVanillaPotionOverride", Configuration.CATEGORY_GENERAL, false, "Override Vanilla potions");


        if (appConfig.hasChanged())
        {
            appConfig.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(AlchemyPlusPlus.ID))
        {
            loadConfiguration();
        }
    }
}
