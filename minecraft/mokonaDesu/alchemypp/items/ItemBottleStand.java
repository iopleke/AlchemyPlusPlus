package mokonaDesu.alchemypp.items;

import cpw.mods.fml.common.network.PacketDispatcher;
import mokonaDesu.alchemypp.blocks.BlockRegistry;
import mokonaDesu.alchemypp.tileentities.ApparatusApplicationBottleStand;
import mokonaDesu.alchemypp.tileentities.TileEntityAlchemicalApparatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemBottleStand extends APPItem {

	public ItemBottleStand(int id, String iconName) {
		super(id, iconName);
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
		if (world.isRemote) return false;
		
		if (world.getBlockId(x, y, z) == BlockRegistry.appBlockAlchemicalApparatus.blockID) {
			if (((TileEntityAlchemicalApparatus)world.getBlockTileEntity(x, y, z)).hasStand()) {
				player.addChatMessage("This Apparatus already has a stand accessory.");
				return false;
			} else {
				 ((TileEntityAlchemicalApparatus)world.getBlockTileEntity(x, y, z)).addStand(new ApparatusApplicationBottleStand(null));
				 player.addChatMessage("Bottlestand added");
				 PacketDispatcher.sendPacketToAllPlayers(world.getBlockTileEntity(x, y, z).getDescriptionPacket());
				 stack.stackSize = 0;
				 return true;
			}
		}
		
		 if (world.isBlockSolidOnSide(x, y, z, ForgeDirection.UP) && world.isAirBlock(x, y + 1, z)) {
			 world.setBlock(x, y + 1, z, BlockRegistry.appBlockAlchemicalApparatus.blockID);
			 ((TileEntityAlchemicalApparatus)world.getBlockTileEntity(x, y + 1, z)).addStand(new ApparatusApplicationBottleStand(null));
			 player.addChatMessage("Placed new apparatus: stand");
			 stack.stackSize = 0;
			 return true;
		 } else return false;
    }

}
