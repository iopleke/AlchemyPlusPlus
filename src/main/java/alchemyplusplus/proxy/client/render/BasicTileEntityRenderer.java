package alchemyplusplus.proxy.client.render;

import alchemyplusplus.block.BasicModel;
import alchemyplusplus.block.BasicTileEntity;
import alchemyplusplus.block.complex.diffuser.DiffuserModel;
import alchemyplusplus.block.complex.diffuser.DiffuserTileEntity;
import alchemyplusplus.reference.Textures;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class BasicTileEntityRenderer extends TileEntitySpecialRenderer
{
    protected BasicModel model;
    protected float rotation;
    protected ResourceLocation texture;

    protected double xOffset;
    protected double yOffset;
    protected double zOffset;
    protected float xScale;
    protected float yScale;
    protected float zScale;

    public BasicTileEntityRenderer()
    {
        setScale(1.0F);
    }

    public BasicTileEntityRenderer(float scale)
    {
        setScale(scale);
        setRotation(0.0625F);
    }

    public BasicTileEntityRenderer(float scale, float rotation)
    {
        setScale(scale);
        setRotation(rotation);
        setOffset(0.0D, 0.0D, 0.0D);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale)
    {
        if (tileEntity instanceof BasicTileEntity)
        {
            GL11.glPushMatrix();
            GL11.glTranslated(x + xOffset, y + yOffset, z + zOffset);

            GL11.glPushMatrix();
            GL11.glTranslatef(0.5F, 0, 0.5F);
            GL11.glRotatef(tileEntity.getBlockMetadata() * (-90F), 0F, 1F, 0F);
            GL11.glTranslatef(-0.5F, 0, -0.5F);

            bindTexture(Textures.Model.DIFFUSER);
            if (model instanceof DiffuserModel && tileEntity instanceof DiffuserTileEntity)
            {
                ((DiffuserModel) model).isDiffusing = ((DiffuserTileEntity) tileEntity).isDiffuserActive();
                ((DiffuserModel) model).potionDamage = ((DiffuserTileEntity) tileEntity).bottleColor;
                ((DiffuserModel) model).fluidAmount = ((DiffuserTileEntity) tileEntity).getFluidAmount();
            }

            model.render(0.0625F);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }

    public final void setOffset(double xOffset, double yOffset, double zOffset)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }

    private void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public final void setScale(float scale)
    {
        this.xScale = scale;
        this.yScale = scale;
        this.zScale = scale;
    }

    public final void setScale(float xScale, float yScale, float zScale)
    {
        this.xScale = xScale;
        this.yScale = yScale;
        this.zScale = zScale;

    }

}
