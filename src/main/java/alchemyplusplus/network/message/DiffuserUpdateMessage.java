package alchemyplusplus.network.message;

import alchemyplusplus.block.complex.diffuser.DiffuserTileEntity;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;

public class DiffuserUpdateMessage implements IMessage, IMessageHandler<DiffuserUpdateMessage, IMessage>
{
    private int posX, posY, posZ;
    private int bottleColor, potionDamageValue, fluidLevel, fluidID;
    private boolean isDiffusing;
    private int effectsListSize;
    private int[] effectIDs;

    public DiffuserUpdateMessage()
    {

    }

    public DiffuserUpdateMessage(DiffuserTileEntity diffuser)
    {
        this.posX = diffuser.xCoord;
        this.posY = diffuser.yCoord;
        this.posZ = diffuser.zCoord;

        this.bottleColor = diffuser.bottleColor;
        this.potionDamageValue = diffuser.potionDamageValue;
        this.isDiffusing = diffuser.isDiffusing;
        this.fluidLevel = diffuser.getFluidAmount();
        if (diffuser.getFluid() != null)
        {
            this.fluidID = diffuser.getFluid().fluidID;
        } else
        {
            this.fluidID = 0;
        }

        this.effectsListSize = diffuser.fluidTank.potionEffects.size();
        this.effectIDs = new int[1];

        Iterator potionEffects = diffuser.fluidTank.potionEffects.iterator();

        while (potionEffects.hasNext())
        {
            int potionID = ((PotionEffect) potionEffects.next()).getPotionID();
            this.effectIDs = this.addIntToArray(this.effectIDs, potionID);
        }

    }

    private int[] addIntToArray(int[] intArray, int toAdd)
    {
        int[] sizedArray = new int[intArray.length + 1];

        sizedArray[intArray.length] = toAdd;

        return sizedArray;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();

        this.bottleColor = buf.readInt();
        this.potionDamageValue = buf.readInt();
        this.isDiffusing = buf.readBoolean();
        this.fluidLevel = buf.readInt();
        this.fluidID = buf.readInt();
        this.effectsListSize = buf.readInt();
        this.effectIDs = new int[1];

        int count = this.effectsListSize;
        while (count > 0)
        {
            this.effectIDs = this.addIntToArray(this.effectIDs, buf.readInt());
            count--;
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);

        buf.writeInt(this.bottleColor);
        buf.writeInt(this.potionDamageValue);
        buf.writeBoolean(this.isDiffusing);
        buf.writeInt(this.fluidLevel);
        buf.writeInt(this.fluidID);
        buf.writeInt(this.effectsListSize);

        int count = 0;
        while (count < this.effectsListSize)
        {
            buf.writeInt(this.effectIDs[count]);
            count++;
        }
    }

    @Override
    public IMessage onMessage(DiffuserUpdateMessage message, MessageContext ctx)
    {
        TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.posX, message.posY, message.posZ);
        if (tile instanceof DiffuserTileEntity)
        {
            ((DiffuserTileEntity) tile).setBottleColorValue(message.bottleColor);
            ((DiffuserTileEntity) tile).setDiffusingState(message.isDiffusing);
            ((DiffuserTileEntity) tile).syncFluidAmountAt(message.fluidLevel, message.fluidID);
            ((DiffuserTileEntity) tile).potionDamageValue = this.potionDamageValue;

        }
        return null;
    }
}
