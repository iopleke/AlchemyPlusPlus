package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.Config;
import alchemyplusplus.network.MessageHandler;
import alchemyplusplus.network.message.DiffuserUpdateMessage;
import alchemyplusplus.potion.fluid.PotionFluid;
import alchemyplusplus.potion.fluid.PotionFluidStack;
import alchemyplusplus.potion.fluid.PotionFluidTank;
import alchemyplusplus.reference.Naming;
import cpw.mods.fml.common.network.NetworkRegistry;
import jakimbox.prefab.tileEntity.BasicTileEntity;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class DiffuserTileEntity extends BasicTileEntity implements IFluidHandler, IFluidTank
{

    private boolean updateState = false;
    public short diffusingTicks = 0;
    public PotionFluidTank fluidTank;
    public boolean isDiffusing = false;

    public DiffuserTileEntity()
    {
        super(Naming.Blocks.DIFFUSER);
        this.fluidTank = new PotionFluidTank((int) 333);
    }

    private void applyPotionEffects()
    {

        // Iterate through all instances of EntityPlayer in the area
        Iterator players = getPlayersInsideEffectRadius().iterator();
        EntityPlayer entityplayer;
        while (players.hasNext())
        {
            // Get the next EntityPlayer
            entityplayer = (EntityPlayer) players.next();

            // Iterate through all potion effects, applying each one to each EntityPlayer
            Iterator potionEffects = this.fluidTank.potionEffects.iterator();

            while (potionEffects.hasNext())
            {
                int potionID = ((PotionEffect) potionEffects.next()).getPotionID();
                int duration = Config.DiffusingRate * Config.DiffusingRateMultiplier;
                if (potionID != 0)
                {
                    entityplayer.addPotionEffect(new PotionEffect(potionID, duration + 2));
                }
            }
        }

    }

    private List getPlayersInsideEffectRadius()
    {
        // Get the bounding box for the diffusing range
        AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox((double) xCoord, (double) yCoord, (double) zCoord, (double) (xCoord + 1), (double) (yCoord + 1), (double) (zCoord + 1));
        if (this.isDiffuserHeated())
        {
            boundingBox.expand(Config.DiffusingRadiusActive, Config.DiffusingRadiusActive, Config.DiffusingRadiusActive);
        } else
        {
            boundingBox.expand(Config.DiffusingRadius, Config.DiffusingRadius, Config.DiffusingRadius);
        }
        boundingBox.maxY = (double) this.worldObj.getHeight();
        return this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox);
    }

    private boolean isDiffuserHeated()
    {
        Block belowBlock = this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord);
        if (belowBlock != null)
        {
            // @TODO - check for lava, blocks on fire, etc
            if (belowBlock.equals(Blocks.lit_furnace))
            {
                return true;
            }
        }
        return false;
    }

    private void printPotionEffects()
    {
        if (getFluid() != null)
        {
            if (getFluid().getFluid() instanceof PotionFluid)
            {
                if (((PotionFluid) this.getFluid().getFluid()).potionEffects != null)
                {
                    Iterator iter = ((PotionFluid) this.getFluid().getFluid()).potionEffects.iterator();
                    while (iter.hasNext())
                    {
                        AlchemyPlusPlus.logger.debug("Effects: " + iter.next().toString());
                    }
                }

            }
        }
    }

    public boolean canDiffuse()
    {
        if (this.fluidTank.getFluidAmount() > 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        if (this.fluidTank != null && this.fluidTank.getFluidAmount() > 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        if (this.fluidTank != null && this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity())
        {
            return true;
        }
        return false;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        if (maxDrain > 0)
        {
            return this.fluidTank.drain(maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource != null)
        {
            return this.fluidTank.drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return this.fluidTank.drain(maxDrain, doDrain);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        if (resource != null)
        {
            return this.fluidTank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if (resource != null)
        {
            return this.fluidTank.fill(resource, doFill);
        }
        return 0;
    }

    public int fillWithOverRide(ItemStack heldItem)
    {
        if (heldItem != null)
        {
            PotionFluid potionFluid = new PotionFluid(heldItem);
            this.fluidTank = new PotionFluidTank(new PotionFluidStack(potionFluid, 333), 333);
            fluidTank.potionDamageValue = heldItem.getItemDamage();
            fluidTank.potionID = ItemPotion.getIdFromItem(heldItem.getItem());
            this.updateState = true;
            return this.fluidTank.getFluidAmount();
        }
        return 0;
    }

    @Override
    public int getCapacity()
    {

        if (this.fluidTank != null)
        {
            return this.fluidTank.getCapacity();
        }
        return 0;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        this.writeToNBT(new NBTTagCompound());
        return MessageHandler.INSTANCE.getPacketFrom(new DiffuserUpdateMessage(this));
    }

    @Override
    public FluidStack getFluid()
    {
        if (this.fluidTank != null)
        {
            return this.fluidTank.getFluid();
        }
        return null;

    }

    @Override
    public int getFluidAmount()
    {

        if (this.fluidTank != null)
        {
            return this.fluidTank.getFluidAmount();
        }
        return 0;
    }

    @Override
    public FluidTankInfo getInfo()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isDiffuserActive()
    {
        return isDiffusing;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        diffusingTicks = nbt.getShort("diffusingTicks");
        isDiffusing = nbt.getBoolean("isDiffusing");

        if (this.fluidTank != null)
        {
            fluidTank.readFromNBT(nbt.getCompoundTag("diffuserTank"));
        }
    }

    public void resetDiffuser()
    {
        diffusingTicks = 0;
        fluidTank = new PotionFluidTank((int) 333);
        isDiffusing = false;
        updateState = true;
    }

    public void setDiffusingState(boolean value)
    {
        isDiffusing = value;
        updateState = true;
    }

    public void syncFluidAmountAt(int amount, int fluidID)
    {
        if (this.fluidTank != null)
        {
            if (this.getFluid() != null)
            {
                if (this.getFluidAmount() > amount)
                {
                    this.drain(this.getFluidAmount() - amount, true);
                } else if (this.getFluidAmount() < amount)
                {
                    this.fill(new FluidStack(this.getFluid(), amount - this.getFluidAmount()), true);
                }
            } else
            {
                FluidStack tankFluid = new FluidStack(fluidID, amount);
                this.fill(tankFluid, true);
            }
        }
        this.updateState = false;
    }

    public void toggleDiffusingState()
    {
        AlchemyPlusPlus.logger.debug("Fluid level:" + this.fluidTank.getFluidAmount());
        AlchemyPlusPlus.logger.debug("Diffusing: " + isDiffusing);
        printPotionEffects();

        if (this.fluidTank.getFluidAmount() > 0)
        {
            isDiffusing = !isDiffusing;
        } else
        {
            isDiffusing = false;
        }
        updateState = true;
    }

    @Override
    public void updateEntity()
    {
        if (this.isDiffusing)
        {
            // Drain by 1 every # of ticks, as set in the config
            if (diffusingTicks % Config.DiffusingRate == 0)
            {
                this.drain(1, true);
            }

            if (diffusingTicks <= 0)
            {
                diffusingTicks = 20;
                if (!this.fluidTank.potionEffects.isEmpty() && this.fluidTank.getFluidAmount() > 0)
                {
                    this.applyPotionEffects();
                }
            }
            diffusingTicks--;

            if (this.getFluidAmount() == 0)
            {
                setDiffusingState(false);
            }
        }

        if (this.updateState)
        {
            MessageHandler.INSTANCE.sendToAllAround(new DiffuserUpdateMessage(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 20));
            this.markDirty();
            this.updateState = false;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if (this.fluidTank != null)
        {
            nbt.setTag("diffuserTank", fluidTank.writeToNBT(new NBTTagCompound()));
        }

        nbt.setShort("diffusingTicks", diffusingTicks);
        nbt.setBoolean("isDiffusing", isDiffusing);

    }
}
