package alchemyplusplus.tileentities.mixer;

import alchemyplusplus.utility.BlockRegistry;
import alchemyplusplus.items.ItemTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemLiquidMixer extends ItemTemplate
{

    public ItemLiquidMixer(int id, String iconName)
    {
        super(id, iconName);
        this.maxStackSize = 64;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        if (world.isBlockSolidOnSide(x, y, z, ForgeDirection.UP) && world.isAirBlock(x, y + 1, z))
        {
            world.setBlock(x, y + 1, z, BlockRegistry.appBlockLiquidMixer.blockID);
            stack.stackSize--;
            return true;
        } else
        {
            return false;
        }

    }

}
