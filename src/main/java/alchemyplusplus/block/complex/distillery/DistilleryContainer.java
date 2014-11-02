package alchemyplusplus.block.complex.distillery;

import alchemyplusplus.block.OutputSlotBasic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class DistilleryContainer extends Container
{

	protected DistilleryTileEntity distillery;
	private int distillingTicks = 0;
	private int fuel = 0;

	public DistilleryContainer(InventoryPlayer playerInv, DistilleryTileEntity distillery)
	{
		this.distillery = distillery;

		this.addSlotToContainer(new OutputSlotBasic(distillery, 0, 18, 23)); // Byproduct
		this.addSlotToContainer(new DistillerySlotInput(distillery, 1, 62, 23)); // input
		this.addSlotToContainer(new DistillerySlotOutput(distillery, 2, 113, 58)); // output
		this.addSlotToContainer(new DistillerySlotFuel(distillery, 3, 62, 58)); // fuel

		this.bindPlayerInventory(playerInv);

	}

	private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
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
			if (slot < 4)
			{
				if (!this.mergeItemStack(stackInSlot, 4, inventorySlots.size(), true))
				{
					return null;
				}
			} // places it into the tileEntity if possible since its in the player
			// inventory
			else
			{
				//is the item a valid input (Log)
				if (distillery.isItemValidForSlot(1, stackInSlot))
				{
					if (!this.mergeItemStack(stackInSlot, 1, 2, true))
					{
						if (stackInSlot.stackSize == 0)
						{
							return null;
						}
					}
				}
				//is the item a valid output (Glass bottle)
				if (distillery.isItemValidForSlot(2, stackInSlot))
				{
					//if there isn't a bottle in there already
					if (!getSlot(2).getHasStack())
					{
						//put one in there.
						ItemStack copy = slotObject.decrStackSize(1);
						getSlot(2).putStack(copy);
						return null;
					} else
					{
						//if there is something in the slot do nothing.
						return null;
					}
				}
				//is the item a valid fuel....
				//and we didn't put it all in the input slot
				if (distillery.isItemValidForSlot(3, stackInSlot) && stackInSlot.stackSize > 0)
				{
					if (!this.mergeItemStack(stackInSlot, 3, 4, false))
					{
						return null;
					}
				}
				//if we couldn't fit any of it in the machine mimic vanilla
				//shift click mechanics.
				//put the item in the hot bar from inventory
				if (slot < 31 && stackInSlot.stackSize == stack.stackSize)
				{
					if (!this.mergeItemStack(stackInSlot, 31, 40, false))
					{
						return null;
					}

				}
				//put the item in the inventory from hot bar.
				if (slot > 30 && stackInSlot.stackSize == stack.stackSize)
				{
					if (!this.mergeItemStack(stackInSlot, 4, 30, false))
					{
						return null;
					}
				}
			}
			//if we moved the entire stack
			if (stackInSlot.stackSize == 0)
			{
				slotObject.putStack(null);
			} else //see if something changed
			{
				slotObject.onSlotChanged();
			}

			//if nothing changed
			if (stackInSlot.stackSize == stack.stackSize)
			{
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0,
				this.distillery.distillingTicks);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.distillery.fuel);
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

			if (this.distillingTicks != this.distillery.distillingTicks)
			{
				icrafting.sendProgressBarUpdate(this, 0,
						this.distillery.distillingTicks);
			}

			if (this.fuel != this.distillery.fuel)
			{
				icrafting.sendProgressBarUpdate(this, 1, this.distillery.fuel);
			}
		}

		this.distillingTicks = this.distillery.distillingTicks;
		this.fuel = this.distillery.fuel;
	}

	@Override
	public void updateProgressBar(int id, int value)
	{
		if (id == 0)
		{
			this.distillery.distillingTicks = value;
		} else if (id == 1)
		{
			this.distillery.fuel = value;
		}

	}
}
