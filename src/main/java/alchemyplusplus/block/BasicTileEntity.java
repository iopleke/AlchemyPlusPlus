package alchemyplusplus.block;

import net.minecraft.tileentity.TileEntity;

public abstract class BasicTileEntity extends TileEntity
{
    @Override
    public int getBlockMetadata()
    {
        return worldObj != null ? super.getBlockMetadata() : 0;
    }
}
