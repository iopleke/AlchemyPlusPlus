package alchemyplusplus.proxy.client.render.block;

import alchemyplusplus.block.BlockRegistry;
import alchemyplusplus.tileentities.diffuser.ModelDiffuser;
import alchemyplusplus.tileentities.diffuser.TileEntityDiffuser;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class RenderDiffuserBlock extends TileEntitySpecialRenderer
{

    ModelDiffuser diffuserModel = new ModelDiffuser();

    public void render(TileEntityDiffuser diffuserTE, World world, int i, int j, int k, Block block)
    {
        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);

        int dir = world.getBlockMetadata(i, j, k);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0, 0.5F);
        GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
        GL11.glTranslatef(-0.5F, 0, -0.5F);

        bindTexture(new ResourceLocation("AlchemyPlusPlus:textures/blocks/diffuser.png"));
        this.diffuserModel.isDiffusing = diffuserTE.isDiffuserActive();
        this.diffuserModel.potionDamage = diffuserTE.bottleColor;
        this.diffuserModel.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity diffuserTE, double d, double d1, double d2, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        TileEntityDiffuser tileEntityYour = (TileEntityDiffuser) diffuserTE;
        render(tileEntityYour, diffuserTE.getWorldObj(), diffuserTE.xCoord, diffuserTE.yCoord, diffuserTE.zCoord, BlockRegistry.diffuser);
        GL11.glPopMatrix();
    }
}
