package alchemyplusplus.tileentities.distillery;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiDistillery extends GuiContainer
{

    private final TileEntityDistillery tileEntity;

    public GuiDistillery(InventoryPlayer inventoryPlayer,
            TileEntityDistillery tileEntity)
    {
        super(new ContainerDistillery(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {

        this.mc.renderEngine.bindTexture(new ResourceLocation(
                "AlchemyPlusPlus:assets/alchemyplusplus/gui/distillery.png"));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        // The amount of fuel remaining for this particular burn
        int fuel = this.tileEntity.fuel;
        // The total amount of fuel the burning item provides
        int burntotal = this.tileEntity.burntotal;

        int burnpercent = 0;
        int burntextureheight = 0;
        if (burntotal > 0 && fuel != 0)
        {
            burnpercent = (int) (((double) fuel / (double) burntotal) * 100);
            burntextureheight = (int) (14 * ((double) burnpercent / 100));
        }

        fuel /= 4;

        this.drawTexturedModalRect(x + 62, y + 55 - burntextureheight, 189,
                14 - burntextureheight, 14, burntextureheight);

        int processing = (int) ((this.tileEntity.distillingTicks / 400f) * 100);

        this.drawTexturedModalRect(x + 37 + 22
                - (int) ((float) processing / 100 * 22), y + 23,
                176 + 22 - (int) ((float) processing / 100 * 22), 42,
                (int) ((float) processing / 100 * 22), 16);
        if (processing == 0)
        {
            return;
        }

        if (processing <= 24)
        {
            this.drawTexturedModalRect(x + 66, y + 21 - (processing / 2), 176,
                    2, 1, (processing / 2));
        } else
        {
            this.drawTexturedModalRect(x + 66, y + 9, 176, 2, 1, 12);
        }

        if (processing > 24 && processing <= 48)
        {
            this.drawTexturedModalRect(x + 67, y + 7, 177, 0,
                    (processing - 24) / 2, 3);
        } else if (processing > 48)
        {
            this.drawTexturedModalRect(x + 67, y + 7, 177, 0, 12, 3);
        }

        if (processing > 48 && processing <= 72)
        {
            this.drawTexturedModalRect(x + 112, y + 9, 176, 14,
                    (processing - 48) / 2, 1);
        } else if (processing > 72)
        {
            this.drawTexturedModalRect(x + 112, y + 9, 176, 14, 12, 1);
        }

        if (processing > 72)
        {
            this.drawTexturedModalRect(x + 124, y + 9, 188, 14, 3,
                    (processing - 72));
        }

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2)
    {

        fontRenderer.drawString("Distillery", 8, 6, 4210752);

        fontRenderer.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                ySize - 96 + 2, 4210752);
    }
}
