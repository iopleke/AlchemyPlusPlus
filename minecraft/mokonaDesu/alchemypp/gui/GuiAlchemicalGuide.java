package mokonaDesu.alchemypp.gui;

import java.util.zip.ZipFile;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

public class GuiAlchemicalGuide extends GuiScreen {
	
	private int bookmark = 0;
	private int currentPage = 0;
	private ItemStack bookStack;
		
	private static final int xPage = 146;
	private static final int yPage = 180;
	
	private GuiButton nextPage;
	private GuiButton previousPage;
	private GuiButton bookmarkPage;
	
	 public GuiAlchemicalGuide(EntityPlayer player, ItemStack stack)
	    {
		 if (stack.hasTagCompound()) {
			 this.bookmark = stack.getTagCompound().getShort("bookmark");
			 this.currentPage = bookmark;
		 }
		 
		 this.bookStack = stack;
	}

	 public FontRenderer getFontRenderer() {
		 return this.fontRenderer;
	 }
	 
	 public TextureManager getRenderEngine() {
		 return this.mc.renderEngine;
	 }
	 
	 public void drawScreen(int par1, int par2, float par3) {
		 	this.drawDefaultBackground();
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        //Bind Left Texture
	        this.mc.renderEngine.bindTexture(new ResourceLocation("AlchemyPP:textures/gui/bookLeft.png"));
	        this.drawTexturedModalRect((this.width / 2) - xPage , (this.height - this.yPage) / 2, 0, 0, this.xPage, this.yPage);
	       
	        //Bind Right Texture
	        this.mc.renderEngine.bindTexture(new ResourceLocation("AlchemyPP:textures/gui/bookRight.png"));
	        this.drawTexturedModalRect((this.width / 2), (this.height - this.yPage) / 2, 0, 0, this.xPage, this.yPage);

	       if (Book.alchemicalGude.isLoaded()) {
	    	   Book.alchemicalGude.getPage(currentPage).drawPage(this);
	       } else {
	    	   this.fontRenderer.drawString("All the pages", (this.width - this.xPage) / 2 - 15, this.height / 2, 0);
	    	   this.fontRenderer.drawString(" are empty...", (this.width - this.xPage) / 2, this.height / 2 + 8, 0);	  
	       }
	       
	       super.drawScreen(par1, par2, par3);
	    }
	 
	 public void initGui()
	    {
		  this.buttonList.clear();
		  
		  this.buttonList.add(this.nextPage = new GuiButton(0, 10, 100, 50, 20, "Next"));
		  this.buttonList.add(this.previousPage = new GuiButton(1, 10, 130, 50, 20, "Previous"));
		  this.buttonList.add(this.bookmarkPage = new GuiButton(2, 10, 160, 50, 20, "Bookmark"));
	   
		  this.updateButtons();
	    }
	 
	 public boolean doesGuiPauseGame() {
		 return false;
	 }
	 
	 private void updateButtons() {
		if (!Book.alchemicalGude.isLoaded()) {
			this.previousPage.drawButton = this.nextPage.drawButton = this.bookmarkPage.drawButton = false;
			return;
		}
		
		this.previousPage.drawButton = !(this.currentPage == 0);
		this.nextPage.drawButton = !(this.currentPage == Book.alchemicalGude.pageAmount - 1);
		this.bookmarkPage.drawButton = this.bookmark != this.currentPage;
	 }
	 
	 protected void actionPerformed(GuiButton button)
	    {
		 if (button.id == 0)
			 this.currentPage++;
		 else if (button.id == 1)
			 this.currentPage--;
		 else if (button.id == 2) {
			 this.bookmark();
		 }
			this.updateButtons();
	    }
	 
	 public void bookmark() {
		 if (!this.bookStack.hasTagCompound()) {
			 this.bookStack.setTagCompound(new NBTTagCompound());
		 }
		 this.bookStack.getTagCompound().setShort("bookmark", (short) this.currentPage);
	 }

}
