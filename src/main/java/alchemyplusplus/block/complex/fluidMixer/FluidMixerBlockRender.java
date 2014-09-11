package alchemyplusplus.block.complex.fluidMixer;

import alchemyplusplus.BlockRegistry;
import alchemyplusplus.reference.Textures;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerModel;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class FluidMixerBlockRender extends TileEntitySpecialRenderer
{

    FluidMixerModel model = new FluidMixerModel();

    public void render(FluidMixerTileEntity tl, World world, int i, int j, int k, Block block)
    {
        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);

        int dir = world.getBlockMetadata(i, j, k);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0, 0.5F);
        GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
        GL11.glTranslatef(-0.5F, 0, -0.5F);

        bindTexture(Textures.Model.LIQUID_MIXER);

        this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, tl.mixerInventory);

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        FluidMixerTileEntity tileEntityYour = (FluidMixerTileEntity) tileEntity;
        render(tileEntityYour, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, BlockRegistry.liquidMixer);
        GL11.glPopMatrix();
    }
}
