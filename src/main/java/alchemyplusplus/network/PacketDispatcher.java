package alchemyplusplus.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;

public class PacketDispatcher extends SimpleNetworkWrapper
{

    public PacketDispatcher(String channelName)
    {
        super(channelName);
    }

    public <T extends IMessage & IMessageHandler<T, IMessage>> void registerPacket(int id, Side side, Class<T> messageClass)
    {
        registerMessage(messageClass, messageClass, id, side);
    }

    public void sendPacket(EntityPlayer player, IMessage message)
    {
        sendTo(message, (EntityPlayerMP) player);
    }
    
    public static void sendPacketToAllPlayers(Packet packet){
        MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(packet);
    }

    public static abstract class AbstractPacket<T extends AbstractPacket<T>> implements IMessage, IMessageHandler<T, IMessage>
    {

        /*
         * sendPacket
         *
         * Abstract method used to send packet to client
         */
        public abstract void sendPacket(PacketBuffer buffer) throws IOException;

        public abstract void receivePacket(PacketBuffer buffer) throws IOException;

        public abstract void processPacket(EntityPlayer player);

        @Override
        public final void toBytes(ByteBuf buffer)
        {
            try
            {
                sendPacket(new PacketBuffer(buffer));
            } catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public final void fromBytes(ByteBuf buffer)
        {
            try
            {
                receivePacket(new PacketBuffer(buffer));
            } catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public final IMessage onMessage(T message, MessageContext context)
        {
            message.processPacket(context.side.isServer() ? context.getServerHandler().playerEntity
                    : Minecraft.getMinecraft().thePlayer);
            return null;
        }

    }

}
