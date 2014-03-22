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

public class TileEntityDiffuser extends TileEntity implements IInventory {
    private ItemStack[] diffuserInventory = new ItemStack[1];

    public int diffusingTicks = 0;

    public int fluidLevel;
    private boolean isDiffusing;

    @Override
    public int getInventoryStackLimit() {
        return 1;
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
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
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
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.diffuserInventory.length) {
                this.diffuserInventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.diffusingTicks = par1NBTTagCompound.getShort("diffusingTicks");
        this.fluidLevel = par1NBTTagCompound.getShort("fluidLevel");

    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("diffusingTicks", (short) this.diffusingTicks);
        par1NBTTagCompound.setShort("fluidLevel", (short) this.fluidLevel);
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
        if (this.fluidLevel != 100 && this.getStackInSlot(0) != null) {
            // Check to see if it's a load-able fluid here
        }
        if (fluidLevel < 0) {
            fluidLevel = 0;
        }
    }

    public boolean getFluidLevel() {
        return this.fluidLevel > 0;
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
        if (slot == 1) {
            if (stack.itemID == Item.glassBottle.itemID || stack.itemID == ItemRegistry.appItemSpirit.itemID) {
                return true;
            }
        }
        return false;
    }

    public boolean isDiffuserActive() {
        return isDiffusing;
    }
}
