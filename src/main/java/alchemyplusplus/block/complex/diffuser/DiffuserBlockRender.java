package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.registry.BlockRegistry;
import alchemyplusplus.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class DiffuserBlockRender extends TileEntitySpecialRenderer
{

    DiffuserModel diffuserModel = new DiffuserModel();

    public void render(DiffuserTileEntity diffuserTE, World world, int posX, int posY, int posZ, Block block)
    {
        int l = world.getLightBrightnessForSkyBlocks(posX, posY, posZ, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);

        int dir = world.getBlockMetadata(posX, posY, posZ);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0, 0.5F);
        GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
        GL11.glTranslatef(-0.5F, 0, -0.5F);

        bindTexture(Textures.Model.DIFFUSER);
        this.diffuserModel.isDiffusing = diffuserTE.isDiffuserActive();
        this.diffuserModel.potionDamage = diffuserTE.bottleColor;
        this.diffuserModel.fluidAmount = diffuserTE.fluidTank.getFluidAmount();
        this.diffuserModel.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float partialTick)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) xCoord, (float) yCoord, (float) zCoord);
        DiffuserTileEntity diffuser = (DiffuserTileEntity) tileEntity;
        this.render(diffuser, diffuser.getWorldObj(), diffuser.xCoord, diffuser.yCoord, diffuser.zCoord, BlockRegistry.diffuser);
        GL11.glPopMatrix();
    }
}
