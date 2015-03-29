package alchemyplusplus.item.flora;

import alchemyplusplus.AlchemyPlusPlus;
import jakimbox.prefab.item.BasicItem;

public class BasicFlora extends BasicItem
{

    public BasicFlora(String itemname)
    {
        super(AlchemyPlusPlus.ID, itemname);

        iconString = AlchemyPlusPlus.ID + ":flora/" + itemname;
    }

}
