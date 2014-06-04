package alchemyplusplus.tileentities;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPotionContainer extends TileEntity {

	public int potionID = 0;
	public int containerHas = 0;
	public final int containerMax = 16;
	
	@Override
    public boolean canUpdate()
    {
        return false;
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
	        par1NBTTagCompound.setShort("potionID", (short)this.potionID);
	        par1NBTTagCompound.setShort("containerHas", (short)this.containerHas);
	    }
	  
	  public Packet getDescriptionPacket() {
	        NBTTagCompound nbtTag = new NBTTagCompound();
	        this.writeToNBT(nbtTag);
	        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	        }

	        public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
	        readFromNBT(packet.data);
	        }
	        
	  
}
