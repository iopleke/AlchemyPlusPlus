package alchemyplusplus.tileentities.distillery;

import java.util.Random;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.items.ItemRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;

public class BlockDistillery extends BlockContainer
{

    public BlockDistillery(int blockID)
    {
        super(Material.piston);
        this.setHardness(1.0F);
        this.setResistance(3.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName("appBlockDistillery");
        this.setBlockBounds(0.05F, 0F, 0.15F, 0.85F, 0.8F, 0.95F);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityDistillery();
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
            player.openGui(AlchemyPlusPlus.instance, 2, world, x, y, z);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
        TileEntityDistillery te = (TileEntityDistillery) par1World.getTileEntity(par2, par3, par4);
        if (te.isActive())
        {
            par1World.spawnParticle("reddust", (par2 + par5Random.nextFloat() / 2), (par3 + 0.1F), (par4 + par5Random.nextFloat() / 2), 1.0D, 0.5D, 0.0D);
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }

}
