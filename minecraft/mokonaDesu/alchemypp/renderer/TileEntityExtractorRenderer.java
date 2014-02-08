package mokonaDesu.alchemypp.renderer;

import mokonaDesu.alchemypp.AlchemyPP;
import mokonaDesu.alchemypp.blocks.BlockRegistry;
import mokonaDesu.alchemypp.model.ModelExtractor;
import mokonaDesu.alchemypp.tileentities.TileEntityExtractor;
import net.minecraft.block.Block;
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

public class TileEntityExtractorRenderer extends TileEntitySpecialRenderer {

	ModelExtractor model = new ModelExtractor();

    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d, (float)d1, (float)d2);
         TileEntityExtractor tileEntityYour = (TileEntityExtractor)tileEntity;
         render(tileEntityYour, tileEntity.worldObj, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, BlockRegistry.appBlockExtractor);
         GL11.glPopMatrix();
    }
    

    public void render(TileEntityExtractor tl, World world, int i, int j, int k, Block block) {

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
     
         if (AlchemyPP.alternativeTextures) bindTexture(new ResourceLocation("AlchemyPP:textures/blocks/ExtractorAlt.png"));
         else bindTexture(new ResourceLocation("AlchemyPP:textures/blocks/Extractor.png"));
        	 
         this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
       
         GL11.glPopMatrix();
    }
}