package alchemyplusplus.tileentities.mixer;

import org.lwjgl.opengl.GL11;

import alchemyplusplus.items.ItemRegistry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;

public class ModelLiquidMixer extends ModelBase
{
    ModelRenderer filter;

    ModelRenderer filterStand;

    ModelRenderer flask1;
    ModelRenderer flask2;
    ModelRenderer flask3;
    ModelRenderer flask4;

//fields
    ModelRenderer platform;
    ModelRenderer potion1;
    ModelRenderer potion2;
    ModelRenderer potion3;
    ModelRenderer potion4;
    ModelRenderer stand1;
    ModelRenderer stand2;
    ModelRenderer stand3;
    ModelRenderer stand4;

    ModelRenderer tube1;
    ModelRenderer tube2;
    ModelRenderer tube3;
    ModelRenderer tube4;

    public ModelLiquidMixer()
    {
        textureWidth = 64;
        textureHeight = 64;

        platform = new ModelRenderer(this, 0, 0);
        platform.setTextureSize(textureWidth, textureHeight);
        setRotation(platform, 0F, 0F, 0F);
        platform.addBox(0, 0, 0, 16, 2, 16);

        stand1 = new ModelRenderer(this, 0, 18);
        stand1.setTextureSize(textureWidth, textureHeight);
        setRotation(stand1, 0F, 0F, 0F);
        stand1.addBox(1, 2, 1, 5, 5, 5);

        stand2 = new ModelRenderer(this, 0, 18);
        stand2.setTextureSize(textureWidth, textureHeight);
        setRotation(stand2, 0F, 0F, 0F);
        stand2.addBox(10, 2, 1, 5, 5, 5);

        stand3 = new ModelRenderer(this, 0, 18);
        stand3.setTextureSize(textureWidth, textureHeight);
        setRotation(stand3, 0F, 0F, 0F);
        stand3.addBox(1, 2, 10, 5, 5, 5);

        stand4 = new ModelRenderer(this, 0, 18);
        stand4.setTextureSize(textureWidth, textureHeight);
        setRotation(stand4, 0F, 0F, 0F);
        stand4.addBox(10, 2, 10, 5, 5, 5);

        flask1 = new ModelRenderer(this, 20, 18);
        flask1.setTextureSize(textureWidth, textureHeight);
        setRotation(flask1, 0F, 0F, 0F);
        flask1.addBox(1, 7, 1, 5, 6, 5);

        flask2 = new ModelRenderer(this, 20, 18);
        flask2.setTextureSize(textureWidth, textureHeight);
        setRotation(flask2, 0F, 0F, 0F);
        flask2.addBox(1, 7, 10, 5, 6, 5);

        flask3 = new ModelRenderer(this, 20, 18);
        flask3.setTextureSize(textureWidth, textureHeight);
        setRotation(flask3, 0F, 0F, 0F);
        flask3.addBox(10, 7, 1, 5, 6, 5);

        flask4 = new ModelRenderer(this, 20, 18);
        flask4.setTextureSize(textureWidth, textureHeight);
        setRotation(flask4, 0F, 0F, 0F);
        flask4.addBox(10, 7, 10, 5, 6, 5);

        filterStand = new ModelRenderer(this, 0, 29);
        filterStand.setTextureSize(textureWidth, textureHeight);
        setRotation(filterStand, 0F, 0F, 0F);
        filterStand.addBox(5, 2, 7, 2, 1, 2);
        filterStand.addBox(7, 2, 5, 2, 1, 6);
        filterStand.addBox(9, 2, 7, 2, 1, 2);

        filter = new ModelRenderer(this, 40, 18);
        filter.setTextureSize(textureWidth, textureHeight);
        setRotation(filter, 0F, 0F, 0F);
        filter.addBox(7, 3, 7, 2, 5, 2);

        tube1 = new ModelRenderer(this, 0, 29);
        tube1.setTextureSize(textureWidth, textureHeight);
        setRotation(tube1, 0F, 0F, 0F);
        tube1.addBox(3, 2, 3, 1, 4, 1);
        tube1.addBox(4, 2, 3, 4, 1, 1);
        tube1.addBox(7, 2, 4, 1, 1, 1);

        tube2 = new ModelRenderer(this, 0, 29);
        tube2.setTextureSize(textureWidth, textureHeight);
        setRotation(tube2, 0F, 0F, 0F);
        tube2.addBox(3, 2, 12, 1, 4, 1);
        tube2.addBox(4, 2, 12, 4, 1, 1);
        tube2.addBox(7, 2, 11, 1, 1, 1);

        tube3 = new ModelRenderer(this, 0, 29);
        tube3.setTextureSize(textureWidth, textureHeight);
        setRotation(tube3, 0F, 0F, 0F);
        tube3.addBox(11, 2, 8, 2, 1, 1);
        tube3.addBox(12, 2, 9, 1, 1, 4);
        tube3.addBox(12, 3, 12, 1, 3, 1);

        tube4 = new ModelRenderer(this, 0, 29);
        tube4.setTextureSize(textureWidth, textureHeight);
        setRotation(tube4, 0F, 0F, 0F);
        tube4.addBox(11, 2, 7, 2, 1, 1);
        tube4.addBox(12, 2, 3, 1, 1, 4);
        tube4.addBox(12, 3, 3, 1, 3, 1);

        potion1 = new ModelRenderer(this, 24, 29);
        potion1.setTextureSize(textureWidth, textureHeight);
        setRotation(potion1, 0F, 0F, 0F);
        potion1.addBox(2, 8, 2, 3, 3, 3);

        potion2 = new ModelRenderer(this, 24, 29);
        potion2.setTextureSize(textureWidth, textureHeight);
        setRotation(potion2, 0F, 0F, 0F);
        potion2.addBox(2, 8, 11, 3, 3, 3);

        potion3 = new ModelRenderer(this, 24, 29);
        potion3.setTextureSize(textureWidth, textureHeight);
        setRotation(potion3, 0F, 0F, 0F);
        potion3.addBox(11, 8, 2, 3, 3, 3);

        potion4 = new ModelRenderer(this, 24, 29);
        potion4.setTextureSize(textureWidth, textureHeight);
        setRotation(potion4, 0F, 0F, 0F);
        potion4.addBox(11, 8, 11, 3, 3, 3);

    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, ItemStack[] stacks)
    {

        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        platform.render(f5);

        stand1.render(f5);
        stand2.render(f5);
        stand3.render(f5);
        stand4.render(f5);

        tube1.render(f5);
        tube2.render(f5);
        tube3.render(f5);
        tube4.render(f5);

        if (stacks[0] != null && stacks[0].itemID == Item.potion.itemID)
        {

            float r, g, b;
            r = (PotionHelper.func_77915_a(stacks[0].getItemDamage(), false) >> 16 & 255) / 255.0F;
            g = (PotionHelper.func_77915_a(stacks[0].getItemDamage(), false) >> 8 & 255) / 255.0F;
            b = (PotionHelper.func_77915_a(stacks[0].getItemDamage(), false) >> 0 & 255) / 255.0F;
            GL11.glColor4f(r, g, b, 0.8f);
            potion1.render(f5);

        }

        if (stacks[1] != null && stacks[1].itemID == Item.potion.itemID)
        {

            float r, g, b;
            r = (PotionHelper.func_77915_a(stacks[1].getItemDamage(), false) >> 16 & 255) / 255.0F;
            g = (PotionHelper.func_77915_a(stacks[1].getItemDamage(), false) >> 8 & 255) / 255.0F;
            b = (PotionHelper.func_77915_a(stacks[1].getItemDamage(), false) >> 0 & 255) / 255.0F;
            GL11.glColor4f(r, g, b, 0.8f);
            potion2.render(f5);

        }

        if (stacks[3] != null && stacks[3].itemID == Item.potion.itemID)
        {

            float r, g, b;
            r = (PotionHelper.func_77915_a(stacks[3].getItemDamage(), false) >> 16 & 255) / 255.0F;
            g = (PotionHelper.func_77915_a(stacks[3].getItemDamage(), false) >> 8 & 255) / 255.0F;
            b = (PotionHelper.func_77915_a(stacks[3].getItemDamage(), false) >> 0 & 255) / 255.0F;
            GL11.glColor4f(r, g, b, 0.8f);
            potion3.render(f5);

        }

        if (stacks[4] != null && stacks[4].itemID == Item.potion.itemID)
        {

            float r, g, b;
            r = (PotionHelper.func_77915_a(stacks[4].getItemDamage(), false) >> 16 & 255) / 255.0F;
            g = (PotionHelper.func_77915_a(stacks[4].getItemDamage(), false) >> 8 & 255) / 255.0F;
            b = (PotionHelper.func_77915_a(stacks[4].getItemDamage(), false) >> 0 & 255) / 255.0F;
            GL11.glColor4f(r, g, b, 0.8f);
            potion4.render(f5);

        }

        GL11.glColor4f(1f, 1f, 1f, 1f);
        filterStand.render(f5);
        if (stacks[2] != null && stacks[2].itemID == ItemRegistry.appItemFilter.itemID)
        {
            filter.render(f5);
        }

        flask1.render(f5);
        flask2.render(f5);
        flask3.render(f5);
        flask4.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
    }

}
