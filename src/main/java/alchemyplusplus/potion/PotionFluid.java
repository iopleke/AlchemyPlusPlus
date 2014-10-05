package alchemyplusplus.potion;

import alchemyplusplus.AlchemyPlusPlus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class PotionFluid extends Fluid
{
	public int fluidColor;
	public List potionEffects;

	public PotionFluid(Potion potion)
	{
		super(potion.getName());
		this.potionEffects = new ArrayList();
		this.fluidColor = potion.getLiquidColor();
		PotionEffect effect = new PotionEffect(potion.getId(), 40, 0);
		this.potionEffects.add(effect);
	}

	public PotionFluid(ItemStack itemStack)
	{
		super(PotionFluid.getPotionNameFromItemStack(itemStack));
		if (itemStack.getItem() instanceof ItemPotion)
		{
			this.potionEffects = ((ItemPotion) itemStack.getItem()).getEffects(itemStack);
			this.fluidColor = ((ItemPotion) itemStack.getItem()).getColorFromDamage(itemStack.getItemDamage());
		}
	}

	@Override
	public int getColor()
	{
		return this.fluidColor;
	}

	public int getPotionIDFromItemStack(ItemStack itemStack)
	{
		if (itemStack != null)
		{
			return FluidRegistry.getFluidID(PotionFluid.getPotionNameFromItemStack(itemStack));
		}
		return 0;
	}

	public void addPotionEffect(PotionEffect effect)
	{
		this.potionEffects.add(effect);
	}

	public void addAllPotionEffects(ItemStack itemStack)
	{
		if (itemStack.getItem() instanceof ItemPotion)
		{
			this.potionEffects = ((ItemPotion) itemStack.getItem()).getEffects(itemStack);
		}
	}

	public void listFluidEffects()
	{
		if (this.potionEffects != null)
		{
			Iterator ite = this.potionEffects.iterator();
			while (ite.hasNext())
			{
				AlchemyPlusPlus.LOGGER.info("Entry: " + ite.next().toString());
			}
		}
	}

	public static String getPotionNameFromItemStack(ItemStack itemStack)
	{
		if (itemStack != null)
		{
			Item item = itemStack.getItem();
			if (item != null)
			{
				List potionEffects = PotionHelper.getPotionEffects(itemStack.getItemDamage(), false);
				if (potionEffects != null)
				{
					Iterator iter = potionEffects.iterator();
					if (iter.hasNext())
					{
						String fullEffect = iter.next().toString();
						if (fullEffect != null)
						{
							String[] effectArray = fullEffect.split(",");
							if (effectArray[0] != null)
							{
								return effectArray[0];
							}
						}
					}
				}
			}
		}
		return null;
	}
}
