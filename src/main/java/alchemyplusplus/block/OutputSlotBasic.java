package alchemyplusplus.block;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class OutputSlotBasic extends Slot
{

    public OutputSlotBasic(IInventory inventory, int slotID, int slotX, int slotY)
    {
        super(inventory, slotID, slotX, slotY);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }
}