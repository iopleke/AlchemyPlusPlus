package mokonaDesu.alchemypp;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class APPConfigurator {

    static Configuration alchemyPPConfig;

    public static void loadConfig(FMLPreInitializationEvent e) {
        alchemyPPConfig = new Configuration(e.getSuggestedConfigurationFile());
        alchemyPPConfig.load();

        Property potionOverride = alchemyPPConfig.get(Configuration.CATEGORY_GENERAL, "potionOverride", false);
        AlchemyPP.potionOverride = potionOverride.getBoolean(false);
        potionOverride.comment = "Enable overriding base Potion class with Alchemy++ potion class.";

        Property hardcoreAlchemy = alchemyPPConfig.get(Configuration.CATEGORY_GENERAL, "hardcoreAlchemy", false);
        AlchemyPP.hardcoreAlchemy = hardcoreAlchemy.getBoolean(false);
        hardcoreAlchemy.comment = "Enable hardcore game mechanic, making the game more challenging";

        APPIDManager.setup(alchemyPPConfig);

        alchemyPPConfig.save();
    }

}
