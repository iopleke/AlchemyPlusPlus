package alchemyplusplus.tileentities.distillery;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDistilleryOutput extends Slot
{

    public SlotDistilleryOutput(IInventory inventory, int id, int x, int y)
    {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        // Set the valid items to be processed for this slot
        if (stack.getItem() == Items.glass_bottle)
        {
            return true;
        }
        return false;

    }

}
