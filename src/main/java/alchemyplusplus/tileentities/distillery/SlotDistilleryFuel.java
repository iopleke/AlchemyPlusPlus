package alchemyplusplus.tileentities.distillery;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotDistilleryFuel extends Slot
{

    public SlotDistilleryFuel(IInventory inventory, int id, int x, int y)
    {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return TileEntityFurnace.getItemBurnTime(stack) > 0;
    }

}
