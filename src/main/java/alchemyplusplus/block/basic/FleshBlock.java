package alchemyplusplus.block.basic;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.registry.MaterialRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jakimbox.helper.NotificationHelper;
import jakimbox.prefab.block.BasicBlock;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class FleshBlock extends BasicBlock
{

    private IIcon blockIconArray[];

    public FleshBlock(String blockname)
    {
        super(AlchemyPlusPlus.ID, blockname, MaterialRegistry.flesh, Block.soundTypeCloth);
        setTickRandomly(true);
        setHardness(3.0F);
        setResistance(5.0F);
        blockIconArray = new IIcon[11];

    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        // makes walking/running slower on the block
        return AxisAlignedBB.getBoundingBox((double) x, (double) y, (double) z, (double) (x + 1), (double) ((float) (y + 1) - 0.125F), (double) (z + 1));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta > 10)
        {
            meta = 10;
        }
        if (meta >= 0)
        {
            return blockIconArray[meta];
        }
        return blockIconArray[0];
    }

    /**
     * Increment or decrement the block metadata by one
     *
     * @param world     the world object
     * @param x         block x coordinate
     * @param y         block y coordinate
     * @param z         block z coordinate
     * @param increment true for increment, false for decrement
     */
    protected void incrementBlockMetadata(World world, int x, int y, int z, boolean increment)
    {
        if (world.getBlockMetadata(x, y, z) >= 10 && increment)
        {
            // sanity checking
            // setting block metadata
            // never more than ten
            world.setBlockMetadataWithNotify(x, y, z, 10, 4);
        } else if (world.getBlockMetadata(x, y, z) <= 0 && !increment)
        {
            world.setBlockMetadataWithNotify(x, y, z, 0, 4);
        } else
        {
            if (increment)
            {
                world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 1, 4);
            } else
            {
                world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) - 1, 4);
            }
        }
    }

    protected void fleshblockRightClickChatMessage(World world, int x, int y, int z, EntityPlayer player)
    {
        if (world.isRemote)
        {
            NotificationHelper.sendPlayerChatMessage(player, StatCollector.translateToLocal("fleshblock.progressMessage." + world.getBlockMetadata(x, y, z)));
        }
    }

    protected void fleshblockRainingChatMessage(World world, int x, int y, int z, EntityPlayer player)
    {
        if (world.isRemote)
        {
            NotificationHelper.sendPlayerChatMessage(player, StatCollector.translateToLocal("fleshblock.progressMessage.raining"));
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (player.getCurrentEquippedItem() != null)
        {
            if (world.isRaining())
            {
                if (world.canBlockSeeTheSky(x, y + 1, z))
                {
                    fleshblockRainingChatMessage(world, x, y, z, player);

                    decrementHeldStackSize(player);
                    return false;
                }
            }
            if (world.getBlockLightValue(x, y + 1, z) < 5 && world.isAirBlock(x, y + 1, z))
            {
                boolean blockDataChanged = false;
                if (player.getCurrentEquippedItem().getItem() == Items.dye && player.getCurrentEquippedItem().getItemDamage() == 15)
                {
                    incrementBlockMetadata(world, x, y, z, true);
                    blockDataChanged = true;
                } else if (player.getCurrentEquippedItem().getItem() == Items.sugar)
                {
                    incrementBlockMetadata(world, x, y, z, true);
                    blockDataChanged = true;
                } else if (player.getCurrentEquippedItem().getItem() == Items.clay_ball)
                {
                    incrementBlockMetadata(world, x, y, z, false);
                    blockDataChanged = true;
                }

                if (blockDataChanged)
                {
                    // @TODO - update the texture on block metadata change
                    this.decrementHeldStackSize(player);
                }

            }

        }
        fleshblockRightClickChatMessage(world, x, y, z, player);
        return false;
    }

    protected void decrementHeldStackSize(EntityPlayer player)
    {

        if (!player.capabilities.isCreativeMode)
        {
            if (player.getCurrentEquippedItem().stackSize > 1)
            {
                player.getCurrentEquippedItem().stackSize--;
            } else
            {
                player.setCurrentItemOrArmor(0, null);
            }
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        entity.motionX *= 0.4D;
        entity.motionZ *= 0.4D;
    }

    /**
     * Determine how many drops for the block
     *
     * @param random
     * @return 1-6
     */
    @Override
    public int quantityDropped(Random random)
    {
        return random.nextInt(5) + 1;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        super.randomDisplayTick(world, x, y, z, random);

        if (random.nextInt(10) == 0)
        {
            world.spawnParticle("townaura", (double) ((float) x + random.nextFloat()), (double) ((float) y + 1.1F), (double) ((float) z + random.nextFloat()), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        for (int i = 0; i < 11; i++)
        {
            blockIconArray[i] = iconRegister.registerIcon(AlchemyPlusPlus.ID + ":fleshBlock" + i);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote)
        {
            if (world.getBlockMetadata(x, y, z) == 10)
            {
                return;
            } else if (world.getBlockMetadata(x, y, z) > 10)
            {
                world.setBlockMetadataWithNotify(x, y, z, 10, 4);
                return;
            }
            if (world.getBlockLightValue(x, y + 1, z) < 5 && world.isAirBlock(x, y + 1, z) && !world.isRaining())
            {
                incrementBlockMetadata(world, x, y, z, true);
            } else if (world.canBlockSeeTheSky(x, y + 1, z))
            {
                // If it's raining on the block, decrease progress
                incrementBlockMetadata(world, x, y, z, false);
            }

        }
    }

}
