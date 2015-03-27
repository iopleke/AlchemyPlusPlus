package alchemyplusplus.block.complex.distillery;

import jakimbox.prefab.model.BasicModel;
import net.minecraft.client.model.ModelRenderer;

public class DistilleryModel extends BasicModel
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
        setRotation(tube, 0F, 0.785f, 0F);
        float distance = 11F;
        tube.addBox(-6, 12, distance, 11, 1, 1);
        tube.addBox(-6, 9, distance, 1, 3, 1);
        tube.addBox(4, 7, distance, 1, 5, 1);

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
    public void render(float scale)
    {
        stand.render(scale);
        boiler.render(scale);
        tube.render(scale);
        collecter.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
