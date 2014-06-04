package alchemyplusplus.tileentities.apparatus;

import net.minecraft.nbt.NBTTagCompound;

public abstract class ApparatusApplication
{

    public TileEntityAlchemicalApparatus parent;

    /* Used to get the itemID of Item representing this apparatus application */
    public abstract int getItemID();

    /* Used to get the Model code for this piece of Alchemical Equipment */
    public abstract int getModelCode();

    /* Used to get info about the Application in order to show to player */
    public abstract String getStat();

    /* Used to read the proper instance of Apparatus Application from NBT */
    public abstract int getType();

    /* Used to get whether the element is active */
    public abstract Boolean isActive();

    /* Used to determine whether or not item is returned upon block breaking */
    public abstract boolean isSalvagable();

    /* Called each tick to update current Application */
    public abstract void update();

    /* Writes current Application object to the NBTTagCompound tag */
    public abstract void writeToNBT(NBTTagCompound tag);

}
