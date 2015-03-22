package alchemyplusplus.registry;

import alchemyplusplus.AlchemyPlusPlus;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabRegistry
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
