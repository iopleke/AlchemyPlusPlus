package mokonaDesu.alchemypp.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ApparatusApplicationStand extends ApparatusApplication {

	public static ApparatusApplicationStand readFromNBT(NBTTagCompound tag,
			TileEntityAlchemicalApparatus tileEntityAlchemicalApparatus) {
		
		Short type = tag.getShort("apparatus_type_stand");
		
		switch (type) {
		case 1: return ApparatusApplicationBottleStand.readFromNBT(tag, tileEntityAlchemicalApparatus);
		default: return null;
		}
	}
	
	public abstract void eject(EntityPlayer e);
	
}
