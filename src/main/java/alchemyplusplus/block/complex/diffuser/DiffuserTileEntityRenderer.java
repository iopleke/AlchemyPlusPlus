package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.reference.Textures;
import jakimbox.prefab.render.BasicTileEntityRenderer;
import net.minecraft.tileentity.TileEntity;

public class DiffuserTileEntityRenderer extends BasicTileEntityRenderer
{

    public DiffuserTileEntityRenderer()
    {
        super(1.0F, 0.0625F);
        model = new DiffuserModel();
        texture = Textures.Model.DIFFUSER;
    }

    @Override
    public void modelSpecificOperations(TileEntity tileEntity)
    {
        if (model instanceof DiffuserModel && tileEntity instanceof DiffuserTileEntity)
        {
            ((DiffuserModel) model).isDiffusing = ((DiffuserTileEntity) tileEntity).isDiffuserActive();
            ((DiffuserModel) model).potionDamage = ((DiffuserTileEntity) tileEntity).getPotionDamageValue();
            ((DiffuserModel) model).fluidAmount = ((DiffuserTileEntity) tileEntity).getFluidAmount();
        }
    }
}
