package alchemyplusplus.block.complex.distillery;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class DistilleryModel extends ModelBase
{

    ModelRenderer boiler;
    ModelRenderer collecter;
    ModelRenderer stand;
    ModelRenderer tube;

    public DistilleryModel()
    {
        textureWidth = 128;
        textureHeight = 32;

        // Boiler. Eventually, will add copper and use that for construction
        boiler = new ModelRenderer(this, 0, 0);
        boiler.setTextureSize(textureWidth, textureHeight);
        setRotation(boiler, 0F, 0F, 0F);
        boiler.addBox(3, 9, 11, 2, 2, 2);
        boiler.addBox(2, 3, 10, 4, 6, 4);
        boiler.addBox(1, 4, 9, 6, 4, 6);

        // Stand for boiler
        stand = new ModelRenderer(this, 24, 1);
        stand.setTextureSize(textureWidth, textureHeight);
        setRotation(stand, 0F, 0F, 0F);
        stand.addBox(1, 0, 9, 6, 3, 6);

        tube = new ModelRenderer(this, 0, 13);
        tube.setTextureSize(textureWidth, textureHeight);
        tube.setRotationPoint(5.7f, 13, 5);
        setRotation(tube, 0F, 0.785f, 0F);
        tube.addBox(-6, -1, 3, 10, 1, 1);
        tube.addBox(-6, -4, 3, 1, 3, 1);
        tube.addBox(3, -6, 3, 1, 5, 1);

        collecter = new ModelRenderer(this, 58, 0);
        collecter.setTextureSize(textureWidth, textureWidth);
        setRotation(collecter, 0f, 0f, 0f);
        collecter.addBox(9, 1, 3, 4, 1, 4);
        collecter.addBox(8, 2, 2, 6, 3, 6);
        collecter.addBox(9, 5, 3, 4, 1, 4);
        collecter.addBox(10, 6, 4, 2, 2, 2);
        collecter.addBox(9.5f, 7.5f, 3.5f, 3, 1, 3);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        stand.render(f5);
        boiler.render(f5);
        tube.render(f5);
        collecter.render(f5);
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
