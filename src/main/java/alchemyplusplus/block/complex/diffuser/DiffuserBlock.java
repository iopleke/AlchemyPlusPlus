package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.helper.MixingHelper;
import alchemyplusplus.reference.Naming;
import jakimbox.helper.NotificationHelper;
import jakimbox.prefab.block.BasicBlockContainer;
import java.util.Iterator;
import net.minecraft.block.Block;
import static net.minecraft.block.BlockDirectional.getDirection;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DiffuserBlock extends BasicBlockContainer
{

    public DiffuserBlock()
    {
        super(AlchemyPlusPlus.ID, Naming.Blocks.DIFFUSER, Material.wood);
        this.setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.8F, 0.8F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new DiffuserTileEntity();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        DiffuserTileEntity diffuser = (DiffuserTileEntity) world.getTileEntity(x, y, z);

        if (player.getHeldItem() != null)
        {
            if (player.getHeldItem().getItem() == Items.potionitem)
            {
                if (player.getHeldItem().getItemDamage() != 0 && !ItemPotion.isSplash(player.getHeldItem().getItemDamage()))
                {
                    ItemStack potionItemStack = player.getHeldItem();
                    if (potionItemStack != null)
                    {
                        if (diffuser.fluidTank.getFluidAmount() == diffuser.fluidTank.getCapacity())
                        {
                            if (!world.isRemote)
                            {
                                NotificationHelper.sendPlayerChatMessage(player, "diffuser.full");
                            }
                        } else if (diffuser.fluidTank.getFluidAmount() == 0 || diffuser.fluidTank.getFluid().fluidID == ItemPotion.getIdFromItem(player.getHeldItem().getItem()))
                        {
                            // @TODO - move potion match check to the TileEntity fill method
                            if (diffuser.getFluidAmount() != 0)
                            {
                                if (!world.isRemote)
                                {
                                    NotificationHelper.sendPlayerChatMessage(player, "diffuser.combine.success");
                                }
                            } else if ((diffuser.getFluidAmount() == 0))
                            {
                                if (!world.isRemote)
                                {
                                    NotificationHelper.sendPlayerChatMessage(player, "diffuser.pour");
                                }
                            }
                            diffuser.fillWithOverRide(player.getHeldItem());
                            diffuser.setDiffusingState(world.isBlockIndirectlyGettingPowered(x, y, z) || this.isGettingInput(world, x, y, z, world.getBlockMetadata(x, y, z)));
                            if (!player.capabilities.isCreativeMode && !player.isSneaking())
                            {
                                player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
                            }
                        } else
                        {
                            if (!world.isRemote)
                            {
                                NotificationHelper.sendPlayerChatMessage(player, "diffuser.combine.failure");
                            }

                        }

                    }
                } else if (player.getHeldItem().getItemDamage() == 0)// Item damage of zero is a water bottle
                {
                    if (!player.capabilities.isCreativeMode)
                    {
                        player.getCurrentEquippedItem().stackSize--;
                        player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
                    }

                    if (diffuser.fluidTank.getFluid() != null)
                    {
                        diffuser.resetDiffuser();

                        if (!world.isRemote)
                        {
                            NotificationHelper.sendPlayerChatMessage(player, "diffuser.wash.success");
                        }

                    } else
                    {
                        NotificationHelper.sendPlayerChatMessage(player, "diffuser.wash.failure");
                    }

                }
            } else if (player.getHeldItem().getItem() == Items.glass_bottle)
            {
                if (diffuser.fluidTank.getFluidAmount() > 0)
                {

                    // Set item to potion
                    ItemStack potion = new ItemStack(Items.potionitem, 1, diffuser.potionDamageValue);
                    Iterator iter = diffuser.fluidTank.potionEffects.iterator();
                    while (iter.hasNext())
                    {
                        MixingHelper.addEffect(potion, (PotionEffect) iter.next());
                    }
                    if (!player.capabilities.isCreativeMode)
                    {
                        player.getCurrentEquippedItem().stackSize--;
                    }
                    player.inventory.addItemStackToInventory(potion);

                    // Wiping the diffuser data
                    diffuser.resetDiffuser();
                } else
                {
                    if (!world.isRemote)
                    {
                        NotificationHelper.sendPlayerChatMessage(player, "diffuser.bottle.refill.failure");
                    }
                }
            }
        } else if (diffuser.canDiffuse() || diffuser.isDiffuserActive())
        {
            if (diffuser.isDiffuserActive())
            {
                if (!world.isRemote)
                {
                    NotificationHelper.sendPlayerChatMessage(player, "diffuser.cork");
                }
            } else
            {
                if (!world.isRemote)
                {
                    NotificationHelper.sendPlayerChatMessage(player, "diffuser.uncork");
                }
            }
            diffuser.toggleDiffusingState();

        } else
        {
            if (!world.isRemote)
            {
                NotificationHelper.sendPlayerChatMessage(player, "diffuser.uncork.failure");
            }

        }
        return false;
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side)
    {
        if (side != -1)
        {
            return true;
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block changedBlock)
    {
        if (changedBlock.canProvidePower())
        {
            TileEntity worldTE = world.getTileEntity(x, y, z);
            if (worldTE != null)
            {
                if (worldTE instanceof DiffuserTileEntity)
                {
                    if (((DiffuserTileEntity) worldTE).canDiffuse())
                    {
                        ((DiffuserTileEntity) worldTE).setDiffusingState(world.getBlockPowerInput(x, y, z) > 0 || world.isBlockIndirectlyGettingPowered(x, y, z) || this.isGettingInput(world, x, y, z, world.getBlockMetadata(x, y, z)));
                    } else
                    {
                        ((DiffuserTileEntity) worldTE).setDiffusingState(false);
                    }
                }
            }
        }
    }

    protected boolean isGettingInput(World world, int x, int y, int z, int side)
    {
        return this.getInputStrength(world, x, y, z, side) > 0;
    }

    protected int getInputStrength(World world, int x, int y, int z, int side)
    {
        int i1 = getDirection(side);
        int j1 = x + Direction.offsetX[i1];
        int k1 = z + Direction.offsetZ[i1];
        int l1 = world.getIndirectPowerLevelTo(j1, y, k1, Direction.directionToFacing[i1]);
        return l1 >= 15 ? l1 : Math.max(l1, world.getBlock(j1, y, k1) == Blocks.redstone_wire ? world.getBlockMetadata(j1, y, k1) : 0);
    }
}
