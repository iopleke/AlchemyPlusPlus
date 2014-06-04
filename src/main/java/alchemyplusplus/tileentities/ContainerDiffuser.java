package alchemyplusplus.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerDiffuser extends Container {

    private final TileEntityDiffuser diffuser;
    public final int fluidLevel = 0;
    public final boolean isDiffusing = false;

    public ContainerDiffuser(InventoryPlayer playerInv, TileEntityDiffuser diffuser) {
        this.diffuser = diffuser;

        this.addSlotToContainer(new Slot(diffuser, 0, 0, 0)); // input

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }

    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.diffuser.fluidLevel);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return false;
    }

}
