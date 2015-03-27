package alchemyplusplus.block.complex.distillery;

import alchemyplusplus.reference.Textures;
import jakimbox.prefab.render.BasicTileEntityRenderer;

public class DistilleryTileEntityRenderer extends BasicTileEntityRenderer
{

    public DistilleryTileEntityRenderer()
    {
        super(1.0F, 0.0625F);
        model = new DistilleryModel();
        texture = Textures.Model.DISTILLERY;
    }
}
