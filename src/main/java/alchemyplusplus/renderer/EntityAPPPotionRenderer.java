package mokonaDesu.alchemypp.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class EntityAPPPotionRenderer extends RenderSnowball {

	private Item item;
	private int damage;
	
	public EntityAPPPotionRenderer(Item item, int damage) {
		super(item, damage);
		this.item = item;
		this.damage = damage;
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2,
			float f, float f1) {

		  Icon icon = this.item.getIconFromDamage(this.damage);
		  System.out.println("lol!");
	        if (icon != null)
	        {
	            GL11.glPushMatrix();
	            GL11.glTranslatef((float)d0, (float)d1, (float)d2);
	            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	            GL11.glScalef(0.5F, 0.5F, 0.5F);
	            this.doRender(entity, d0, d1, d2, f, f1);
	            //func_110777_b(entity);
	            Tessellator tessellator = Tessellator.instance;

	            if (icon == ItemPotion.func_94589_d("bottle_splash"))
	            {
	                int i = PotionHelper.func_77915_a(((EntityPotion)entity).getPotionDamage(), false);
	                float f2 = (float)(i >> 16 & 255) / 255.0F;
	                float f3 = (float)(i >> 8 & 255) / 255.0F;
	                float f4 = (float)(i & 255) / 255.0F;
	                GL11.glColor3f(f2, f3, f4);
	                GL11.glPushMatrix();
	                this.func_77026_a(tessellator, ItemPotion.func_94589_d("overlay"));
	                GL11.glPopMatrix();
	                GL11.glColor3f(1.0F, 1.0F, 1.0F);
	            }

	            this.func_77026_a(tessellator, icon);
	            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	            GL11.glPopMatrix();
	        }
		
	}
	
	  private void func_77026_a(Tessellator par1Tessellator, Icon par2Icon)
	    {
	        float f = par2Icon.getMinU();
	        float f1 = par2Icon.getMaxU();
	        float f2 = par2Icon.getMinV();
	        float f3 = par2Icon.getMaxV();
	        float f4 = 1.0F;
	        float f5 = 0.5F;
	        float f6 = 0.25F;
	        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	        par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
	        par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
	        par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
	        par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
	        par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
	        par1Tessellator.draw();
	    }

}
