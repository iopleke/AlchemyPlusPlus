package alchemyplusplus.tileentities.diffuser;

import alchemyplusplus.block.BlockComplex;
import alchemyplusplus.utility.NotificationManager;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.init.Items;

public class BlockDiffuser extends BlockComplex
{

    public boolean isDiffusing = false;

    public BlockDiffuser(String blockname)
    {
        super(Material.wood, blockname);
        this.setStepSound(Block.soundTypeWood);
        this.setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.8F, 0.8F);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityDiffuser();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float g)
    {

        TileEntityDiffuser diffuserTE = (TileEntityDiffuser) world.getTileEntity(x, y, z);

        if (player.getHeldItem() != null && !ItemPotion.isSplash(player.getHeldItem().getItemDamage()) && player.getHeldItem().getItem() == Items.potionitem)
        {
            if (diffuserTE.potionStack == null)
            {
                if (!world.isRemote)
                {
                    NotificationManager.sendChatMessage(player, "You pour the potion into the diffuser");
                }
                diffuserTE.potionStack = player.getHeldItem().copy();
                diffuserTE.setBottleColorValue(diffuserTE.potionStack.getItemDamage());
                if (!player.capabilities.isCreativeMode && !player.isSneaking())
                {
                    player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.glass_bottle);
                }
                diffuserTE.fluidLevel = 10;
                if (!diffuserTE.isDiffuserActive())
                {
                    diffuserTE.toggleDiffusingState();
                }
            } else
            {

                if (!world.isRemote)
                {
                    NotificationManager.sendChatMessage(player, "The diffuser is too full to hold any more liquid");
                }
            }
        } else if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.glass_bottle)
        {

            if (diffuserTE.potionStack != null)
            {
                if (diffuserTE.getFluidLevel() > 9)
                {

                    // Allow creative mode players to override this by sneaking
                    if (!player.capabilities.isCreativeMode)
                    {
                        player.inventory.mainInventory[player.inventory.currentItem] = diffuserTE.potionStack;
                    }
                    // Wiping the diffuser data
                    diffuserTE.potionStack = null;
                    diffuserTE.setBottleColorValue(0);
                    if (diffuserTE.isDiffuserActive())
                    {
                        diffuserTE.toggleDiffusingState();
                    }
                } else
                {
                    if (!world.isRemote)
                    {
                        NotificationManager.sendChatMessage(player, "There doesn't seem to be enough to refill your bottle");
                    }
                }
            }
        } else if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.potionitem && player.getHeldItem().getItemDamage() == 0)
        {
            if (!player.capabilities.isCreativeMode)
            {
                player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.glass_bottle);
            }
            diffuserTE.potionStack = null;
            diffuserTE.fluidLevel = 0;
            diffuserTE.setBottleColorValue(0);
            if (diffuserTE.isDiffuserActive())
            {
                diffuserTE.toggleDiffusingState();
            }
            if (!world.isRemote)
            {
                NotificationManager.sendChatMessage(player, "The diffuser is washed clean, ready for re-use");
            }

        } else if (diffuserTE.canDiffuse() || diffuserTE.isDiffuserActive())
        {
            String action;
            if (diffuserTE.isDiffuserActive())
            {
                action = "cork";
            } else
            {
                action = "un-cork";
            }
            diffuserTE.toggleDiffusingState();
            if (!world.isRemote)
            {
                NotificationManager.sendChatMessage(player, "You " + action + " the diffuser");
            }
        } else
        {
            if (!world.isRemote)
            {
                NotificationManager.sendChatMessage(player, "Without a potion filling it, un-corking the diffuser seems pointless");
            }

        }

        return false;
    }
}
