package alchemyplusplus.item;

import alchemyplusplus.AlchemyPlusPlus;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;

public class PotionBucket extends ItemBucket
{
	private String iconName;

	@SideOnly(Side.CLIENT)
	private IIcon potionBucket, potionBucketOverlay;
	private final Potion potion;

	public PotionBucket(Block block, Potion potion)
	{
		super(block);
		this.potion = potion;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.potionBucket = iconRegister.registerIcon(AlchemyPlusPlus.ID + ":potionBucket");
		this.potionBucketOverlay = iconRegister.registerIcon(AlchemyPlusPlus.ID + ":potionBucketOverlay");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass)
	{
		if (pass == 0)
		{
			return this.potionBucketOverlay;
		}
		return this.potionBucket;

	}

	@SideOnly(Side.CLIENT)
	public int getColorFromDamage(int damage)
	{
		return PotionHelper.func_77915_a(damage, false);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemStack, int pass)
	{
		return pass > 0 ? 16777215 : this.potion.getLiquidColor();
	}

}
