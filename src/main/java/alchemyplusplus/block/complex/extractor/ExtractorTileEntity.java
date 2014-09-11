package alchemyplusplus.block.complex.extractor;

import alchemyplusplus.utility.MixingHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class ExtractorTileEntity extends TileEntity implements IInventory
{

    public int extractingTicks = 0;
    private ItemStack[] extractorInventory = new ItemStack[4];
    public int fuel = 0;

    @Override
    public void closeInventory()
    {
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (this.extractorInventory[slot] != null)
        {
            ItemStack itemstack;

            if (this.extractorInventory[slot].stackSize <= amount)
            {
                itemstack = this.extractorInventory[slot];
                this.extractorInventory[slot] = null;
                return itemstack;
            } else
            {
                itemstack = this.extractorInventory[slot].splitStack(amount);

                if (this.extractorInventory[slot].stackSize == 0)
                {
                    this.extractorInventory[slot] = null;
                }

                return itemstack;
            }
        } else
        {
            return null;
        }
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    @Override
    public String getInventoryName()
    {
        return "Extractor";
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public int getSizeInventory()
    {
        return this.extractorInventory.length;
    }

    public ItemStack getStackInSlot(int slot)
    {
        return this.extractorInventory[slot];
    }

    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.extractorInventory[slot] != null)
        {
            ItemStack itemstack = this.extractorInventory[slot];
            this.extractorInventory[slot] = null;
            return itemstack;
        } else
        {
            return null;
        }
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isActive()
    {
        return this.fuel > 0;
    }

    public boolean isInvNameLocalized()
    {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        if (slot == 3)
        {
            if (stack.getItem() == Items.blaze_powder)
            {
                return true;
            }
            return false;
        }

        return true;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public void openInventory()
    {
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        this.extractorInventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.extractorInventory.length)
            {
                this.extractorInventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.extractingTicks = par1NBTTagCompound.getShort("extractingTicks");
        this.fuel = par1NBTTagCompound.getShort("fuel");

    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        this.extractorInventory[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public void updateEntity()
    {
        if (this.fuel == 0 && this.getStackInSlot(3) != null)
        {
            this.fuel = 400;
            this.decrStackSize(3, 1);
        }

        if (fuel > 0)
        {
            if (MixingHelper.extractingPossible(this))
            {
                this.extractingTicks++;

            } else
            {
                this.extractingTicks = 0;
            }

            if (this.extractingTicks == 400)
            {
                MixingHelper.performExtraction(this);
                this.extractingTicks = 0;
            }
        } else
        {
            this.extractingTicks = 0;
        }
        this.fuel--;
        if (fuel < 0)
        {
            fuel = 0;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("extractingTicks", (short) this.extractingTicks);
        par1NBTTagCompound.setShort("fuel", (short) this.fuel);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.extractorInventory.length; ++i)
        {
            if (this.extractorInventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.extractorInventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);

    }

}