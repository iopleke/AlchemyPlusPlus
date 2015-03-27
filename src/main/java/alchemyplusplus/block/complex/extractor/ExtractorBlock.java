package alchemyplusplus.block.complex.extractor;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ExtractorBlock extends BlockContainer
{

    public ExtractorBlock(int blockID)
    {
        super(Material.piston);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return null;
    }
}
