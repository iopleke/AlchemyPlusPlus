package alchemyplusplus.items;

import java.util.List;

import alchemyplusplus.AlchemyPlusPlus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemAlchemicalGuide extends APPItem {

	public ItemAlchemicalGuide(int id, String iconName) {
		super(id, iconName);
	}
	
	 public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
	 	 player.openGui(AlchemyPlusPlus.instance, 2, world, 0, 0, 0);
	 	 return stack; 
	}
	 
	 public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4)
	 {
		 if (stack.hasTagCompound() && stack.getTagCompound().getShort("bookmark") != 0) {
			 list.add("Bookmarked at page "+stack.getTagCompound().getShort("bookmark"));
		 }
	 }
}
