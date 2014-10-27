package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.network.MessageHandler;
import alchemyplusplus.network.message.DiffuserUpdateMessage;
import alchemyplusplus.potion.PotionFluid;
import alchemyplusplus.potion.PotionFluidStack;
import alchemyplusplus.potion.PotionFluidTank;
import alchemyplusplus.reference.Settings;
import cpw.mods.fml.common.network.NetworkRegistry;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class DiffuserTileEntity extends TileEntity implements IFluidHandler, IFluidTank
{
	private boolean updateState;
	public PotionFluidTank fluidTank;
	public boolean isDiffusing;
	public int bottleColor;
	public int diffusingTicks;

	public DiffuserTileEntity()
	{
		this.bottleColor = 0;
		this.diffusingTicks = 0;
		this.fluidTank = new PotionFluidTank((int) 333);
		this.isDiffusing = false;
		this.updateState = false;
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
	public Packet getDescriptionPacket()
	{
		this.writeToNBT(new NBTTagCompound());
		return MessageHandler.INSTANCE.getPacketFrom(new DiffuserUpdateMessage(this));
	}

	public boolean isDiffuserActive()
	{
		return isDiffusing;
	}

	public void setBottleColorValue(int bottleColor)
	{
		this.bottleColor = bottleColor;
	}

	public void resetDiffuser()
	{
		this.bottleColor = 0;
		this.diffusingTicks = 0;
		this.fluidTank = new PotionFluidTank((int) 333);
		this.isDiffusing = false;
		this.updateState = true;
	}

	public void toggleDiffusingState()
	{
		if (Settings.DebugMode)
		{
			AlchemyPlusPlus.LOGGER.info("Fluid level:" + this.fluidTank.getFluidAmount());
			AlchemyPlusPlus.LOGGER.info("Diffusing: " + isDiffusing);
			if (this.getFluid() != null && this.getFluid().getFluid() instanceof PotionFluid)
			{
				if (((PotionFluid) this.getFluid().getFluid()).potionEffects != null)
				{
					Iterator iter = ((PotionFluid) this.getFluid().getFluid()).potionEffects.iterator();
					while (iter.hasNext())
					{
						AlchemyPlusPlus.LOGGER.info("Effects: " + iter.next().toString());
					}
				}

			}
		}
		if (this.fluidTank.getFluidAmount() > 0)
		{
			isDiffusing = !isDiffusing;
		} else
		{
			isDiffusing = false;
		}
		this.updateState = true;
	}

	public void setDiffusingState(boolean value)
	{
		this.isDiffusing = value;
		this.updateState = true;
	}

	@Override
	public void updateEntity()
	{
		if (this.isDiffusing)
		{
			this.drain(1, true); // drain by 1 per tick, for testing purposes
			if (this.getFluidAmount() == 0)
			{
				this.setBottleColorValue(0);
			}
		}
		if (this.updateState)
		{
			MessageHandler.INSTANCE.sendToAllAround(new DiffuserUpdateMessage(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 20));
			this.markDirty();
			this.updateState = false;
		}
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

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if (this.fluidTank != null)
		{
			nbt.setTag("diffuserTank", this.fluidTank.writeToNBT(new NBTTagCompound()));
			nbt.setInteger("diffuserTankAmount", this.fluidTank.getFluidAmount());
		}

		nbt.setShort("diffusingTicks", (short) this.diffusingTicks);
		nbt.setInteger("bottleColor", this.bottleColor);
		nbt.setBoolean("isDiffusing", this.isDiffusing);

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		this.diffusingTicks = nbt.getShort("diffusingTicks");

		this.isDiffusing = nbt.getBoolean("isDiffusing");

		this.bottleColor = nbt.getInteger("bottleColor");

		if (this.fluidTank != null)
		{
			this.fluidTank.readFromNBT(nbt.getCompoundTag("diffuserTank"));
		}
	}

	public int fillWithOverRide(ItemStack heldItem)
	{
		PotionFluid potionFluid = new PotionFluid(heldItem);
		this.fluidTank = new PotionFluidTank(new PotionFluidStack(potionFluid, 333), 333);
		//this.bottleColor = ((ItemPotion) heldItem.getItem()).getColorFromDamage(heldItem.getItemDamage());
		this.bottleColor = potionFluid.getColor();
		AlchemyPlusPlus.LOGGER.info("Potion color is " + this.bottleColor);
		this.updateState = true;
		return this.fluidTank.getFluidAmount();
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
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		if (this.fluidTank != null && this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity())
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
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
	public int getCapacity()
	{

		if (this.fluidTank != null)
		{
			return this.fluidTank.getCapacity();
		}
		return 0;
	}

	@Override
	public FluidTankInfo getInfo()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
