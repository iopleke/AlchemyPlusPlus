package mokonaDesu.alchemypp.tileentities;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotAlchemicalFuel extends Slot {

	public SlotAlchemicalFuel(IInventory inventory, int id, int x,
			int y) {
		super(inventory, id, x, y);
	}

    public boolean isItemValid(ItemStack stack)
    {
        return stack.itemID == Item.blazePowder.itemID;
    }

	
}
