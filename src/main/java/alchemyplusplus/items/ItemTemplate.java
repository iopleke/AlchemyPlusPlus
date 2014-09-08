package alchemyplusplus.items;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.utility.ConfigManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTemplate extends Item
{

    String iconName;
    int lifespan = 6000;

    public ItemTemplate(String itemname)
    {
        super();
        this.iconName = AlchemyPlusPlus.ID + ":" + itemname;
        this.setUnlocalizedName(itemname);
        this.setCreativeTab(ConfigManager.appCreativeTab);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return this.lifespan;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon(iconName);
    }

    public void setLifespan(int lifespan)
    {
        this.lifespan = lifespan;
    }

}
