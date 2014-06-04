package alchemyplusplus.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelExtractor extends ModelBase {

    // fields
    ModelRenderer platform;
    ModelRenderer heater;
    ModelRenderer bottle1;
    ModelRenderer bottle2;

    ModelRenderer tube1;
    ModelRenderer tube2;
    ModelRenderer tube3;

    ModelRenderer chamber;

    public ModelExtractor() {
        textureWidth = 128;
        textureHeight = 32;

        platform = new ModelRenderer(this, 0, 0);
        platform.setTextureSize(textureWidth, textureHeight);

        heater = new ModelRenderer(this, 56, 0);
        heater.setTextureSize(textureWidth, textureHeight);

        bottle1 = new ModelRenderer(this, 56, 7);
        bottle1.setTextureSize(textureWidth, textureHeight);

        bottle2 = new ModelRenderer(this, 72, 7);
        bottle2.setTextureSize(textureWidth, textureHeight);

        tube1 = new ModelRenderer(this, 0, 16);
        tube1.setTextureSize(textureWidth, textureHeight);

        tube2 = new ModelRenderer(this, 0, 16);
        tube2.setTextureSize(textureWidth, textureHeight);

        tube3 = new ModelRenderer(this, 0, 16);
        tube3.setTextureSize(textureWidth, textureHeight);
        tube3.setRotationPoint(5.7f, 13, 5);

        chamber = new ModelRenderer(this, 20, 16);
        chamber.setTextureSize(textureWidth, textureHeight);
        chamber.setRotationPoint(5.7f, 13, 5);

        setRotation(platform, 0F, 0F, 0F);
        setRotation(heater, 0F, 0F, 0F);
        setRotation(bottle1, 0F, 0F, 0F);
        setRotation(bottle2, 0F, 0F, 0F);
        setRotation(tube1, 0F, 0F, 0F);
        setRotation(tube2, 0F, 0F, 0F);
        setRotation(tube3, 0F, -0.785f, 0F);
        setRotation(chamber, 0F, -0.785f, 0F);

        platform.addBox(1, 0, 1, 14, 2, 14);
        heater.addBox(2, 2, 2, 6, 1, 6);
        bottle1.addBox(3, 3, 3, 4, 8, 4);
        bottle2.addBox(9, 2, 9, 5, 6, 5);
        tube1.addBox(5, 6, 7, 1, 1, 5);
        tube1.addBox(6, 6, 11, 3, 1, 1);
        tube2.addBox(7, 4, 5, 5, 1, 1);
        tube2.addBox(11, 4, 6, 1, 1, 3);

        tube3.addBox(0, 0, 0, 9, 1, 1);
        tube3.addBox(0, -2, 0, 1, 2, 1);
        tube3.addBox(8, -5, 0, 1, 5, 1);

        chamber.addBox(3, -1, -1, 3, 3, 3);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3,
            float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        platform.render(f5);
        heater.render(f5);
        bottle1.render(f5);
        bottle2.render(f5);
        tube1.render(f5);
        tube2.render(f5);
        tube3.render(f5);
        chamber.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3,
            float f4, float f5, Entity e) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
    }

}
