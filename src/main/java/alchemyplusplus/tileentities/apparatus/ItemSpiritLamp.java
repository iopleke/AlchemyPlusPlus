package alchemyplusplus.tileentities.apparatus;

import cpw.mods.fml.common.network.PacketDispatcher;
import alchemyplusplus.utility.BlockRegistry;
import alchemyplusplus.items.ItemTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemSpiritLamp extends ItemTemplate
{

    public ItemSpiritLamp(int id, String iconName)
    {
        super(id, iconName);
        this.maxStackSize = 1;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        if (world.isRemote)
        {
            return false;
        }

        if (world.getBlockId(x, y, z) == BlockRegistry.appBlockAlchemicalApparatus.blockID)
        {
            if (((TileEntityAlchemicalApparatus) world.getBlockTileEntity(x, y, z)).hasBottom())
            {
                player.addChatMessage("This Apparatus already has bottom accessory.");
                return false;
            } else
            {
                ((TileEntityAlchemicalApparatus) world.getBlockTileEntity(x, y, z)).addBottom(new ApparatusApplicationSpiritLamp(null));
                player.addChatMessage("Spiritlamp added");
                PacketDispatcher.sendPacketToAllPlayers(world.getBlockTileEntity(x, y, z).getDescriptionPacket());
                stack.stackSize = 0;
                return true;
            }
        }

        if (world.isBlockSolidOnSide(x, y, z, ForgeDirection.UP) && world.isAirBlock(x, y + 1, z))
        {
            world.setBlock(x, y + 1, z, BlockRegistry.appBlockAlchemicalApparatus.blockID);
            ((TileEntityAlchemicalApparatus) world.getBlockTileEntity(x, y + 1, z)).addBottom(new ApparatusApplicationSpiritLamp(null));
            player.addChatMessage("Placed new apparatus: lamp");
            stack.stackSize = 0;
            return true;
        } else
        {
            return false;
        }
    }

}
