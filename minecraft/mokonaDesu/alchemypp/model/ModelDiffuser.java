package mokonaDesu.alchemypp.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDiffuser extends ModelBase {

    ModelRenderer bowl;
    ModelRenderer bottle;
    ModelRenderer stopper;
    public boolean isDiffusing = false;

    public ModelDiffuser() {
        textureWidth = 128;
        textureHeight = 32;

        // Diffuser "bowl" base
        bowl = new ModelRenderer(this, 0, 0);
        bowl.setTextureSize(textureWidth, textureHeight);
        setRotation(bowl, 0F, 0F, 0F);
        bowl.addBox(5F, 0F, 5F, 6, 1, 6, 0.0F); // bottom layer
        bowl.addBox(4F, 1F, 4F, 8, 2, 8, 0.0F); // middle layer
        bowl.addBox(3F, 3F, 3F, 10, 2, 10, 0.0F); // top layer

        // Bottle
        bottle = new ModelRenderer(this, 40, 0);
        bottle.setTextureSize(textureWidth, textureHeight);
        setRotation(bottle, 0.4F, 0F, 0F);
        bottle.addBox(6F, 4F, 3F, 4, 1, 4); // lower portion of the bottle
        bottle.addBox(5F, 5F, 2F, 6, 4, 6); // middle portion of the bottle
        bottle.addBox(6F, 9F, 3F, 4, 1, 4); // top portion of the bottle
        bottle.addBox(7F, 10F, 4F, 2, 2, 2); // neck portion of the bottle
        bottle.addBox(6F, 12F, 3F, 4, 1, 4); // lip portion of the bottle

        stopper = new ModelRenderer(this, 6, 6);
        stopper.setTextureSize(textureWidth, textureHeight);
        // stopper.setRotationPoint(5.7f, 13, 5);
        setRotation(stopper, 0.4F, 0F, 0F);
        stopper.addBox(7F, 12F, 4F, 2, 2, 2); // stopper portion of the bottle

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bowl.render(f5);
        bottle.render(f5);

        // @todo - set this to not render when diffusing
        // if (true) {
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
