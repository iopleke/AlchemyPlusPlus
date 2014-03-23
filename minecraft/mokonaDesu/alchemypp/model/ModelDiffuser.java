package mokonaDesu.alchemypp.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelDiffuser extends ModelBase {

    ModelRenderer bowl;
    ModelRenderer bottle;
    ModelRenderer liquid;
    ModelRenderer stopper;

    public int potionColor = 0;

    public boolean isDiffusing = false;

    public ModelDiffuser() {
        textureWidth = 128;
        textureHeight = 32;

        // Diffuser "bowl" base
        bowl = new ModelRenderer(this, 0, 0);
        bowl.setTextureSize(textureWidth, textureHeight);
        setRotation(bowl, 0, 0, 0);
        bowl.addBox(5, 0, 5, 6, 1, 6, 0); // bottom layer
        bowl.addBox(4, 1, 4, 8, 2, 8, 0); // middle layer
        bowl.addBox(3, 3, 3, 10, 2, 10, 0); // top layer

        // Bottle liquid
        liquid = new ModelRenderer(this, 0, 20);
        liquid.setTextureSize(textureWidth, textureHeight);
        setRotation(liquid, 0.4F, 0, 0);
        liquid.addBox(6, 5, 3, 4, 4, 4);
        liquid.addBox(7, 9, 4, 2, 1, 2);

        // Bottle
        bottle = new ModelRenderer(this, 40, 0);
        bottle.setTextureSize(textureWidth, textureHeight);
        setRotation(bottle, 0.4F, 0, 0);
        bottle.addBox(6, 4, 3, 4, 1, 4); // lower portion of the bottle
        bottle.addBox(5, 5, 2, 6, 5, 6); // middle portion of the bottle
        bottle.addBox(6, 10, 3, 4, 1, 4); // top portion of the bottle
        bottle.addBox(7, 11, 4, 2, 2, 2); // neck portion of the bottle
        bottle.addBox(6, 13, 3, 4, 1, 4); // lip portion of the bottle

        stopper = new ModelRenderer(this, 6, 6);
        stopper.setTextureSize(textureWidth, textureHeight);
        // stopper.setRotationPoint(5.7f, 13, 5);
        setRotation(stopper, 0.4F, 0, 0);
        stopper.addBox(7, 13, 4, 2, 2, 2); // stopper portion of the bottle

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);

        float red, green, blue;

        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        bowl.render(f5);

        // Calculate the colors
        red = (potionColor >> 16 & 255) / 255f;
        green = (potionColor >> 8 & 255) / 255f;
        blue = (potionColor >> 0 & 255) / 255f;

        GL11.glColor3f(red, green, blue);
        if (potionColor > 0) {
            liquid.render(f5);
        }
        GL11.glColor3f(1f, 1f, 1f);

        // Bottle must render AFTER the liquid
        bottle.render(f5);

        if (!isDiffusing) {
            stopper.render(f5);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
    }

}
