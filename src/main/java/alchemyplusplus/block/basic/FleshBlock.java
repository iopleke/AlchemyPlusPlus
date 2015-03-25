package alchemyplusplus.block.basic;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.registry.CreativeTabRegistry;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class FleshBlock extends BasicBlock
{

    private IIcon blockIconArray[];
    private final Random random = new Random();

    public FleshBlock(String blockname)
    {
        super(AlchemyPlusPlus.ID, blockname, MaterialRegistry.flesh, Block.soundTypeCloth);
        setCreativeTab(CreativeTabRegistry.APP_TAB);
        setTickRandomly(true);
        setHardness(3.0F);
        setResistance(5.0F);
        blockIconArray = new IIcon[10];

    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float f = 0.125F;
        return AxisAlignedBB.getBoundingBox((double) par2, (double) par3, (double) par4, (double) (par2 + 1), (double) ((float) (par3 + 1) - f), (double) (par4 + 1));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta > 10)
        {
            meta = 10;
        }
        if (meta > 0)
        {
            return blockIconArray[meta - 1];
        }
        return blockIconArray[0];
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        // @TODO - create localized progress messages using
        // NotificationHelper.sendPlayerChatMessage(player, StatCollector.translateToLocal("fleshblock.progressMessage."+world.getBlockMetadata(x, y, z)));

        if (player.capabilities.isCreativeMode)
        {
            if (world.getBlockMetadata(x, y, z) < blockIconArray.length)
            {
                world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 1, 4);
                if (world.isRemote)
                {
                    NotificationHelper.sendPlayerChatMessage(player, "Incrementing block metadata to " + world.getBlockMetadata(x, y, z));
                }
            } else
            {
                if (world.isRemote)
                {
                    NotificationHelper.sendPlayerChatMessage(player, "Block metadata is " + blockIconArray.length + " (maximum)");
                }
            }
        }
        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        par5Entity.motionX *= 0.4D;
        par5Entity.motionZ *= 0.4D;
    }

    @Override
    public int quantityDropped(Random par1Random)
    {
        return random.nextInt(5) + 1;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

        if (par5Random.nextInt(10) == 0)
        {
            par1World.spawnParticle("townaura", (double) ((float) par2 + par5Random.nextFloat()), (double) ((float) par3 + 1.1F), (double) ((float) par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        for (int i = 0; i < 10; i++)
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
            }
            if (world.getBlockLightValue(x, y + 1, z) < 4 && world.isAirBlock(x, y + 1, z))
            {
                world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 1, 4);
            }

        }
    }

}
