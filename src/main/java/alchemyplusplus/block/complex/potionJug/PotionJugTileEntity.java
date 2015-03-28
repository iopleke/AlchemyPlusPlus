package alchemyplusplus.block.complex.potionJug;

import alchemyplusplus.reference.Naming;
import jakimbox.prefab.tileEntity.BasicTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class PotionJugTileEntity extends BasicTileEntity
{

    public int containerHas = 0;
    public final int containerMax = 16;
    public int potionID = 0;

    public PotionJugTileEntity()
    {
        super(Naming.Blocks.POTION_JUG);
    }

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

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.potionID = nbt.getShort("potionID");
        this.containerHas = nbt.getShort("containerHas");

    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setShort("potionID", (short) this.potionID);
        nbt.setShort("containerHas", (short) this.containerHas);
    }

}
