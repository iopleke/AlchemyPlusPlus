package alchemyplusplus.item;

import alchemyplusplus.AlchemyPlusPlus;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class MortarAndPestle extends ItemBasic
{

    private int tier;
    private IIcon[] icons;

    public MortarAndPestle(String itemname)
    {
        super(itemname);
        this.iconName = AlchemyPlusPlus.ID + ":" + itemname;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[2];
        icons[0] = iconRegister.registerIcon(iconName + "Wooden");
        icons[1] = iconRegister.registerIcon(iconName + "Stone");

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIIconFromDamage(int damage)
    {
        return icons[damage];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(int itemID, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < 1; ++i)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }
}
