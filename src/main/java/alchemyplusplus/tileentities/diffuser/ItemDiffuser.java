package alchemyplusplus.tileentities.diffuser;

import alchemyplusplus.block.BlockRegistry;
import alchemyplusplus.items.ItemTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemDiffuser extends ItemTemplate
{

    public ItemDiffuser(int id, String iconName)
    {
        super(id, iconName);
        this.maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        // !par3World.getBlockMaterial(par4, par5, par6).isSolid() ||
        // !world.isBlockFullCube(x, y + 1, z)
        if (world.isBlockSolidOnSide(x, y, z, ForgeDirection.UP) && world.isAirBlock(x, y + 1, z))
        {
            // @todo - set block to face to the right when placed
            world.setBlock(x, y + 1, z, BlockRegistry.appBlockDiffuser.blockID);
            stack.stackSize--;
            if (!world.isRemote)
            {
                // @todo - make this only display once per user
                player.addChatMessage("The diffuser is ready to be filled with a potion");
            }
            return true;
        } else
        {
            return false;
        }

    }

}
