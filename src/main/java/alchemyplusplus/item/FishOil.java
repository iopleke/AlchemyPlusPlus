package alchemyplusplus.item;

import alchemyplusplus.potion.custom.PotionCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class FishOil extends ItemBasic
{

    public static void fill(ItemStack stack, int percent)
    {
        if (stack.getItemDamage() < 100)
        {
            stack.setItemDamage(stack.getItemDamage() + percent);
        }
        if (stack.getItemDamage() > 100)
        {
            stack.setItemDamage(100);
        }
    }

    private IIcon percent0;
    private IIcon percent100;
    private IIcon percent20;
    private IIcon percent40;
    private IIcon percent60;
    private IIcon percent80;

    public FishOil(String itemname)
    {
        super(itemname);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.getItemDamage() == 0)
        {
            list.add("<Bottle empty>");
            return;
        }
        if (stack.getItemDamage() < 100)
        {
            list.add("Bottle is " + stack.getItemDamage() + "% full");
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIIconFromDamage(int damage)
    {
        return damage < 20 ? this.percent0 : damage < 40 ? this.percent20 : damage < 60 ? this.percent40 : damage < 80 ? this.percent60 : damage < 100 ? this.percent80 : this.percent100;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {

        if (!player.isSneaking() || stack.getItemDamage() == 100)
        {
            return false;
        }
        if (player.inventory.hasItem(Items.fish))
        {
            player.inventory.consumeInventoryItem(Items.fish);
            fill(stack, Item.itemRand.nextInt(5) + 1);
            return true;
        } else
        {
            player.addPotionEffect(PotionCustom.infectedPotion.getEffect());
            return false;
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon(iconName);
        this.percent0 = iconRegister.registerIcon(iconName + "0");
        this.percent20 = iconRegister.registerIcon(iconName + "20");
        this.percent40 = iconRegister.registerIcon(iconName + "40");
        this.percent60 = iconRegister.registerIcon(iconName + "60");
        this.percent80 = iconRegister.registerIcon(iconName + "80");
        this.percent100 = iconRegister.registerIcon(iconName + "100");
    }

}
