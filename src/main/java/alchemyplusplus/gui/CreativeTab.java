package alchemyplusplus.gui;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.registry.BlockRegistry;
import alchemyplusplus.registry.ItemRegistry;
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

    public static CreativeTabs APP_TAB_FLORA = new CreativeTabs(AlchemyPlusPlus.ID + "flora")
    {
        @Override
        public Item getTabIconItem()
        {
            return ItemRegistry.pasteGrass;
        }
    };
}
