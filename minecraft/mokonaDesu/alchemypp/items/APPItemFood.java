package mokonaDesu.alchemypp.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;

public class APPItemFood extends ItemFood {

	String icon;
	
	public APPItemFood(int id, String icon, int effectID, int amp, int duration) {
		super(id, -5, -0.5f, false);
		this.setPotionEffect(effectID, duration, amp, 1f);
		this.icon = icon;
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(icon);
	}

}
