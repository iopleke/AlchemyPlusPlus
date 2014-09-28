package alchemyplusplus.network.message;

import alchemyplusplus.block.complex.diffuser.DiffuserTileEntity;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class DiffuserUpdateMessage implements IMessage, IMessageHandler<DiffuserUpdateMessage, IMessage>
{
	private int posX, posY, posZ;
	private int bottleColor;
	private boolean isDiffusing;

	public DiffuserUpdateMessage()
	{

	}

	public DiffuserUpdateMessage(DiffuserTileEntity diffuser)
	{
		this.posX = diffuser.xCoord;
		this.posY = diffuser.yCoord;
		this.posZ = diffuser.zCoord;

		this.bottleColor = diffuser.bottleColor;
		this.isDiffusing = diffuser.isDiffusing;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.posX = buf.readInt();
		this.posY = buf.readInt();
		this.posZ = buf.readInt();

		this.bottleColor = buf.readInt();
		this.isDiffusing = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.posX);
		buf.writeInt(this.posY);
		buf.writeInt(this.posZ);

		buf.writeInt(this.bottleColor);
		buf.writeBoolean(this.isDiffusing);
	}

	@Override
	public IMessage onMessage(DiffuserUpdateMessage message, MessageContext ctx)
	{
		TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.posX, message.posY, message.posZ);
		if (tile instanceof DiffuserTileEntity)
		{
			((DiffuserTileEntity) tile).setBottleColorValue(message.bottleColor);
			((DiffuserTileEntity) tile).setDiffusingState(message.isDiffusing);

		}
		return null;
	}
}
