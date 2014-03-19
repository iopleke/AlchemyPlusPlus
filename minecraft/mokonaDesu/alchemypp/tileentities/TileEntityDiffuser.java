package mokonaDesu.alchemypp.tileentities;

import mokonaDesu.alchemypp.items.ItemRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityDiffuser extends TileEntity implements IInventory {
    private ItemStack[] diffuserInventory = new ItemStack[4];

    public int diffusingTicks = 0;
    public int fuel = 0;
    public int burntotal = 1;

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public int getSizeInventory() {
        return this.diffuserInventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.diffuserInventory[slot];
    }

    @Override
    public String getInvName() {
        return "Diffuser";
    }

    @Override
    public boolean isInvNameLocalized() {
        return true;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.diffuserInventory[slot] != null) {
            ItemStack itemstack;

            if (this.diffuserInventory[slot].stackSize <= amount) {
                itemstack = this.diffuserInventory[slot];
                this.diffuserInventory[slot] = null;
                return itemstack;
            } else {
                itemstack = this.diffuserInventory[slot].splitStack(amount);

                if (this.diffuserInventory[slot].stackSize == 0) {
                    this.diffuserInventory[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.diffuserInventory[slot] != null) {
            ItemStack itemstack = this.diffuserInventory[slot];
            this.diffuserInventory[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.diffuserInventory[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord,
                this.zCoord, 1, nbtTag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        readFromNBT(packet.data);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.diffuserInventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
                    .tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.diffuserInventory.length) {
                this.diffuserInventory[b0] = ItemStack
                        .loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.diffusingTicks = par1NBTTagCompound.getShort("diffusingTicks");
        this.fuel = par1NBTTagCompound.getShort("fuel");

    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("diffusingTicks",
                (short) this.diffusingTicks);
        par1NBTTagCompound.setShort("fuel", (short) this.fuel);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.diffuserInventory.length; ++i) {
            if (this.diffuserInventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.diffuserInventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);

    }

    @Override
    public void updateEntity() {
        if (this.fuel == 0 && this.getStackInSlot(3) != null
                && this.getStackInSlot(1) != null
                && this.getStackInSlot(2) != null
                && this.getStackInSlot(2).getItemDamage() != 100) {

            this.fuel = TileEntityFurnace.getItemBurnTime(this
                    .getStackInSlot(3));
            this.burntotal = fuel;
            this.decrStackSize(3, 1);
        }
        this.fuel--;
        if (fuel < 0)
            fuel = 0;
    }

    public boolean isActive() {
        return this.fuel > 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 3) {
            if (TileEntityFurnace.getItemBurnTime(stack) > 0) {
                return true;
            }
        } else if (slot == 1) {
            if (stack.itemID == 17) {
                return true;
            }
        } else if (slot == 2) {
            if (stack.itemID == Item.glassBottle.itemID
                    || stack.itemID == ItemRegistry.appItemSpirit.itemID) {
                return true;
            }
        }
        return false;
    }
}
