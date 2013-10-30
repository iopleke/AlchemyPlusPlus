package mokonaDesu.alchemypp.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerExtractor extends Container {

	private TileEntityExtractor 	extractor;
	private int						extractingTicks = 0;
	private int						fuel = 0;
	
	 public ContainerExtractor(InventoryPlayer playerInv, TileEntityExtractor extractor)
	    {
		this.extractor = extractor;

     this.addSlotToContainer(new Slot(extractor, 0, 80, 13));	//ingridient
     this.addSlotToContainer(new Slot(extractor, 1, 67, 37));	//in
     this.addSlotToContainer(new Slot(extractor, 2, 94, 37));	//out
     this.addSlotToContainer(new SlotAlchemicalFuel(extractor, 3, 67, 56));	//fuel
     
     int i;

     for (i = 0; i < 3; ++i)
     {
         for (int j = 0; j < 9; ++j)
         {
             this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
     }

     for (i = 0; i < 9; ++i)
     {
         this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
     }
	    
	    }
	 
	    public void addCraftingToCrafters(ICrafting par1ICrafting)
	    {
	        super.addCraftingToCrafters(par1ICrafting);
	        par1ICrafting.sendProgressBarUpdate(this, 0, this.extractor.extractingTicks);
	        par1ICrafting.sendProgressBarUpdate(this, 1, this.extractor.fuel);
	    }
	    
	    public void detectAndSendChanges()
	    {
	        super.detectAndSendChanges();

	        for (int i = 0; i < this.crafters.size(); ++i)
	        {
	            ICrafting icrafting = (ICrafting)this.crafters.get(i);

	            if (this.extractingTicks != this.extractor.extractingTicks)
	            {
	                icrafting.sendProgressBarUpdate(this, 0, this.extractor.extractingTicks);
	            }
	            
	            if (this.fuel != this.extractor.fuel)
	            {
	                icrafting.sendProgressBarUpdate(this, 1, this.extractor.fuel);
	            }
	        }

	        this.extractingTicks = this.extractor.extractingTicks;
	        this.fuel = this.extractor.fuel;
	    }
	    
	    public void updateProgressBar(int id, int value)
	    {
	        if (id == 0)
	        {
	            this.extractor.extractingTicks = value;
	        } else
	        if (id == 1)
	        {
	        	this.extractor.fuel = value;
	        }
	        
	    }
	    
	    
	 
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
			return true;
	}

	 public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	    {
			return null; 
		}
	
}
