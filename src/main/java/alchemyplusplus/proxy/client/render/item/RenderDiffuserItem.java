package alchemyplusplus.proxy.client.render.item;

import alchemyplusplus.reference.Textures;
import alchemyplusplus.tileentities.diffuser.ModelDiffuser;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderDiffuserItem implements IItemRenderer
{
    private final ModelDiffuser modelDiffuser;

    public RenderDiffuserItem()
    {
        this.modelDiffuser = new ModelDiffuser();
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.4F, 1.4F, 0.3F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(0.07F, 0.07F, 0.07F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Model.DIFFUSER);
        
        this.modelDiffuser.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 1);
        
        GL11.glPopMatrix();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }
}
