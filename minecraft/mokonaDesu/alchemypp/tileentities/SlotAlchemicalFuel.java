package mokonaDesu.alchemypp.tileentities;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class SlotAlchemicalFuel extends Slot {

    public SlotAlchemicalFuel(IInventory inventory, int id, int x, int y) {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return GameRegistry.getFuelValue(stack) > 0;
    }

}
