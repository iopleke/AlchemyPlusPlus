package alchemyplusplus.item.flora;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.registry.CreativeTabRegistry;
import jakimbox.prefab.item.BasicItem;

public class BasicFlora extends BasicItem
{

    public BasicFlora(String itemname)
    {
        super(AlchemyPlusPlus.ID, itemname);
        this.setCreativeTab(CreativeTabRegistry.APP_TAB_FLORA);

        this.iconString = AlchemyPlusPlus.ID + ":flora/" + itemname;
    }

}
