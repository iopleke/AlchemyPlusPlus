package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.utility.ConfigManager;
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

public class DiffuserTileEntity extends TileEntity implements IInventory
{
    public int bottleColor;
    private final ItemStack[] diffuserInventory = new ItemStack[1];

    public int diffusingTicks = 0;

    public int fluidLevel;
    private boolean isDiffusing = false;

    public ItemStack potionStack = null;

    public boolean canDiffuse()
    {
        if (this.fluidLevel > 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public void closeInventory()
    {
        
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (this.diffuserInventory[slot] != null)
        {
            ItemStack itemstack;

            if (this.diffuserInventory[slot].stackSize <= amount)
            {
                itemstack = this.diffuserInventory[slot];
                this.diffuserInventory[slot] = null;
                return itemstack;
            } else
            {
                itemstack = this.diffuserInventory[slot].splitStack(amount);

                if (this.diffuserInventory[slot].stackSize == 0)
                {
                    this.diffuserInventory[slot] = null;
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

    public int getFluidLevel()
    {
        return this.fluidLevel;
    }

    @Override
    public String getInventoryName()
    {
        // @TODO - set blockname in initialization
        // return this.blockName;
        return "Diffuser";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public int getSizeInventory()
    {
        return this.diffuserInventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return this.diffuserInventory[slot];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.diffuserInventory[slot] != null)
        {
            ItemStack itemstack = this.diffuserInventory[slot];
            this.diffuserInventory[slot] = null;
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

    public boolean hasFluidLevel()
    {
        return this.fluidLevel > 0;
    }

    public boolean isDiffuserActive()
    {
        return isDiffusing;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        if (slot == 1)
        {
            if (stack.getItem() == Items.glass_bottle)
            {
                return true;
            }
        }
        return false;
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
    public void readFromNBT(NBTTagCompound diffuserNBTData)
    {
        super.readFromNBT(diffuserNBTData);
        NBTTagList nbttaglist = diffuserNBTData.getTagList("Items", Constants.NBT.TAG_COMPOUND);

        this.diffusingTicks = diffuserNBTData.getShort("diffusingTicks");
        this.fluidLevel = diffuserNBTData.getShort("fluidLevel");

        this.isDiffusing = diffuserNBTData.getBoolean("isDiffusing");

        potionStack = new ItemStack(Items.potionitem, 1, diffuserNBTData.getInteger("potionStackDamage"));
        bottleColor = diffuserNBTData.getInteger("bottleColor");

        // Without this check, diffusers give free water bottles
        if (potionStack.getItemDamage() == 0)
        {
            potionStack = null;
            bottleColor = 0;
        }
    }

    public void setBottleColorValue(int bottleColor)
    {
        this.bottleColor = bottleColor;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        this.diffuserInventory[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    public void toggleDiffusingState()
    {
        if (fluidLevel > 0)
        {
            isDiffusing = !isDiffusing;
        } else
        {
            isDiffusing = false;
        }
        if (ConfigManager.appDebugMode)
        {
            System.err.println("Fluid level:" + fluidLevel);
            System.err.println("Diffusing: " + isDiffusing);
        }
    }

    @Override
    public void updateEntity()
    {
        if (this.fluidLevel != 100 && this.getStackInSlot(0) != null)
        {
            // Check to see if it's a load-able fluid here
        }
        if (fluidLevel < 0)
        {
            fluidLevel = 0;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound diffuserNBTData)
    {
        super.writeToNBT(diffuserNBTData);

        diffuserNBTData.setShort("diffusingTicks", (short) this.diffusingTicks);
        diffuserNBTData.setShort("fluidLevel", (short) this.fluidLevel);
        diffuserNBTData.setBoolean("isDiffusing", this.isDiffusing);

        if (potionStack != null)
        {
            diffuserNBTData.setInteger("potionStackDamage", potionStack.getItemDamage());
        }

        if (bottleColor != 0)
        {
            diffuserNBTData.setInteger("bottleColor", bottleColor);
        }

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.diffuserInventory.length; ++i)
        {
            if (this.diffuserInventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.diffuserInventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        diffuserNBTData.setTag("Items", nbttaglist);

    }
}
