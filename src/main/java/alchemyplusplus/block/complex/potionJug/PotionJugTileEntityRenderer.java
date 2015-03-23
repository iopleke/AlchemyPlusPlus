package alchemyplusplus.block.complex.potionJug;

import alchemyplusplus.reference.Textures;
import jakimbox.prefab.render.BasicTileEntityRenderer;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

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

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + xOffset, y + yOffset, z + zOffset);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0, 0.5F);
        GL11.glRotatef(tileEntity.getBlockMetadata() * (-90F), 0F, 1F, 0F);
        GL11.glTranslatef(-0.5F, 0, -0.5F);

        bindTexture(texture);

        modelSpecificOperations(tileEntity);

        model.render(0.0625F);

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
