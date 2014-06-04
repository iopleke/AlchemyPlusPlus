package alchemyplusplus.tileentities;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDistilleryInput extends Slot {

    public SlotDistilleryInput(IInventory inventory, int id, int x, int y) {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        // Set the valid items to be processed for this slot
        if (stack.itemID == 17) {
            return true;
        } else {
            return false;

        }
    }

}
