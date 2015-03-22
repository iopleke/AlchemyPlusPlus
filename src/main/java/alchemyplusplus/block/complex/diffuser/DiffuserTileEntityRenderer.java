package alchemyplusplus.block.complex.diffuser;

import jakimbox.prefab.render.BasicTileEntityRenderer;
import alchemyplusplus.reference.Textures;

public class DiffuserTileEntityRenderer extends BasicTileEntityRenderer
{

    public DiffuserTileEntityRenderer()
    {
        super(1.0F, 0.0625F);
        model = new DiffuserModel();
        texture = Textures.Model.DIFFUSER;
    }
}
