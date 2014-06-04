package alchemyplusplus.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import alchemyplusplus.items.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class APPCreativeTab extends CreativeTabs {
    public APPCreativeTab(int par1, String par2Str) {
        super(par1, par2Str);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        // Set the creative tab icon
        return new ItemStack(
                alchemyplusplus.items.ItemRegistry.appItemPotionBottle, 1,
                0);
    }

    public String getTranslatedTabLabel() {
        // Name the creative tab
        return "Alchemy++";
    }
}
