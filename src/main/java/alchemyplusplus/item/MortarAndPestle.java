package alchemyplusplus.item;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.gui.CreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class MortarAndPestle extends Item
{

    private int tier;
    private IIcon[] icons;
    private final String iconName;

    public MortarAndPestle(String itemname)
    {
        super();
        this.iconName = AlchemyPlusPlus.ID + ":" + itemname;
        this.setUnlocalizedName(itemname);
        this.setCreativeTab(CreativeTab.APP_TAB);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[4];
        icons[0] = iconRegister.registerIcon(iconName + "Wooden");
        icons[1] = iconRegister.registerIcon(iconName + "Stone");
        icons[2] = iconRegister.registerIcon(iconName + "Iron");
        icons[3] = iconRegister.registerIcon(iconName + "Obsidian");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
        return icons[damage];
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i <= 3; ++i)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }
}
