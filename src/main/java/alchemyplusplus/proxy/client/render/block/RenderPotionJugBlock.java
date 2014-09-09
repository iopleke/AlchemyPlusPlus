package alchemyplusplus.proxy.client.render.block;

import alchemyplusplus.block.BlockRegistry;
import alchemyplusplus.reference.Textures;
import alchemyplusplus.tileentities.potionjug.ModelPotionJug;
import alchemyplusplus.tileentities.potionjug.TileEntityPotionJug;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderPotionJugBlock extends TileEntitySpecialRenderer
{

    ModelPotionJug model = new ModelPotionJug();

    public void render(TileEntityPotionJug tl, World world, int i, int j, int k, Block block)
    {
        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1,
                l2);

        int dir = world.getBlockMetadata(i, j, k);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0, 0.5F);
        GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
        GL11.glTranslatef(-0.5F, 0, -0.5F);

        bindTexture(Textures.Model.POTION_JUG);

        float percentage = ((float) tl.containerHas) / tl.containerMax;
        int color = 0;
        if (percentage > 0)
        {
            color = PotionHelper.func_77915_a(tl.potionID, false);
        }

        this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F,
                0.0625F, percentage, color);

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        TileEntityPotionJug tileEntityYour = (TileEntityPotionJug) tileEntity;
        render(tileEntityYour, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, BlockRegistry.potionJug);
        GL11.glPopMatrix();
    }
}
