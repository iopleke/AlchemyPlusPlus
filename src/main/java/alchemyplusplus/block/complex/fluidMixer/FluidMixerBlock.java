package alchemyplusplus.block.complex.fluidMixer;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.block.BlockComplex;
import alchemyplusplus.reference.Naming;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FluidMixerBlock extends BlockComplex
{

    public FluidMixerBlock(String blockname)
    {
        super(Material.iron, Naming.Blocks.FLUID_MIXER, Block.soundTypeGlass);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new FluidMixerTileEntity();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            player.openGui(AlchemyPlusPlus.INSTANCE, 0, world, x, y, z);
        }
        return true;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }

}
