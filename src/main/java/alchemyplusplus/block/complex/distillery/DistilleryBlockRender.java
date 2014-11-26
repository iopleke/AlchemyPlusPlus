package alchemyplusplus.block.complex.distillery;

import alchemyplusplus.registry.BlockRegistry;
import alchemyplusplus.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class DistilleryBlockRender extends TileEntitySpecialRenderer
{

    DistilleryModel model = new DistilleryModel();

    public void render(DistilleryTileEntity tl, World world, int i, int j, int k, Block block)
    {
        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1,
                l2);

        int dir = world.getBlockMetadata(i, j, k);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0, 0.5F);
        GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
        GL11.glTranslatef(-0.5F, 0, -0.5F);

        bindTexture(Textures.Model.DISTILLERY);

        this.model
                .render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        DistilleryTileEntity tileEntityYour = (DistilleryTileEntity) tileEntity;
        render(tileEntityYour, tileEntity.getWorldObj(), tileEntity.xCoord,
                tileEntity.yCoord, tileEntity.zCoord,
                BlockRegistry.distillery);
        GL11.glPopMatrix();
    }
}
