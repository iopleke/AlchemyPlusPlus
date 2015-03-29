package alchemyplusplus.block.complex.potionJug;

import jakimbox.prefab.model.BasicModel;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class PotionJugModel extends BasicModel
{

    //fields
    ModelRenderer bottle;

    ModelRenderer liquid;
    ModelRenderer neck;

    public float percentage;
    public int color;
    public int potionID;

    public PotionJugModel()
    {
        textureWidth = 64;
        textureHeight = 32;

        bottle = new ModelRenderer(this, 0, 0);
        neck = new ModelRenderer(this, 0, 0);
        liquid = new ModelRenderer(this, 12, 26);

        bottle.setTextureSize(64, 64);
        neck.setTextureSize(64, 64);
        neck.setTextureOffset(0, 26);
        liquid.setTextureSize(64, 64);

        setRotation(liquid, 0F, 0F, 0F);
        setRotation(bottle, 0F, 0F, 0F);
        setRotation(neck, 0F, 0F, 0F);

        bottle.addBox(1, 0, 1, 14, 12, 14);    //bottle
        neck.addBox(5, 12, 5, 6, 4, 6);        //neck
    }

    @Override
    public void render(float scale)
    {
        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (percentage > 0)
        {
            float r, g, b;
            r = (color >> 16 & 255) / 255.0F;
            g = (color >> 8 & 255) / 255.0F;
            b = (color & 255) / 255.0F;

            GL11.glColor4f(r * 0.8f, g * 0.8f, b * 0.8f, 0.8f);

            liquid = new ModelRenderer(this, 12, 26);
            liquid.setTextureSize(64, 64);
            setRotation(liquid, 0F, 0F, 0F);

            //liquid where 10 - height
            liquid.addBox(2, 1, 2, 12, (int) (10 * percentage), 12);
            // @TODO - figure out why using scale here causes weird rendering
            // liquid.render(scale);
            liquid.render(0.0625F);
            GL11.glColor4f(1f, 1f, 1f, 1f);
        }

        bottle.render(scale);
        neck.render(scale);

    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
    }

}
