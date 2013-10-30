package mokonaDesu.alchemypp.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public abstract class ApparatusApplicationBottomAccessory extends ApparatusApplication {

	public static ApparatusApplicationBottomAccessory readFromNBT(
			NBTTagCompound tag,
			TileEntityAlchemicalApparatus tileEntityAlchemicalApparatus) {

		Short type = tag.getShort("apparatus_type_bottom");
		
		switch (type) {
		case 1: return ApparatusApplicationSpiritLamp.readFromNBT(tag, tileEntityAlchemicalApparatus);
		default: return null;
		}
		
	}

}
