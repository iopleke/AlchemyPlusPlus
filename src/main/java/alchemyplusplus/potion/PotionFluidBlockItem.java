package alchemyplusplus.potion;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class PotionFluidBlockItem extends ItemBlock
{
	public PotionFluidBlockItem(Block block)
	{
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack p_77653_1_)
	{
		return this.field_150939_a.getLocalizedName();
	}

	@Override
	public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_)
	{
		return this.field_150939_a.getRenderColor(p_82790_2_);
	}
}
