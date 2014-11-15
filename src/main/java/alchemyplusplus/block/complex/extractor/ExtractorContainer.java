package alchemyplusplus.block.complex.extractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ExtractorContainer extends Container
{

    private int extractingTicks = 0;
    private final ExtractorTileEntity extractor;
    private int fuel = 0;

    public ExtractorContainer(InventoryPlayer playerInv,
            ExtractorTileEntity extractor)
    {
        this.extractor = extractor;

        this.addSlotToContainer(new Slot(extractor, 0, 80, 13)); // ingridient
        this.addSlotToContainer(new Slot(extractor, 1, 67, 37)); // in
        this.addSlotToContainer(new Slot(extractor, 2, 94, 37)); // out
        this.addSlotToContainer(new Slot(extractor, 3, 67, 56)); // fuel

        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9,
                        8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }

    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0,
                this.extractor.extractingTicks);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.extractor.fuel);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (this.extractingTicks != this.extractor.extractingTicks)
            {
                icrafting.sendProgressBarUpdate(this, 0,
                        this.extractor.extractingTicks);
            }

            if (this.fuel != this.extractor.fuel)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.extractor.fuel);
            }
        }

        this.extractingTicks = this.extractor.extractingTicks;
        this.fuel = this.extractor.fuel;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        // null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            // merges the item into player inventory since its in the tileEntity
            if (slot < 9)
            {
                if (!this.mergeItemStack(stackInSlot, 0, 35, true))
                {
                    return null;
                }
            } // places it into the tileEntity is possible since its in the player
            // inventory
            else if (!this.mergeItemStack(stackInSlot, 0, 9, false))
            {
                return null;
            }

            if (stackInSlot.stackSize == 0)
            {
                slotObject.putStack(null);
            } else
            {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize)
            {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }

    @Override
    public void updateProgressBar(int id, int value)
    {
        if (id == 0)
        {
            this.extractor.extractingTicks = value;
        } else if (id == 1)
        {
            this.extractor.fuel = value;
        }

    }

}
