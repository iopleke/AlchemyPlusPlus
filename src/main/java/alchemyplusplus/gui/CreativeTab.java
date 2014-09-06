package alchemyplusplus.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab extends CreativeTabs
{
    @SideOnly(Side.CLIENT)
    private final Item tabIconItem = alchemyplusplus.items.ItemRegistry.appItemPotionBottle;

    public CreativeTab(String tabName)
    {
        super(tabName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        return this.tabIconItem;
    }
}
