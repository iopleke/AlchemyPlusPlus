package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.network.MessageHandler;
import alchemyplusplus.network.message.DiffuserUpdateMessage;
import alchemyplusplus.potion.PotionFluid;
import alchemyplusplus.potion.PotionFluidStack;
import alchemyplusplus.potion.PotionFluidTank;
import alchemyplusplus.reference.Settings;
import cpw.mods.fml.common.network.NetworkRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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
	public int potionDamageValue;

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
			// @TODO - remove these, and/or allow player to see info
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

			if (diffusingTicks <= 0)
			{
				this.diffusingTicks = 20;
				if (!this.fluidTank.potionEffects.isEmpty() && this.fluidTank.getFluidAmount() > 0)
				{
					this.applyPotionEffects();
				}
			}
			this.diffusingTicks--;

			if (this.getFluidAmount() == 0)
			{
				this.setBottleColorValue(0);
				this.setDiffusingState(false);
			}
		}

		if (this.updateState)
		{
			MessageHandler.INSTANCE.sendToAllAround(new DiffuserUpdateMessage(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 20));
			this.markDirty();
			this.updateState = false;
		}
	}

	private void applyPotionEffects()
	{
		AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox((double) this.xCoord, (double) this.yCoord, (double) this.zCoord, (double) (this.xCoord + 1), (double) (this.yCoord + 1), (double) (this.zCoord + 1)).expand(Settings.DiffusingRadius, Settings.DiffusingRadius, Settings.DiffusingRadius);
		axisalignedbb.maxY = (double) this.worldObj.getHeight();
		List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);

		// Iterate through all instances of EntityPlayer in the area
		Iterator players = list.iterator();
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
				if (potionID != 0)
				{
					int duration = 21;
					entityplayer.addPotionEffect(new PotionEffect(potionID, duration));
				}
			}
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
			nbt.setInteger("effectsCount", this.fluidTank.potionEffects.size());
			nbt.setInteger("potionDamageValue", this.potionDamageValue);
			Iterator potionEffects = this.fluidTank.potionEffects.iterator();
			int count = 0;
			while (potionEffects.hasNext())
			{
				PotionEffect effect = (PotionEffect) potionEffects.next();
				nbt.setInteger("effect" + count, effect.getPotionID());
				nbt.setInteger("duration" + count, effect.getDuration());
				count++;
			}
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

		this.potionDamageValue = nbt.getInteger("potionDamageValue");

		if (this.fluidTank != null)
		{
			this.fluidTank.readFromNBT(nbt.getCompoundTag("diffuserTank"));
			this.fluidTank.potionEffects = new ArrayList();

			int count = nbt.getInteger("effectsCount");
			while (count >= 0)
			{
				int potionID = nbt.getInteger("effect" + count);
				int duration = nbt.getInteger("duration" + count);
				PotionEffect effect = new PotionEffect(potionID, duration, 0);

				this.fluidTank.potionEffects.add(effect);
				count--;
			}
		}
	}

	public int fillWithOverRide(ItemStack heldItem)
	{
		PotionFluid potionFluid = new PotionFluid(heldItem);
		this.fluidTank = new PotionFluidTank(new PotionFluidStack(potionFluid, 333), 333);
		this.potionDamageValue = heldItem.getItemDamage();
		this.bottleColor = potionFluid.getColor();
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
