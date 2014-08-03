package alchemyplusplus.tileentities.apparatus;

import alchemyplusplus.items.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ApparatusApplicationSpiritLamp extends ApparatusApplicationBottomAccessory
{

    public static ApparatusApplicationSpiritLamp readFromNBT(NBTTagCompound tag, TileEntityAlchemicalApparatus entity)
    {
        ApparatusApplicationSpiritLamp lamp = new ApparatusApplicationSpiritLamp(entity);
        lamp.fuel = tag.getShort("spiritlamp_fuel");
        lamp.spirit = tag.getShort("spiritlamp_spirit");
        lamp.active = tag.getBoolean("spiritlamp_active");
        return lamp;
    }

    public boolean active = false;	//is burning or not
    public short fuel = 0;   //remaining fuel in use: 1 for 1 tick
    public short spirit = 0;  //remaining lamp fuel: 1 == 100 ticks
    public final short spiritMax = 60; //spiritlamp capacity

    public ApparatusApplicationSpiritLamp(TileEntityAlchemicalApparatus parent)
    {
        this.parent = parent;
    }

    public void fill(ItemStack stack)
    {	//fills the spirit lamp with spirit from the bottle
        int draw = Math.min(this.spiritMax - this.spirit, stack.getItemDamage());
        if (draw == stack.getItemDamage())
        {
            stack.itemID = Item.glassBottle.itemID;
            stack.setItemDamage(0);
            stack.stackSize = 1;
            this.spirit += draw;
        } else
        {
            if (draw == 0)
            {
                return;
            }
            stack.setItemDamage(stack.getItemDamage() - draw);
            this.spirit += draw;
        }
    }

    @Override
    public int getItemID()
    {
        return ItemRegistry.appItemSpiritLamp.itemID;
    }

    @Override
    public int getModelCode()
    {
        return 0;
    }

    @Override
    public String getStat()
    {
        return "Fuel: " + this.spirit + "/60" + (this.active ? " (Burning)" : "");
    }

    @Override
    public int getType()
    {
        return 1;
    }

    public Boolean isActive()
    {
        return this.active;
    }

    @Override
    public boolean isSalvagable()
    {
        return true;
    }

    public void setActive(boolean flag)
    {
        if (flag)
        {
            this.active = true;
        } else
        {
            this.active = false;
            this.fuel = 0;
        }
    }

    @Override
    public void update()
    {
        if (this.active)
        {
            if (this.fuel > 0)
            {
                this.fuel--;
            } else if (this.spirit > 0)
            {
                this.spirit--;
                this.fuel = 99;
            } else
            {
                this.active = false;
            }
        }
        
        if (this.active)
        {
            if (this.parent.stand != null
                    && this.parent.stand instanceof ApparatusApplicationBottleStand
                    && ((ApparatusApplicationBottleStand) this.parent.stand).stack != null)
            {
                ((ApparatusApplicationBottleStand) this.parent.stand).temperature += 0.3f;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        tag.setShort("apparatus_type_bottom", (short) this.getType());
        tag.setShort("spiritlamp_fuel", this.fuel);
        tag.setShort("spiritlamp_spirit", this.spirit);
        tag.setBoolean("spiritlamp_active", this.active);
    }


}
