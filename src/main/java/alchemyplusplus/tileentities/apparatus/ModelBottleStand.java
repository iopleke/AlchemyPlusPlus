package alchemyplusplus.tileentities.apparatus;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionHelper;

import org.lwjgl.opengl.GL11;

public class ModelBottleStand extends APPAlchemicalApparatusModel
{

    ModelRenderer bottle;
    ModelRenderer liquid;
    ModelRenderer plug;
    // fields
    ModelRenderer stand;

    public ModelBottleStand()
    {
        textureWidth = 64;
        textureHeight = 64;

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, TileEntityAlchemicalApparatus tileEntity)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);

        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        if (((ApparatusApplicationBottleStand) tileEntity.stand).hasBottle())
        {

            int color = PotionHelper.func_77915_a(((ApparatusApplicationBottleStand) tileEntity.stand).stack.getItemDamage(), false);

            float red, green, blue;
            red = (color >> 16 & 255) / 255f;
            green = (color >> 8 & 255) / 255f;
            blue = (color >> 0 & 255) / 255f;

            GL11.glColor3f(red, green, blue);
            liquid = new ModelRenderer(this, 0, 24);
            liquid.setTextureSize(this.textureWidth, this.textureHeight);
            setRotation(liquid, 0f, 0f, 0f);
            liquid.addBox(5.5f, 8, 5.5f, 5, 2, 5);
            liquid.addBox(6.5f, 7.5f, 6.5f, 3, 1, 3);
            liquid.render(f5);
            GL11.glColor3f(1f, 1f, 1f);

            if (tileEntity.upper == null)
            {
                plug = new ModelRenderer(this, 32, 0);
                plug.setTextureSize(this.textureWidth, this.textureHeight);
                setRotation(plug, 0f, 0f, 0f);
                plug.addBox(7.5f, 13, 7.5f, 1, 2, 1);
                plug.render(f5);
            }

            bottle = new ModelRenderer(this, 29, 13);
            bottle.setTextureSize(this.textureWidth, this.textureHeight);
            setRotation(bottle, 0f, 0f, 0f);
            bottle.addBox(6, 7, 6, 4, 1, 4);
            bottle.addBox(5, 8, 5, 6, 3, 6);
            bottle.addBox(6, 11, 6, 4, 1, 4);
            bottle.addBox(7, 12, 7, 2, 2, 2);
            bottle.addBox(6.5f, 13.5f, 6.5f, 3, 1, 3);
            bottle.render(f5);
        }
        stand = new ModelRenderer(this, 0, 11);
        stand.setTextureSize(textureWidth, textureHeight);
        setRotation(stand, 0F, 0F, 0F);
        stand.addBox(5, 0, 5, 6, 7, 6);
        stand.render(f5);

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
