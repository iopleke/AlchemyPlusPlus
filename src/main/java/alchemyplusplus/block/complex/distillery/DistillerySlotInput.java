package alchemyplusplus.block.complex.distillery;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DistillerySlotInput extends Slot
{

	public DistillerySlotInput(IInventory inventory, int id, int x, int y)
	{
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		// Set the valid items to be processed for this slot
		if (stack.getItem() == Item.getItemFromBlock(Blocks.log) || stack.getItem() == Item.getItemFromBlock(Blocks.log2))
		{
			return true;
		}
		return false;
	}

}
