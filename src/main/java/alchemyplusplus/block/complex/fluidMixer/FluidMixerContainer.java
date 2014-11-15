package alchemyplusplus.block.complex.fluidMixer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FluidMixerContainer extends Container
{

    private final FluidMixerTileEntity mixer;
    private int mixingTicks = 0;

    public FluidMixerContainer(InventoryPlayer playerInv,
            FluidMixerTileEntity mixer)
    {
        this.mixer = mixer;

        this.addSlotToContainer(new Slot(mixer, 0, 53, 19)); // 1st potion
        this.addSlotToContainer(new Slot(mixer, 1, 53, 45)); // 2nd potion
        this.addSlotToContainer(new Slot(mixer, 2, 80, 32)); // filter
        this.addSlotToContainer(new Slot(mixer, 3, 107, 19)); // result 1
        this.addSlotToContainer(new Slot(mixer, 4, 107, 45)); // result 2

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
        par1ICrafting.sendProgressBarUpdate(this, 0, this.mixer.mixingTicks);
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

            if (this.mixingTicks != this.mixer.mixingTicks)
            {
                icrafting
                        .sendProgressBarUpdate(this, 0, this.mixer.mixingTicks);
            }
        }

        this.mixingTicks = this.mixer.mixingTicks;
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
            this.mixer.mixingTicks = value;
        }
    }

}
