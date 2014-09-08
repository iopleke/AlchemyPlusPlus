package alchemyplusplus.tileentities.mixer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import alchemyplusplus.AlchemyPlusPlus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLiquidMixer extends BlockContainer
{

    public BlockLiquidMixer(int blockID)
    {
        super(Material.piston);
        this.setHardness(1.0F);
        this.setResistance(3.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName("appBlockLiquidMixer");
    }

    @SideOnly(Side.CLIENT)
    public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean addBlockHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityLiquidMixer();
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
            player.openGui(AlchemyPlusPlus.instance, 0, world, x, y, z);
        }
        return true;
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
