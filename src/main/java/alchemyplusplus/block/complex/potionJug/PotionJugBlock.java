package alchemyplusplus.block.complex.potionJug;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.reference.Textures;
import alchemyplusplus.registry.BlockRegistry;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PotionJugBlock extends BlockContainer
{

    public PotionJugBlock(String blockname)
    {
        super(Material.glass);
        this.setStepSound(Block.soundTypeGlass);
        this.setBlockName(blockname);
        this.setBlockTextureName(Textures.Icon.POTION_JUG);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new PotionJugTileEntity();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        ItemStack stack = new ItemStack(BlockRegistry.potionJug, 1, 0);
        Random random = new Random();

        if (world.getTileEntity(x, y, z) != null)
        {
            int potionID = (((PotionJugTileEntity) world.getTileEntity(x, y, z)).potionID);
            int has = (((PotionJugTileEntity) world.getTileEntity(x, y, z)).containerHas);
            if (potionID != 0 && has != 0)
            {
                stack.stackTagCompound = new NBTTagCompound();
                stack.getTagCompound().setShort("effectID", (short) potionID);
                stack.getTagCompound().setShort("containerHas", (short) has);
            }
        }

        float f = random.nextFloat() * 0.8F + 0.1F;
        float f1 = random.nextFloat() * 0.8F + 0.1F;
        float f2 = random.nextFloat() * 0.8F + 0.1F;

        EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2),
            stack);

        entityitem.motionX = (double) ((float) random.nextGaussian() * 0.05F);
        entityitem.motionY = (double) ((float) random.nextGaussian() * 0.05F + 0.2F);
        entityitem.motionZ = (double) ((float) random.nextGaussian() * 0.05F);

        AlchemyPlusPlus.logger.info("Spawning potion jug item entity");

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int par5)
    {
        PotionJugTileEntity te = (PotionJugTileEntity) world.getTileEntity(x, y, z);
        return (int) Math.floor(te.containerHas * 16f / te.containerMax);
    }

    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        world.notifyBlockChange(x, y, z, this);

        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof PotionJugTileEntity)
        {
            if (player.isSneaking() || player.getCurrentEquippedItem() == null)
            {
                if (((PotionJugTileEntity) tileEntity).containerHas > 0)
                {
                    ((PotionJugTileEntity) tileEntity).containerHas--;

                    List effectList = PotionHelper.getPotionEffects(((PotionJugTileEntity) tileEntity).potionID, true);
                    for (Object effect : effectList)
                    {
                        player.addPotionEffect((PotionEffect) effect);
                    }
                }
            } else
            {
                ItemStack itemStack = player.getCurrentEquippedItem();
                if (itemStack != null)
                {
                    Item heldItem = itemStack.getItem();
                    if (heldItem != null)
                    {
                        // Check if the player is holding a potion
                        if (heldItem == Items.potionitem)
                        {
                            // Make sure it's not a bottle of water
                            if (itemStack.getItemDamage() > 0)
                            {
                                // @TODO - allow custom potions
                                // For now, vanilla potions only
                                if (!itemStack.hasTagCompound())
                                {
                                    boolean changed = false;
                                    // Jug doesn't have any potion in it
                                    if (((PotionJugTileEntity) tileEntity).containerHas == 0)
                                    {
                                        ((PotionJugTileEntity) tileEntity).potionID = itemStack.getItemDamage();
                                        changed = true;

                                    } else if (((PotionJugTileEntity) tileEntity).potionID == itemStack.getItemDamage())
                                    {
                                        changed = true;
                                    }
                                    if (changed)
                                    {
                                        // Make sure the jug isn't full
                                        if (((PotionJugTileEntity) tileEntity).containerHas < ((PotionJugTileEntity) tileEntity).containerMax)
                                        {
                                            ((PotionJugTileEntity) tileEntity).containerHas++;
                                        }
                                        if (!player.capabilities.isCreativeMode)
                                        {
                                            itemStack.stackSize--;

                                            ItemStack potion = new ItemStack(Items.glass_bottle, 1, 0);
                                            player.inventory.addItemStackToInventory(potion);
                                        }
                                    }
                                }

                            }

                        } else if (heldItem == Items.glass_bottle && itemStack.getItemDamage() == 0)
                        {
                            // Check if the jug has anything in it
                            if (((PotionJugTileEntity) tileEntity).containerHas > 0)
                            {
                                // Remove a unit of potion from the jug
                                ((PotionJugTileEntity) tileEntity).containerHas--;
                                ItemStack potion = new ItemStack(Items.potionitem, 1, ((PotionJugTileEntity) tileEntity).potionID);
                                if (!player.capabilities.isCreativeMode)
                                {
                                    // Add it to the player's inventory if they aren't in creative mode
                                    player.inventory.addItemStackToInventory(potion);
                                    itemStack.stackSize--;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
