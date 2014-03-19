package mokonaDesu.alchemypp.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDiffuser extends Container {

    private final TileEntityDiffuser diffuser;
    private int diffusingTicks = 0;
    private int fuel = 0;

    public ContainerDiffuser(InventoryPlayer playerInv,
            TileEntityDiffuser diffuser) {
        this.diffuser = diffuser;

        this.addSlotToContainer(new Slot(diffuser, 0, 18, 23)); // Byproduct
        this.addSlotToContainer(new Slot(diffuser, 1, 62, 23)); // input
        this.addSlotToContainer(new Slot(diffuser, 2, 113, 58)); // output
        this.addSlotToContainer(new Slot(diffuser, 3, 62, 58)); // fuel

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9,
                        8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }

    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0,
                this.diffuser.diffusingTicks);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.diffuser.fuel);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (this.diffusingTicks != this.diffuser.diffusingTicks) {
                icrafting.sendProgressBarUpdate(this, 0,
                        this.diffuser.diffusingTicks);
            }

            if (this.fuel != this.diffuser.fuel) {
                icrafting.sendProgressBarUpdate(this, 1, this.diffuser.fuel);
            }
        }

        this.diffusingTicks = this.diffuser.diffusingTicks;
        this.fuel = this.diffuser.fuel;
    }

    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            this.diffuser.diffusingTicks = value;
        } else if (id == 1) {
            this.diffuser.fuel = value;
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        // null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            // merges the item into player inventory since its in the tileEntity
            if (slot < 9) {
                if (!this.mergeItemStack(stackInSlot, 0, 35, true)) {
                    return null;
                }
            }
            // places it into the tileEntity is possible since its in the player
            // inventory
            else if (!this.mergeItemStack(stackInSlot, 1, 9, false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }

}
