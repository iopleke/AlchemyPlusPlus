package alchemyplusplus.block.complex.extractor;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ExtractorTileEntity //extends TileEntity implements IInventory
{

    public int extractingTicks = 0;
    private ItemStack[] extractorInventory = new ItemStack[4];
    public int fuel = 0;

    public ItemStack decrStackSize(int slot, int amount)
    {
        if (this.extractorInventory[slot] != null)
        {
            ItemStack itemstack;

            if (this.extractorInventory[slot].stackSize <= amount)
            {
                itemstack = this.extractorInventory[slot];
                this.extractorInventory[slot] = null;
                return itemstack;
            } else
            {
                itemstack = this.extractorInventory[slot].splitStack(amount);

                if (this.extractorInventory[slot].stackSize == 0)
                {
                    this.extractorInventory[slot] = null;
                }

                return itemstack;
            }
        } else
        {
            return null;
        }
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        if (slot == 3)
        {
            if (stack.getItem() == Items.blaze_powder)
            {
                return true;
            }
            return false;
        }

        return true;
    }

}
