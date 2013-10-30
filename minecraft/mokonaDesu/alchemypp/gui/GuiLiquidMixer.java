package mokonaDesu.alchemypp.gui;

import org.lwjgl.opengl.GL11;

import mokonaDesu.alchemypp.tileentities.ContainerLiquidMixer;
import mokonaDesu.alchemypp.tileentities.TileEntityLiquidMixer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;


public class GuiLiquidMixer extends GuiContainer {

		private TileEntityLiquidMixer tileEntity;
	
        public GuiLiquidMixer (InventoryPlayer inventoryPlayer,
                        TileEntityLiquidMixer tileEntity) {
                //the container is instanciated and passed to the superclass for handling
                super(new ContainerLiquidMixer(inventoryPlayer, tileEntity));
                this.tileEntity = tileEntity;
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int param1, int param2) {
                //draw text and stuff here
                //the parameters for drawString are: string, x, y, color
                fontRenderer.drawString("Liquid Mixer", 8, 6, 4210752);
                //draws "Inventory" or your regional equivalent
                fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                        int par3) {
                //draw your Gui here, only thing you need to change is the path

                this.mc.renderEngine.func_110577_a(new ResourceLocation("AlchemyPP:textures/gui/mixer.png"));
                int x = (width - xSize) / 2;
                int y = (height - ySize) / 2;
                this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
                
                //adding progress bar
                this.drawTexturedModalRect(x + 71, y + 19, 176, 0, tileEntity.getProgressPixels(), 42);
        }

}