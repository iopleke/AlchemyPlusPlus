package mokonaDesu.alchemypp;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class APPConfigurator {

    static Configuration alchemyPPConfig;

    public static void loadConfig(FMLPreInitializationEvent e) {
        alchemyPPConfig = new Configuration(e.getSuggestedConfigurationFile());
        alchemyPPConfig.load();

        Property potionOverride = alchemyPPConfig.get(
                Configuration.CATEGORY_GENERAL, "potionOverride", false);
        AlchemyPP.potionOverride = potionOverride.getBoolean(false);
        potionOverride.comment = "Enable overriding base Potion class with Alchemy++ potion class."
                + " Required for \"charged\" potion throw. "
                + " !!! IT IS WIP, SO DON'T EVEN BOTHER TURNING IT ON JUST YET !!!";

        Property hardcoreAlchemy = alchemyPPConfig.get(
                Configuration.CATEGORY_GENERAL, "hardcoreAlchemy", false);
        AlchemyPP.hardcoreAlchemy = hardcoreAlchemy.getBoolean(false);
        hardcoreAlchemy.comment = "Enable hardcore recipes for some of the brewing stuff to make"
                + " the game more challenging. Requires "
                + "modifying vanilla recipes. (Best expirience achieved"
                + " with naturalRegeneration - OFF gamerule)";

        APPIDManager.setup(alchemyPPConfig);

        alchemyPPConfig.save();
    }

    public static void loadHerbsConfig(FMLPreInitializationEvent e) {
        AlchemyPP.herbs = true;
        APPIDManager.setupHerbs(alchemyPPConfig);

        alchemyPPConfig.save();
    }

}
