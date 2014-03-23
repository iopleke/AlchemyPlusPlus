package mokonaDesu.alchemypp.renderer;

import mokonaDesu.alchemypp.blocks.BlockRegistry;
import mokonaDesu.alchemypp.model.ModelDiffuser;
import mokonaDesu.alchemypp.tileentities.TileEntityDiffuser;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class TileEntityDiffuserRenderer extends TileEntitySpecialRenderer {

    ModelDiffuser model = new ModelDiffuser();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        TileEntityDiffuser tileEntityYour = (TileEntityDiffuser) tileEntity;
        render(tileEntityYour, tileEntity.worldObj, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, BlockRegistry.appBlockDiffuser);
        GL11.glPopMatrix();
    }

    public void render(TileEntityDiffuser tl, World world, int i, int j, int k, Block block) {

        float f = block.getBlockBrightness(world, i, j, k);
        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);

        int dir = world.getBlockMetadata(i, j, k);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0, 0.5F);
        GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
        GL11.glTranslatef(-0.5F, 0, -0.5F);

        bindTexture(new ResourceLocation("AlchemyPP:textures/blocks/Diffuser.png"));
        this.model.isDiffusing = tl.isDiffuserActive();
        this.model.potionColor = PotionHelper.func_77915_a(2, false);
        this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }
}