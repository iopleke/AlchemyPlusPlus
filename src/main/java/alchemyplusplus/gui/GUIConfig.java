package alchemyplusplus.gui;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.Config;
import static alchemyplusplus.Config.config;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;

/**
 *
 * @author jakimfett
 */
public class GUIConfig extends GuiConfig
{

    public GUIConfig(GuiScreen guiScreen)
    {
        super(guiScreen,
                Config.getConfigElements(),
                AlchemyPlusPlus.ID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(config.toString()));
    }
}
