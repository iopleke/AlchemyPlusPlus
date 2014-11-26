package alchemyplusplus.block.complex.distillery;

import alchemyplusplus.utility.MixingHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.Constants;

public class DistilleryTileEntity extends TileEntity implements IInventory
{

    private ItemStack[] inventory;
    public int burntotal = 1;
    public int distillingTicks = 0;
    public int fuel = 0;

    public DistilleryTileEntity()
    {
        this.inventory = new ItemStack[4];
    }

    @Override
    public int getSizeInventory()
    {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return this.inventory[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        this.inventory[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack stack = this.getStackInSlot(slot);

        if (stack != null)
        {
            if (stack.stackSize <= amount)
            {
                this.setInventorySlotContents(slot, null);
            } else
            {
                stack = stack.splitStack(amount);

                if (stack.stackSize == 0)
                {
                    this.setInventorySlotContents(slot, null);
                }
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack stack = this.getStackInSlot(slot);
        if (stack != null)
        {
            this.setInventorySlotContents(slot, null);
        }
        return stack;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public void closeInventory()
    {
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
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.inventory.length)
            {
                this.inventory[b0] = ItemStack
                        .loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.distillingTicks = par1NBTTagCompound.getShort("distillingTicks");
        this.fuel = par1NBTTagCompound.getShort("fuel");

    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("distillingTicks",
                (short) this.distillingTicks);
        par1NBTTagCompound.setShort("fuel", (short) this.fuel);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);

    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord,
                this.zCoord, 1, nbtTag);
    }

    @Override
    public String getInventoryName()
    {
        return "Distillery";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    public boolean isActive()
    {
        return this.fuel > 0;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        if (slot == 3)
        {
            if (TileEntityFurnace.getItemBurnTime(stack) > 0)
            {
                return true;
            }
        } else if (slot == 1)
        {
            if (stack.getItem() == Item.getItemFromBlock(Blocks.log) || stack.getItem() == Item.getItemFromBlock(Blocks.log2))
            {
                return true;
            }
        } else if (slot == 2)
        {
            if (stack.getItem() == Items.glass_bottle)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateEntity()
    {
        if (this.fuel == 0 && this.getStackInSlot(3) != null
                && this.getStackInSlot(1) != null
                && this.getStackInSlot(2) != null
                && this.getStackInSlot(2).getItemDamage() != 100)
        {

            this.fuel = TileEntityFurnace.getItemBurnTime(this
                    .getStackInSlot(3));
            this.burntotal = fuel;
            this.decrStackSize(3, 1);
        }

        if (fuel > 0)
        {
            if (MixingHandler.distillingPossible(this))
            {
                this.distillingTicks++;
            } else
            {
                this.distillingTicks = 0;
            }
            if (this.distillingTicks == 400)
            {
                MixingHandler.performDistillation(this);
                this.distillingTicks = 0;
            }
        } else
        {
            this.distillingTicks = 0;
        }
        this.fuel--;
        if (fuel < 0)
        {
            fuel = 0;
        }
    }
}
