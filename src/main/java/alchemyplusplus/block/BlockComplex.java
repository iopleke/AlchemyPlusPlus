package alchemyplusplus.block;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.utility.ConfigManager;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockComplex extends BlockContainer
{

    String blockname;
    
    public BlockComplex(Material material, String blockname)
    {
        super(material);
        this.setBlockName(blockname);
        this.blockname = blockname;
        
        this.setCreativeTab(ConfigManager.appCreativeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        throw new UnsupportedOperationException("TileEntity didn't override createNewTileEntity");
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(blockname);
    }    

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        return false;
    }
}