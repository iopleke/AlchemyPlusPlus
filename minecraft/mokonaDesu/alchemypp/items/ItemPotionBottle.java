package mokonaDesu.alchemypp.items;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import mokonaDesu.alchemypp.AlchemyPP;
import mokonaDesu.alchemypp.blocks.BlockRegistry;
import mokonaDesu.alchemypp.tileentities.TileEntityPotionContainer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemPotionBottle extends Item {

	public static Icon bottle;
	public static Icon contents;
	
	public ItemPotionBottle(int id) {
		super(id);
		this.maxStackSize = 1;
	}
	
	 public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	    {
		 if (world.isBlockSolidOnSide(x, y, z, ForgeDirection.UP) && world.isAirBlock(x, y + 1, z)) {
			 world.setBlock(x, y + 1, z, BlockRegistry.appBlockPotionContainer.blockID);
			if (stack.hasTagCompound() && stack.getTagCompound().getShort("containerHas") > 0) {
			 TileEntityPotionContainer te = (TileEntityPotionContainer) world.getBlockTileEntity(x, y + 1, z);
			 te.potionID = stack.getTagCompound().getShort("effectID");
			 te.containerHas = stack.getTagCompound().getShort("containerHas"); 
			}
			stack.stackSize--;
			 return true;
		 }
		 else return false;

	    }
	 
	 @SideOnly(Side.CLIENT)
	 public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
	if (!stack.hasTagCompound() || stack.getTagCompound().getShort("containerHas") == 0) list.add("<Bottle empty>");
	else {
		ArrayList l = (((ArrayList)PotionHelper.getPotionEffects(stack.getTagCompound().getShort("effectID"), false)));
		
		if (l != null && !l.isEmpty()) {
		PotionEffect effect = (PotionEffect)l.get(0);
		String s = StatCollector.translateToLocal(effect.getEffectName()).trim();
		  if (effect.getAmplifier() > 0)
          {
              s = s + " " + StatCollector.translateToLocal("potion.potency." + effect.getAmplifier()).trim();
          }

          if (effect.getDuration() > 20)
          {
              s = s + " (" + Potion.getDurationString(effect) + ")";
          }
          
          Potion potion = Potion.potionTypes[effect.getPotionID()];

          if (potion.isBadEffect())
          {
              list.add(EnumChatFormatting.RED + s);
          }
          else
          {
        	  list.add(EnumChatFormatting.GRAY + s);
          }
		} else {
            String s1 = StatCollector.translateToLocal("potion.empty").trim();
            list.add(EnumChatFormatting.GRAY + s1);
        }
		list.add("");
		list.add(EnumChatFormatting.BLUE + "" + EnumChatFormatting.ITALIC + Short.toString(stack.getTagCompound().getShort("containerHas")) + " doses");
	}		 
	 }
	 
		public void registerIcons(IconRegister iconRegister) {
			if (AlchemyPP.alternativeTextures) this.bottle = iconRegister.registerIcon("AlchemyPP:PotionBottleAlt");
			else this.bottle = iconRegister.registerIcon("AlchemyPP:PotionBottle");
			
			this.contents = iconRegister.registerIcon("AlchemyPP:BottleContents");
		}

	    public Icon getIconFromDamage(int damage)
	    {
	        return this.bottle;
	    }
	    
	    
}
