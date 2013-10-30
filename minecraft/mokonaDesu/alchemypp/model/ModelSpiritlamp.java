package mokonaDesu.alchemypp.model;

import org.lwjgl.opengl.GL11;

import mokonaDesu.alchemypp.items.ItemRegistry;
import mokonaDesu.alchemypp.tileentities.TileEntityAlchemicalApparatus;
import mokonaDesu.alchemypp.tileentities.ApparatusApplicationSpiritLamp;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;

public class ModelSpiritlamp extends APPModel
{

	//fields
ModelRenderer bottle;	
ModelRenderer neck;
ModelRenderer liquid;


  public ModelSpiritlamp()
  {
    textureWidth = 64;
    textureHeight = 64;
       
   }


  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, TileEntityAlchemicalApparatus tileEntity)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    
    GL11.glEnable(GL11.GL_NORMALIZE);
    GL11.glEnable(GL11.GL_BLEND);
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    
    int spirit = ((ApparatusApplicationSpiritLamp)tileEntity.bottom).spirit;
    boolean active = ((ApparatusApplicationSpiritLamp)tileEntity.bottom).active;
    
    if (spirit > 0) {
        liquid = new ModelRenderer(this, 0, 24);
        liquid.setTextureSize(textureWidth, textureHeight);
        setRotation(liquid, 0F, 0F, 0F);
        int height = spirit == 60 ? 3 : spirit >= 40 ? 2 : spirit >= 20 ? 1 : 0;
        liquid.addBox(6.5F, 0.5F, 6.5F, 3, height, 3); 
        liquid.render(f5);   
      }
    
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    bottle = new ModelRenderer(this, active ? 16 : 0, 0);
    bottle.setTextureSize(textureWidth, textureHeight);
    setRotation(bottle, 0F, 0F, 0F);
    bottle.addBox(6, 0, 6, 4, 4, 4);
    bottle.render(f5);
    
    neck = new ModelRenderer(this, active ? 8 : 0, 8);
    neck.setTextureSize(textureWidth, textureHeight);
    setRotation(neck, 0F, 0F, 0F);
    neck.addBox(7, 4, 7, 2, 1, 2);
    neck.render(f5);
    
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
