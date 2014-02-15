package mokonaDesu.alchemypp.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import mokonaDesu.alchemypp.items.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AlchemyPPCreativeTab extends CreativeTabs {
    public AlchemyPPCreativeTab(int par1, String par2Str) {
        super(par1, par2Str);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        // Set the creative tab icon
        return new ItemStack(
                mokonaDesu.alchemypp.items.ItemRegistry.appItemPotionBottle, 1,
                0);
    }

    public String getTranslatedTabLabel() {
        // Name the creative tab
        return "Alchemy++";
    }
}
