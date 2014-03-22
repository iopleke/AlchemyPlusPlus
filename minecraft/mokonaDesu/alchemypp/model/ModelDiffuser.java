package mokonaDesu.alchemypp.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDiffuser extends ModelBase {

    ModelRenderer bowl;
    ModelRenderer bottle;
    ModelRenderer stopper;

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
        // bowl.addBox(1, 4, 9, 6, 4, 6);

        // Stand for boiler
        bottle = new ModelRenderer(this, 24, 1);
        bottle.setTextureSize(textureWidth, textureHeight);
        setRotation(bottle, 0F, 0F, 0F);
        bottle.addBox(1, 0, 9, 6, 3, 6);

        stopper = new ModelRenderer(this, 0, 13);
        stopper.setTextureSize(textureWidth, textureHeight);
        stopper.setRotationPoint(5.7f, 13, 5);
        setRotation(stopper, 0F, 0.785f, 0F);
        stopper.addBox(-6, 0, 3, 10, 1, 1);
        stopper.addBox(-6, -3, 3, 1, 3, 1);
        stopper.addBox(3, -5, 3, 1, 5, 1);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bowl.render(f5);
        // bottle.render(f5);
        // stopper.render(f5);
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
