package alchemyplusplus.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public abstract class ApparatusApplicationUpperAccessory extends ApparatusApplication {

	public static ApparatusApplicationUpperAccessory readFromNBT(
			NBTTagCompound tag,
			TileEntityAlchemicalApparatus tileEntityAlchemicalApparatus) {

		Short type = tag.getShort("apparatus_type_upper");
		
		switch (type) {
		case 1: return ApparatusApplicationSprayer.readFromNBT(tag, tileEntityAlchemicalApparatus);
		default: return null;
		}
	}

}
