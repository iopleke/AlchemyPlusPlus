package mokonaDesu.alchemypp.gui;

import mokonaDesu.alchemypp.tileentities.ContainerDistillery;
import mokonaDesu.alchemypp.tileentities.TileEntityDistillery;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiDistillery extends GuiContainer {

    private final TileEntityDistillery tileEntity;

    public GuiDistillery(InventoryPlayer inventoryPlayer,
            TileEntityDistillery tileEntity) {
        super(new ContainerDistillery(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {

        fontRenderer.drawString("Distillery", 8, 6, 4210752);

        fontRenderer.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
            int par3) {

        this.mc.renderEngine.bindTexture(new ResourceLocation(
                "AlchemyPP:textures/gui/distillery.png"));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        // The amount of fuel remaining for this particular burn
        int fuel = this.tileEntity.fuel;
        // The total amount of fuel the burning item provides
        int burntotal = this.tileEntity.burntotal;

        int burnpercent = 0;
        int burntextureheight = 0;
        if (burntotal > 0 && fuel != 0) {
            burnpercent = (int) (((double) fuel / (double) burntotal) * 100);
            burntextureheight = (int) (14 * ((double) burnpercent / 100));
        }

        fuel /= 4;

        this.drawTexturedModalRect(x + 62, y + 55 - burntextureheight, 176,
                52 - burntextureheight, 14, burntextureheight);

        int processing = (int) ((this.tileEntity.distillingTicks / 400f) * 100);

        if (processing == 0)
            return;

        if (processing <= 36)
            this.drawTexturedModalRect(x + 66, y + 8 - (processing / 2), 176,
                    2, 1, (processing / 2));
        else
            this.drawTexturedModalRect(x + 70, y + 17, 176, 2, 1, 18);

        if (processing > 36 && processing <= 50) {
            this.drawTexturedModalRect(x + 71, y + 15, 177, 0,
                    (processing - 36) / 2, 3);
        } else if (processing > 50) {
            this.drawTexturedModalRect(x + 71, y + 15, 177, 0, 7, 3);
        }

        if (processing > 50 && processing <= 64) {
            this.drawTexturedModalRect(x + 98, y + 17, 176, 20,
                    (processing - 50) / 2, 1);
        } else if (processing > 64) {
            this.drawTexturedModalRect(x + 98, y + 17, 176, 20, 7, 1);
        }

        if (processing > 64) {
            this.drawTexturedModalRect(x + 105, y + 17, 183, 20, 3,
                    (processing - 64) / 2);
        }

    }
}