package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.Config;
import alchemyplusplus.network.MessageHandler;
import alchemyplusplus.network.message.DiffuserUpdateMessage;
import alchemyplusplus.potion.fluid.PotionFluid;
import alchemyplusplus.potion.fluid.PotionFluidStack;
import alchemyplusplus.potion.fluid.PotionFluidTank;
import alchemyplusplus.reference.Naming;
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
    private boolean diffusingState;

    private short diffusingTicks;
    private PotionFluidTank fluidTank;
    private boolean updateState;

    public DiffuserTileEntity()
    {
        super(Naming.Blocks.DIFFUSER);
        resetDiffuser();
    }

    /**
     * Apply an effect to a player for a given potion ID
     *
     * @param player
     * @param potionID
     * @param duration
     * @return true if effect is applied
     */
    private boolean applyPotionEffectFromID(EntityPlayer player, int potionID, int duration)
    {
        if (player != null && potionID > 0 && duration > 0)
        {
            int overlap = 4;
            player.addPotionEffect(new PotionEffect(potionID, duration + overlap));
            return true;
        }
        return false;
    }

    private void applyPotionEffects()
    {

        // Iterate through all instances of EntityPlayer in the area
        Iterator playerIteratorForRadius = getPlayersInsideEffectRadius().iterator();
        EntityPlayer entityPlayer;
        while (playerIteratorForRadius.hasNext())
        {
            // Get the next EntityPlayer
            entityPlayer = (EntityPlayer) playerIteratorForRadius.next();

            // Iterate through all potion effects, applying each one to each EntityPlayer
            Iterator potionEffects = fluidTank.potionEffects.iterator();

            while (potionEffects.hasNext())
            {
                int potionID = ((PotionEffect) potionEffects.next()).getPotionID();
                int duration = Config.DiffusingRate * Config.DiffusingRateMultiplier;
                applyPotionEffectFromID(entityPlayer, potionID, duration);
            }
        }

    }

    private List getPlayersInsideEffectRadius()
    {
        // Get the bounding box for the diffusing range
        AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox((double) xCoord, (double) yCoord, (double) zCoord, (double) (xCoord + 1), (double) (yCoord + 1), (double) (zCoord + 1));
        if (isDiffuserHeated())
        {
            boundingBox.expand(Config.DiffusingRadiusActive, Config.DiffusingRadiusActive, Config.DiffusingRadiusActive);
        } else
        {
            boundingBox.expand(Config.DiffusingRadius, Config.DiffusingRadius, Config.DiffusingRadius);
        }
        boundingBox.maxY = (double) worldObj.getHeight();
        return worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox);
    }

    private boolean isDiffuserHeated()
    {
        Block belowBlock = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
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
                if (((PotionFluid) getFluid().getFluid()).potionEffects != null)
                {
                    Iterator iter = ((PotionFluid) getFluid().getFluid()).potionEffects.iterator();
                    while (iter.hasNext())
                    {
                        AlchemyPlusPlus.logger.debug("Effects: " + iter.next().toString());
                    }
                }

            }
        }
    }

    private void setDiffusingTicks(short ticks)
    {
        diffusingTicks = ticks;
    }

    private void setUpdateState(boolean state)
    {
        updateState = state;
    }

    public boolean canDiffuse()
    {
        if (getFluidAmount() > 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        if (fluidTank != null && getFluidAmount() > 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        if (fluidTank != null && getFluidAmount() < getCapacity())
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
            return fluidTank.drain(maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource != null)
        {
            return fluidTank.drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return fluidTank.drain(maxDrain, doDrain);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        if (resource != null)
        {
            return fluidTank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if (resource != null)
        {
            return fluidTank.fill(resource, doFill);
        }
        return 0;
    }

    public int fillWithOverRide(ItemStack heldItem)
    {
        if (heldItem != null)
        {
            PotionFluid potionFluid = new PotionFluid(heldItem);
            fluidTank = new PotionFluidTank(new PotionFluidStack(potionFluid, 333), 333);
            fluidTank.potionDamageValue = heldItem.getItemDamage();
            fluidTank.potionID = ItemPotion.getIdFromItem(heldItem.getItem());
            updateState = true;
            return getFluidAmount();
        }
        return 0;
    }

    @Override
    public int getCapacity()
    {

        if (fluidTank != null)
        {
            return fluidTank.getCapacity();
        }
        return 0;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        writeToNBT(new NBTTagCompound());
        MessageHandler.INSTANCE.sendToServer(new DiffuserUpdateMessage(this));
        return null;
    }

    @Override
    public FluidStack getFluid()
    {
        if (fluidTank != null)
        {
            return fluidTank.getFluid();
        }
        return null;

    }

    @Override
    public int getFluidAmount()
    {

        if (fluidTank != null)
        {
            return fluidTank.getFluidAmount();
        }
        return 0;
    }

    public PotionFluidTank getFluidTank()
    {
        return fluidTank;
    }

    @Override
    public FluidTankInfo getInfo()
    {
        if (fluidTank != null)
        {
            return fluidTank.getInfo();
        }
        return null;
    }

    public boolean getIsDiffusing()
    {
        return diffusingState;
    }

    public int getPotionDamageValue()
    {
        if (fluidTank != null)
        {
            return fluidTank.getPotionDamageValue();
        }
        return 0;
    }

    public List<PotionEffect> getPotionEffects()
    {
        if (fluidTank != null)
        {
            return fluidTank.getPotionEffects();
        }
        return null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {

        if (fluidTank != null)
        {
            return fluidTank.getTankInfo(from);
        }
        return null;
    }

    public boolean isDiffuserActive()
    {
        return diffusingState;
    }

    @Override
    public void markDirty()
    {
        super.markDirty();
        getDescriptionPacket();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        diffusingTicks = nbt.getShort("diffusingTicks");
        diffusingState = nbt.getBoolean("isDiffusing");

        if (fluidTank != null)
        {
            fluidTank.readFromNBT(nbt.getCompoundTag("diffuserTank"));
        }
    }

    public final void resetDiffuser()
    {
        setDiffusingTicks((short) 0);
        fluidTank = new PotionFluidTank((int) 333);
        setDiffusingState(false);
    }

    public void setDiffusingState(boolean state)
    {
        diffusingState = state;
        setUpdateState(true);
    }

    public void syncFluidAmountAt(int amount, int fluidID)
    {
        if (fluidTank != null)
        {
            if (getFluid() != null)
            {
                if (getFluidAmount() > amount)
                {
                    drain(getFluidAmount() - amount, true);
                } else if (getFluidAmount() < amount)
                {
                    fill(new FluidStack(getFluid(), amount - getFluidAmount()), true);
                }
            } else
            {
                FluidStack tankFluid = new FluidStack(fluidID, amount);
                fill(tankFluid, true);
            }
        }
        updateState = false;
    }

    public void toggleDiffusingState()
    {
        AlchemyPlusPlus.logger.debug("Fluid level:" + getFluidAmount());
        AlchemyPlusPlus.logger.debug("Diffusing: " + diffusingState);
        printPotionEffects();

        if (getFluidAmount() > 0)
        {
            diffusingState = !diffusingState;
        } else
        {
            diffusingState = false;
        }
        updateState = true;
    }

    @Override
    public void updateEntity()
    {
        if (diffusingState)
        {
            // Drain by 1 every # of ticks, as set in the config
            if (diffusingTicks % Config.DiffusingRate == 0)
            {
                drain(1, true);
            }

            if (diffusingTicks <= 0)
            {
                diffusingTicks = 20;
                if (!fluidTank.potionEffects.isEmpty() && getFluidAmount() > 0)
                {
                    applyPotionEffects();
                }
            }
            diffusingTicks--;

            if (getFluidAmount() == 0)
            {
                setDiffusingState(false);
            }
        }

        if (updateState)
        {
            markDirty();
            updateState = false;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if (fluidTank != null)
        {
            nbt.setTag("diffuserTank", fluidTank.writeToNBT(new NBTTagCompound()));
        }

        nbt.setShort("diffusingTicks", diffusingTicks);
        nbt.setBoolean("isDiffusing", diffusingState);

    }
}
