package alchemyplusplus.block.complex.distillery;

import alchemyplusplus.helper.MixingHelper;
import alchemyplusplus.reference.Naming;
import jakimbox.prefab.tileEntity.BasicInventoryTileEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class DistilleryTileEntity extends BasicInventoryTileEntity implements IInventory
{

    public int burntotal = 1;
    public int distillingTicks = 0;
    public int fuel = 0;

    public DistilleryTileEntity()
    {
        super(Naming.Blocks.DIFFUSER, 4);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.distillingTicks = nbt.getShort("distillingTicks");
        this.fuel = nbt.getShort("fuel");

    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setShort("distillingTicks", (short) this.distillingTicks);
        nbt.setShort("fuel", (short) this.fuel);

    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
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
            if (MixingHelper.distillingPossible(this))
            {
                this.distillingTicks++;
            } else
            {
                this.distillingTicks = 0;
            }
            if (this.distillingTicks == 400)
            {
                MixingHelper.performDistillation(this);
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
