package alchemyplusplus.potion;

import alchemyplusplus.AlchemyPlusPlus;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
	public int fluidColor = 0;
	private ItemPotion potion;
	public List potionEffects;
	private int itemDamage;

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
		super(PotionFluid.getPotionNameFromItemStack(itemStack).substring(0, (PotionFluid.getPotionNameFromItemStack(itemStack).indexOf(" ") > 0 ? PotionFluid.getPotionNameFromItemStack(itemStack).indexOf(" ") : PotionFluid.getPotionNameFromItemStack(itemStack).length())));
		this.potion = (ItemPotion) itemStack.getItem();
		this.itemDamage = itemStack.getItemDamage();
		this.potionEffects = ((ItemPotion) itemStack.getItem()).getEffects(itemStack);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			this.fluidColor = ((ItemPotion) itemStack.getItem()).getColorFromDamage(itemStack.getItemDamage());
		} else
		{
			this.fluidColor = 0;
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColor()
	{
		if (this.potion != null)
		{
			return potion.getColorFromDamage(this.itemDamage);
		}
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
