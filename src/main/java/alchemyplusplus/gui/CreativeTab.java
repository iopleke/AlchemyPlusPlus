package alchemyplusplus.gui;

import alchemyplusplus.block.BlockRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTab extends CreativeTabs
{

    public CreativeTab(String tabName)
    {
        super(tabName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        return Item.getItemFromBlock(BlockRegistry.fleshBlock);
    }
}
