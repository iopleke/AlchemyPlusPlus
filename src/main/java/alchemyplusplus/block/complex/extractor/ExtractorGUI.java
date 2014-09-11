package alchemyplusplus.block.complex.extractor;

import alchemyplusplus.reference.Textures;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class ExtractorGUI extends GuiContainer
{

    private ExtractorTileEntity tileEntity;

    public ExtractorGUI(InventoryPlayer inventoryPlayer,
            ExtractorTileEntity tileEntity)
    {
        super(new ExtractorContainer(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {

        this.mc.renderEngine.bindTexture(Textures.Gui.EXTRACTOR);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        int fuel = this.tileEntity.fuel;
        fuel /= 4;

        this.drawTexturedModalRect(x + 86, y + 72 - ((int) (fuel * 0.16f)), 176, 38, 2, (int) (fuel * 0.16f));

        int percentage = (int) ((this.tileEntity.extractingTicks / 400f) * 100);

        if (percentage == 0)
        {
            return;
        }

        if (percentage <= 36)
        {
            this.drawTexturedModalRect(x + 70, y + 35 - (percentage / 2), 176, 2, 1, (percentage / 2));
        } else
        {
            this.drawTexturedModalRect(x + 70, y + 17, 176, 2, 1, 18);
        }

        if (percentage > 36 && percentage <= 50)
        {
            this.drawTexturedModalRect(x + 71, y + 15, 177, 0, (percentage - 36) / 2, 3);
        } else if (percentage > 50)
        {
            this.drawTexturedModalRect(x + 71, y + 15, 177, 0, 7, 3);
        }

        if (percentage > 50 && percentage <= 64)
        {
            this.drawTexturedModalRect(x + 98, y + 17, 176, 20, (percentage - 50) / 2, 1);
        } else if (percentage > 64)
        {
            this.drawTexturedModalRect(x + 98, y + 17, 176, 20, 7, 1);
        }

        if (percentage > 64)
        {
            this.drawTexturedModalRect(x + 105, y + 17, 183, 20, 3, (percentage - 64) / 2);
        }

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2)
    {

        fontRendererObj.drawString("Extractor", 8, 6, 4210752);

        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

}
