package mokonaDesu.alchemypp.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public abstract class ApparatusApplication {
	
	public TileEntityAlchemicalApparatus parent;
	
	/* Writes current Application object to the NBTTagCompound tag */
	public abstract void writeToNBT(NBTTagCompound tag);
	
	/* Called each tick to update current Application */
	public abstract void update();
	
	/* Used to get info about the Application in order to show to player */
	public abstract String getStat();
	
	/* Used to get the Model code for this piece of Alchemical Equipment */
	public abstract int getModelCode();
	
	/* Used to read the proper instance of Apparatus Application from NBT */
	public abstract int getType();
	
	/* Used to determine whether or not item is returned upon block breaking */
	public abstract boolean isSalvagable();
	
	/* Used to get the itemID of Item representing this apparatus application */
	public abstract int getItemID();
	
}
