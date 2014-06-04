package alchemyplusplus.tileentities.potioncontainer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPotionContainer extends TileEntity
{

    public int containerHas = 0;
    public final int containerMax = 16;
    public int potionID = 0;

    @Override
    public boolean canUpdate()
    {
        return false;
    }

    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
    {
        readFromNBT(packet.data);
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);

        this.potionID = par1NBTTagCompound.getShort("potionID");
        this.containerHas = par1NBTTagCompound.getShort("containerHas");

    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("potionID", (short) this.potionID);
        par1NBTTagCompound.setShort("containerHas", (short) this.containerHas);
    }

}
