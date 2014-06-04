package alchemyplusplus.tileentities.apparatus;

import alchemyplusplus.utility.BlockRegistry;
import alchemyplusplus.items.ItemTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ItemSprayer extends ItemTemplate
{

    public ItemSprayer(int id, String iconName)
    {
        super(id, iconName);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        if (world.isRemote)
        {
            return false;
        }

        if (world.getBlockId(x, y, z) == BlockRegistry.appBlockAlchemicalApparatus.blockID)
        {
            if (((TileEntityAlchemicalApparatus) world.getBlockTileEntity(x, y, z)).hasUpper())
            {
                player.addChatMessage("This Apparatus already has an upper accessory.");
                return false;
            } else
            {
                TileEntityAlchemicalApparatus entity = ((TileEntityAlchemicalApparatus) world.getBlockTileEntity(x, y, z));
                if (entity.hasStand() && entity.stand instanceof ApparatusApplicationBottleStand)
                {
                    ((TileEntityAlchemicalApparatus) world.getBlockTileEntity(x, y, z)).addUpper(new ApparatusApplicationSprayer(null));
                    player.addChatMessage("Diffuser added");
                    PacketDispatcher.sendPacketToAllPlayers(world.getBlockTileEntity(x, y, z).getDescriptionPacket());
                    stack.stackSize = 0;
                    return true;
                } else
                {
                    player.addChatMessage("There is no container to attach sprayer to.");
                    return false;
                }
            }
        }

        /*
         if (world.isBlockSolidOnSide(x, y, z, ForgeDirection.UP) && world.isAirBlock(x, y + 1, z)) {
         world.setBlock(x, y + 1, z, BlockRegistry.appBlockAlchemicalApparatus.blockID);
         ((TileEntityAlchemicalApparatus)world.getBlockTileEntity(x, y + 1, z)).addSprayer();
         player.addChatMessage("Placed new apparatus: sprayer");
         // PacketDispatcher.sendPacketToAllPlayers(world.getBlockTileEntity(x, y, z).getDescriptionPacket());
         return true;
         } else return false;
         */
        return true;
    }

}
