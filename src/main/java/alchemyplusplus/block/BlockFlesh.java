package alchemyplusplus.block;

import alchemyplusplus.items.ItemRegistry;
import alchemyplusplus.utility.ConfigManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Items;

public class BlockFlesh extends Block
{

    private IIcon iconFester;
    private IIcon iconNormal;
    private final Random random = new Random();

    public BlockFlesh(int id, Material material)
    {
        super(material);
        this.setTickRandomly(true);
        this.setCreativeTab(ConfigManager.appCreativeTab);
        this.setHardness(3.0F).setResistance(5.0F);
        this.setBlockName("fleshBlock");
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float f = 0.125F;
        return AxisAlignedBB.getBoundingBox((double) par2, (double) par3, (double) par4, (double) (par2 + 1), (double) ((float) (par3 + 1) - f), (double) (par4 + 1));
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIIcon(int side, int meta)
    {
        return meta == 10 ? this.iconFester : this.iconNormal;
    }

    public Item itemDropped(int meta, Random random, int fortune)
    {
        return meta == 10 ? ItemRegistry.appItemFesteringFlesh : Items.rotten_flesh;
    }

    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        par5Entity.motionX *= 0.4D;
        par5Entity.motionZ *= 0.4D;
    }

    public int quantityDropped(Random par1Random)
    {
        return random.nextInt(5) + 1;
    }

    @SideOnly(Side.CLIENT)
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
        this.iconNormal = iconRegister.registerIcon("AlchemyPlusPlus:flesh");
        this.iconFester = iconRegister.registerIcon("AlchemyPlusPlus:fleshFester");
    }

    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote)
        {
            if (world.getBlockMetadata(x, y, z) == 10)
            {
                return;
            }
            if (world.getBlockLightValue(x, y + 1, z) < 4 && world.isAirBlock(x, y + 1, z))
            {
                world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 1, 4);
            }

        }
    }

}
