package alchemyplusplus.tileentities;

import java.util.ArrayList;

import alchemyplusplus.MixingHelper;
import alchemyplusplus.items.ItemRegistry;
import alchemyplusplus.model.ModelBottleStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.biome.BiomeGenBase;

public class ApparatusApplicationBottleStand extends ApparatusApplicationStand {
		
	public ItemStack stack = null;
	public boolean normalTemperatureSet = false;
	public float temperature = 0;
	public float normalTemperature = 0;
	public boolean active = false;
	
	public ApparatusApplicationBottleStand(TileEntityAlchemicalApparatus entity) {
		this.parent = entity;
	}
	
	public static ApparatusApplicationBottleStand readFromNBT(NBTTagCompound tag, TileEntityAlchemicalApparatus entity) {
		ApparatusApplicationBottleStand stand = new ApparatusApplicationBottleStand(entity);
		if (tag.hasKey("bottlestand_stack")) stand.stack = ItemStack.loadItemStackFromNBT((NBTTagCompound)tag.getTag("bottlestand_stack"));
		stand.temperature = tag.getFloat("bottlestand_temperature");
		return stand;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setShort("apparatus_type_stand", (short)this.getType());
		if (this.stack != null) tag.setTag("bottlestand_stack", this.stack.writeToNBT(new NBTTagCompound()));
		tag.setFloat("bottlestand_temperature", this.temperature);
	}

	@Override
	public void update() {
	
		this.regulateHeat();
	}

	private void regulateHeat() {
		//small preparation for calculation since it is not possible during TileEntity first load
		if (!this.normalTemperatureSet) 
		{
			this.normalTemperature = this.getBiomeNormalTemp(this.parent.worldObj.getBiomeGenForCoords(this.parent.xCoord, this.parent.yCoord));
			if (this.temperature == 0) this.temperature = this.normalTemperature;
			this.normalTemperatureSet = true;
		}
		
		//normal heat exchange
		if (this.temperature > this.normalTemperature) {
			this.temperature *= 0.9995;
			this.temperature -= 0.01;
			if (this.temperature < this.normalTemperature) this.temperature = this.normalTemperature;
		} else if (this.temperature < this.normalTemperature) {
			this.temperature *= 1.001;
			this.temperature += 0.01;
			if (this.temperature > this.normalTemperature) this.temperature = this.normalTemperature;
		}
	}
	
	public void placeBottle(ItemStack stack) {
		this.stack = stack;
	}
	
	public void eject(EntityPlayer e) {
		if (this.stack == null) return;
		
		this.temperature = this.normalTemperature;
		
		ItemStack stack = this.stack;
		this.stack = null;
		
		EntityItem entity = new EntityItem(this.parent.worldObj, this.parent.xCoord, this.parent.yCoord, this.parent.zCoord, stack);
		this.parent.worldObj.spawnEntityInWorld(entity);
	}

	private short getBiomeNormalTemp(BiomeGenBase biome) {
		return (short)((biome.temperature * 20) + 273);
	}
	
	public int getSprayColor() {
		return PotionHelper.func_77915_a(stack.getItemDamage(), false);
	}
	
	public ArrayList getSprayEffects() {
return (ArrayList)((ItemPotion) (this.stack.getItem())).getEffects(this.stack);
	}
	
	public boolean isBoiling() {
		return this.temperature >= MixingHelper.getBoilingTemperature(this.stack);	
	}

	@Override
	public String getStat() {
		if ( this.temperature == this.normalTemperature ) {
			return "Not heating\n";
		}
		return "Temp: " + java.lang.Math.round(this.temperature) + " kelvin\n";
	}
	
	public boolean hasBottle() {
		return this.stack != null;
	}

	@Override
	public int getModelCode() {
		return 1;
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
		return ItemRegistry.appItemBottleStand.itemID;
	}
	
	public Boolean isActive(){
		return this.active;
	}
	
}
