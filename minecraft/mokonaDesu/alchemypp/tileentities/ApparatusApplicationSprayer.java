package mokonaDesu.alchemypp.tileentities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import mokonaDesu.alchemypp.MixingHelper;
import mokonaDesu.alchemypp.items.ItemRegistry;
import mokonaDesu.alchemypp.model.ModelSprayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ApparatusApplicationSprayer extends ApparatusApplicationUpperAccessory {

	public short activeTicks = 0;
	public final short delay = 200;
	public final short particleAmount = 10;
	
	public ApparatusApplicationSprayer(TileEntityAlchemicalApparatus parent) {
		this.parent = parent;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setShort("apparatus_type_upper", (short)this.getType());
		tag.setShort("sprayer_activeTicks", this.activeTicks);
	}
	
	public static ApparatusApplicationSprayer readFromNBT(NBTTagCompound tag, TileEntityAlchemicalApparatus entity) {
			ApparatusApplicationSprayer sprayer = new ApparatusApplicationSprayer(entity);
			sprayer.activeTicks = tag.getShort("sprayer_ticks");
			return sprayer;
	}

	@Override
	public void update() {
		if (this.canSpray()) {
			this.activeTicks++;
			if (this.activeTicks >= this.delay) {
				this.activeTicks = 0;
				this.doSpraying(((ApparatusApplicationBottleStand)this.parent.stand).getSprayEffects());
				if (FMLCommonHandler.instance().getEffectiveSide().isClient())
				doParticleRendering(((ApparatusApplicationBottleStand)this.parent.stand).getSprayColor(), this.parent.worldObj);
				MixingHelper.sprayingDegrade(((ApparatusApplicationBottleStand)this.parent.stand).stack);
			}
		} else this.activeTicks = 0;
	}
	
	private boolean canSpray() {
		if (this.parent.hasStand() && this.parent.stand instanceof ApparatusApplicationBottleStand)
		{
			ItemStack stack = ((ApparatusApplicationBottleStand)this.parent.stand).stack;
			if (stack != null && stack.itemID == Item.potion.itemID &&
			((ApparatusApplicationBottleStand)this.parent.stand).temperature >=
			MixingHelper.getBoilingTemperature
			(((ApparatusApplicationBottleStand)this.parent.stand).stack)) return true;
			else return false;
			
			
		} else return false;
	}
	
	private void doParticleRendering(int color, World world) {
		Random random = new Random();
		float r = (color >> 16 & 255) / 255.0F;
		float g = (color >> 8 & 255) / 255.0F;
		float b = (color >> 0 & 255) / 255.0F;

		for (int i = 0; i < this.particleAmount; i++) 
			world.spawnParticle("reddust", this.parent.xCoord + random.nextFloat() , this.parent.yCoord + random.nextFloat(), this.parent.zCoord + random.nextFloat(), r, g, b);
		
		for (int j = 0; j < this.particleAmount * 10; j++)
			world.spawnParticle("reddust", this.parent.xCoord - 10 + 20*random.nextFloat() , this.parent.yCoord + - 10 + 20*random.nextFloat(), this.parent.zCoord + - 10 + 20*random.nextFloat(), r, g, b);
		
	}
	
	private void doSpraying(ArrayList<PotionEffect> effects) {

		 AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB((double)this.parent.xCoord - 10, (double)this.parent.yCoord - 10, (double)this.parent.zCoord - 10, (double)(this.parent.xCoord + 10), (double)(this.parent.yCoord + 10), (double)(this.parent.zCoord + 10));
		
		 ArrayList<EntityLivingBase> entities = (ArrayList) this.parent.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
		 for (int i = 0; i < entities.size(); i++) {
			 for (int j = 0; j < effects.size(); j++) {
				 entities.get(i).addPotionEffect(new PotionEffect(effects.get(j).getPotionID(), 220, effects.get(j).getAmplifier()));
			 }
		 }

	}

	@Override
	public String getStat() {
		return " Has sprayer\n";
	}

	@Override
	public int getModelCode() {
		return 2;
	}
	
	@Override
	public int getType() {
		return 1;
	}

	@Override
	public boolean isSalvagable() {
		return true;
	}

	@Override
	public int getItemID() {
		return ItemRegistry.appItemSprayer.itemID;
	}

}
