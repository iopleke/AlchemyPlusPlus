package alchemyplusplus.gui;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.BlockRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab
{

    public static CreativeTabs APP_TAB = new CreativeTabs(AlchemyPlusPlus.ID)
    {
        @Override
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(BlockRegistry.potionJug);
        }
    };
}
