package alchemyplusplus.network.packet;

import alchemyplusplus.network.PacketDispatcher;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;


public class DiffuserPacketUpdate extends PacketDispatcher.AbstractPacket<DiffuserPacketUpdate>
{

    @Override
    public void processPacket(EntityPlayer player)
    {
        // process the packet
    }

    @Override
    public void receivePacket(PacketBuffer buffer) throws IOException
    {
        // recieve the packet
    }

    @Override
    public void sendPacket(PacketBuffer buffer) throws IOException
    {
        // send a packet
    }
    

}
