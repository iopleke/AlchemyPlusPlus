package alchemyplusplus.tileentities.potioncontainer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
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

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    public void onDataPacket(INetHandler net, net.minecraft.network.play.server.S35PacketUpdateTileEntity packet)
    {
        packet.processPacket(net);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);

        this.potionID = par1NBTTagCompound.getShort("potionID");
        this.containerHas = par1NBTTagCompound.getShort("containerHas");

    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("potionID", (short) this.potionID);
        par1NBTTagCompound.setShort("containerHas", (short) this.containerHas);
    }

}
