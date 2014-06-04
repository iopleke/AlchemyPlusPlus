package mokonaDesu.alchemypp.model;

import org.lwjgl.opengl.GL11;

import mokonaDesu.alchemypp.items.ItemRegistry;
import mokonaDesu.alchemypp.tileentities.ApparatusApplicationBottleStand;
import mokonaDesu.alchemypp.tileentities.TileEntityAlchemicalApparatus;
import mokonaDesu.alchemypp.tileentities.TileEntityLiquidMixer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;

public class ModelSprayer extends APPAlchemicalApparatusModel
{

	//fields
ModelRenderer sprayer;	
ModelRenderer tube;
ModelRenderer sprayerOff;

  public ModelSprayer()
  {
    textureWidth = 64;
    textureHeight = 64;
       
   }


  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, TileEntityAlchemicalApparatus tileEntity)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
          
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    
    if (tileEntity.stand != null && tileEntity.stand instanceof ApparatusApplicationBottleStand && ((ApparatusApplicationBottleStand)tileEntity.stand).stack != null) {
    sprayer = new ModelRenderer(this, 32, 0);
    sprayer.setTextureSize(this.textureWidth, this.textureHeight);
    setRotation(sprayer, 0f, 0f, 0f);
    sprayer.addBox(6, 13, 6, 4, 2, 4);
    sprayer.render(f5);
    
    tube = new ModelRenderer(this, 24, 8);
    tube.setTextureSize(this.textureWidth, this.textureHeight);
    setRotation(tube, 0f, 0f, 0f);
    tube.addBox(7.5f, 9, 7.5f, 1, 5, 1);
    tube.render(f5);
    
    } else {
    sprayerOff = new ModelRenderer(this, 32, 0);
    sprayerOff.setTextureSize(this.textureWidth, this.textureHeight);
    setRotation(sprayerOff, 0f, 0f, 0f);
    sprayerOff.addBox(6, 7, 6, 4, 2, 4);
    sprayerOff.render(f5);    
    }

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
