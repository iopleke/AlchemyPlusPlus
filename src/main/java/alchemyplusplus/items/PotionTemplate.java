package alchemyplusplus.items;

import java.lang.reflect.Field;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

//Extended ItemPotion to add ability to charge-throw splash potions.
public class PotionTemplate extends ItemPotion
{

    ItemPotion potionReference;

    public PotionTemplate(int id, ItemPotion potion)
    {
        super(id);
        this.potionReference = potion;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        if (ItemPotion.isSplash(stack.getItemDamage()))
        {
            return EnumAction.bow;
        } else
        {
            return EnumAction.drink;
        }
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return ItemPotion.isSplash(stack.getItemDamage()) ? 72000 : 32;
    }

    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {

        super.onEaten(stack, world, player);

        return stack;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (isSplash(stack.getItemDamage()))
        {
            ArrowNockEvent event = new ArrowNockEvent(player, stack);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled())
            {
                return event.result;
            }
        }

        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int dmg)
    {
        if (!isSplash(stack.getItemDamage()))
        {
            return;
        }

        int j = this.getMaxItemUseDuration(stack) - dmg;

        ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        float f = (float) j / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if ((double) f < 0.1D)
        {
            return;
        }

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

        if (!world.isRemote)
        {

            world.spawnEntityInWorld(new EntityPotion(world, player, stack));
        }

    }

    @SideOnly(Side.CLIENT)
    public void registerIIcons(IIconRegister par1IIconRegister)
    {
        Class potionClass = potionReference.getClass();

        //setting up the icons for proper graphics rendering
        try
        {

            //I WANTS THIS STUFFS!!!!
            Field icon1 = potionClass.getDeclaredField("field_94591_c");
            icon1.setAccessible(true);

            Field icon2 = potionClass.getDeclaredField("field_94590_d");
            icon2.setAccessible(true);

            Field icon3 = potionClass.getDeclaredField("field_94592_ct");
            icon3.setAccessible(true);

            Field superIIcon1 = this.getClass().getSuperclass().getDeclaredField("field_94591_c");
            superIIcon1.setAccessible(true);
            superIIcon1.set(this, icon1.get(potionReference));

            Field superIIcon2 = this.getClass().getSuperclass().getDeclaredField("field_94590_d");
            superIIcon2.setAccessible(true);
            superIIcon2.set(this, icon2.get(potionReference));

            Field superIIcon3 = this.getClass().getSuperclass().getDeclaredField("field_94592_ct");
            superIIcon3.setAccessible(true);
            superIIcon3.set(this, icon3.get(potionReference));

        } catch (NoSuchFieldException e)
        {
            System.err.println("Alchemy++ failed to reflect the potion class!");
            e.printStackTrace();
        } catch (SecurityException e)
        {
            System.err.println("Alchemy++ failed to reflect the potion class!");
            e.printStackTrace();
        } catch (IllegalArgumentException e)
        {
            System.err.println("Alchemy++ failed to reflect the potion class!");
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            System.err.println("Alchemy++ failed to reflect the potion class!");
            e.printStackTrace();
        }

    }

}
