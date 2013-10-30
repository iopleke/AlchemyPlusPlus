package mokonaDesu.alchemypp.renderer;

import mokonaDesu.alchemypp.blocks.BlockRegistry;
import mokonaDesu.alchemypp.model.APPModel;
import mokonaDesu.alchemypp.model.ModelBottleStand;
import mokonaDesu.alchemypp.model.ModelLiquidMixer;
import mokonaDesu.alchemypp.model.ModelSpiritlamp;
import mokonaDesu.alchemypp.model.ModelSprayer;
import mokonaDesu.alchemypp.tileentities.ApparatusApplicationSpiritLamp;
import mokonaDesu.alchemypp.tileentities.TileEntityAlchemicalApparatus;
import mokonaDesu.alchemypp.tileentities.TileEntityLiquidMixer;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class TileEntityAlchemicalApparatusRenderer extends TileEntitySpecialRenderer {

	//ModelSpiritlamp modelLamp = new ModelSpiritlamp();
	//ModelBottleStand modelStand = new ModelBottleStand();
	//ModelSprayer modelSprayer = new ModelSprayer();

	APPModel[] modelList = {
			new ModelSpiritlamp(),
			new ModelBottleStand(),
			new ModelSprayer()
	};
		
    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d, (float)d1, (float)d2);
         TileEntityAlchemicalApparatus tileEntityYour = (TileEntityAlchemicalApparatus)tileEntity;
         render(tileEntityYour, tileEntity.worldObj, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, BlockRegistry.appBlockLiquidMixer);
         GL11.glPopMatrix();
    }
    

    public void render(TileEntityAlchemicalApparatus tl, World world, int i, int j, int k, Block block) {

        float f = block.getBlockBrightness(world, i, j, k);
        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)l1, (float)l2);
       
        int dir = world.getBlockMetadata(i, j, k);
       
         GL11.glPushMatrix();
         GL11.glTranslatef(0.5F, 0, 0.5F);
         GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
         GL11.glTranslatef(-0.5F, 0, -0.5F);
     
         func_110628_a(new ResourceLocation("AlchemyPP:textures/blocks/Apparatus.png"));

         if (tl.bottom != null)
        	 this.modelList[tl.bottom.getModelCode()].render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, tl);
         
         if (tl.stand != null)
             this.modelList[tl.stand.getModelCode()].render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, tl);
             
         if (tl.upper != null)
             this.modelList[tl.upper.getModelCode()].render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, tl);
             
         GL11.glPopMatrix();
    }
}