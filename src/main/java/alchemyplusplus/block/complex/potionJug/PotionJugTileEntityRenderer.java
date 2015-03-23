package alchemyplusplus.block.complex.potionJug;

import alchemyplusplus.reference.Textures;
import jakimbox.prefab.render.BasicTileEntityRenderer;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;

public class PotionJugTileEntityRenderer extends BasicTileEntityRenderer
{

    public PotionJugTileEntityRenderer()
    {
        super(1.0F, 0.0625F);
        model = new PotionJugModel();
        texture = Textures.Model.POTION_JUG;
    }

    @Override
    public void modelSpecificOperations(TileEntity tileEntity)
    {
        if (model instanceof PotionJugModel && tileEntity instanceof PotionJugTileEntity)
        {
            ((PotionJugModel) model).percentage = ((float) ((PotionJugTileEntity) tileEntity).containerHas) / ((PotionJugTileEntity) tileEntity).containerMax;
            if (((PotionJugModel) model).percentage > 0)
            {
                // @TODO - use this for the diffuser color
                ((PotionJugModel) model).color = PotionHelper.func_77915_a(((PotionJugTileEntity) tileEntity).potionID, false);
            }
        }
    }
}
