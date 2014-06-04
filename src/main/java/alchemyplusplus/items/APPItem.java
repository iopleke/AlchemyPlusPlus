package alchemyplusplus.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class APPItem extends Item {

	String iconName;
	int    lifespan = 6000;
	
	public APPItem(int id, String iconName) {
		super(id);
		this.iconName = iconName;
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(iconName);
	}

	public void setLifespan(int lifespan) {
		this.lifespan = lifespan;
	}
	
	@Override
    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return this.lifespan;
    }
	
}
