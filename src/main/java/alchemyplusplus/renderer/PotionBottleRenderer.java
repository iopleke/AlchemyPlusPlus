package alchemyplusplus.renderer;

import alchemyplusplus.tileentities.potionjug.ItemPotionJug;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class PotionBottleRenderer implements IItemRenderer
{

    public static RenderItem renderer = new RenderItem();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type == ItemRenderType.INVENTORY;
    }

    public void renderIIconColored(int par1, int par2, IIcon par3IIcon, int par4, int par5, int color)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color >> 0 & 255) / 255.0F, 1f);
        tessellator.addVertexWithUV((double) (par1 + 0), (double) (par2 + par5), 0, (double) par3IIcon.getMinU(), (double) par3IIcon.getMaxV());
        tessellator.addVertexWithUV((double) (par1 + par4), (double) (par2 + par5), 0, (double) par3IIcon.getMaxU(), (double) par3IIcon.getMaxV());
        tessellator.addVertexWithUV((double) (par1 + par4), (double) (par2 + 0), 0, (double) par3IIcon.getMaxU(), (double) par3IIcon.getMinV());
        tessellator.addVertexWithUV((double) (par1 + 0), (double) (par2 + 0), 0, (double) par3IIcon.getMinU(), (double) par3IIcon.getMinV());
        tessellator.draw();
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {

        IIcon icon = ItemPotionJug.bottle;
        IIcon icon2 = ItemPotionJug.contents;

        if (item.hasTagCompound())
        {
            int has = item.getTagCompound().getShort("containerHas");
            if (has > 0)
            {

                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDepthMask(false);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                int color = item.getTagCompound().getShort("effectID");
                int starting = 14 - ((int) (10.0f * (has / 16.0f)));
                renderIIconColored(0, starting, icon2, 16, 16 - starting, PotionHelper.func_77915_a(color, false));
                renderer.renderIcon(0, 0, icon, 16, 16);

                GL11.glDepthMask(true);
                GL11.glDisable(GL11.GL_BLEND);

            }
        } else
        {
            renderer.renderIcon(0, 0, icon, 16, 16);
        }

    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

}
