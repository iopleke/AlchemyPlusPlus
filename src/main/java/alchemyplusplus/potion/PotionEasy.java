package alchemyplusplus.potion;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;

public class PotionEasy extends Potion
{
	List effectsList;

	public PotionEasy(int id, boolean isNegativeEffect, int liquidColor)
	{
		super(id, isNegativeEffect, liquidColor);
	}

	public PotionEasy(ItemStack itemStack)
	{
		super(0, false, 0);
		this.effectsList = PotionHelper.getPotionEffects(itemStack.getItemDamage(), true);
	}

}
