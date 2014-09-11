package alchemyplusplus.block.complex.extractor;

import alchemyplusplus.AlchemyPlusPlus;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class ExtractorBlock extends BlockContainer
{

    public ExtractorBlock(int blockID)
    {
        super(Material.piston);
        this.setHardness(1.0F);
        this.setResistance(3.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName("appBlockExtractor");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new ExtractorTileEntity();
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float g)
    {
        if (!world.isRemote)
        {
            player.openGui(AlchemyPlusPlus.instance, 1, world, x, y, z);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
        ExtractorTileEntity te = (ExtractorTileEntity) par1World.getTileEntity(par2, par3, par4);
        if (te.isActive())
        {
            par1World.spawnParticle("reddust",
                    (par2 + par5Random.nextFloat() / 2), (par3 + 0.1F),
                    (par4 + par5Random.nextFloat() / 2), 1.0D, 0.5D, 0.0D);
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("AlchemyPlusPlus:WIPLiquidMixer");
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }

}
