package alchemyplusplus.block.complex.distillery;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.reference.Naming;
import jakimbox.prefab.block.BasicBlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DistilleryBlock extends BasicBlockContainer
{

    public DistilleryBlock()
    {
        super(AlchemyPlusPlus.ID, Naming.Blocks.DISTILLERY, Material.iron);
        this.setBlockBounds(0.05F, 0F, 0.15F, 0.85F, 0.8F, 0.95F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float b, float c, float g)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && !player.isSneaking())
        {
            if (!world.isRemote)
            {
                player.openGui(AlchemyPlusPlus.INSTANCE, 2, world, x, y, z);
            }
            return true;
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new DistilleryTileEntity();
    }
}
