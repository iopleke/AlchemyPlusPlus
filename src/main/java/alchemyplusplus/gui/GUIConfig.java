package alchemyplusplus.gui;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.reference.Settings;
import static alchemyplusplus.reference.Settings.config;
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
                Settings.getConfigElements(),
                AlchemyPlusPlus.ID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(config.toString()));
    }
}
