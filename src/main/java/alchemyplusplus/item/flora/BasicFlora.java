package alchemyplusplus.item.flora;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.item.ItemBasic;

public class BasicFlora extends ItemBasic
{

    public BasicFlora(String itemname)
    {
        super(itemname);

        this.iconName = AlchemyPlusPlus.ID + ":flora/" + itemname;
    }

}
