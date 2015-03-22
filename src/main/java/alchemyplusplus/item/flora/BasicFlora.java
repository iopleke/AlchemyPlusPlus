package alchemyplusplus.item.flora;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.registry.CreativeTabRegistry;
import alchemyplusplus.item.ItemBasic;

public class BasicFlora extends ItemBasic
{

    public BasicFlora(String itemname)
    {
        super(itemname);
        this.setCreativeTab(CreativeTabRegistry.APP_TAB_FLORA);

        this.iconName = AlchemyPlusPlus.ID + ":flora/" + itemname;
    }

}
