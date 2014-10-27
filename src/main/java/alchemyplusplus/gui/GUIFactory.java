/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alchemyplusplus.gui;

import cpw.mods.fml.client.IModGuiFactory;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

/**
 *
 * @author jakimfett
 */
public class GUIFactory implements IModGuiFactory
{

	@Override
	public void initialize(Minecraft minecraftInstance)
	{

	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return GUIConfig.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		return null;
	}

}
