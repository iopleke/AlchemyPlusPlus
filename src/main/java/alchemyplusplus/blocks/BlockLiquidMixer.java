package alchemyplusplus.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.client.ClientProxy;
import alchemyplusplus.items.ItemRegistry;
import alchemyplusplus.tileentities.TileEntityLiquidMixer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;


public class BlockLiquidMixer extends BlockContainer {	
	
		public BlockLiquidMixer(int blockID) {
			super(blockID, Material.piston);
			this.setHardness(1.0F);
			this.setResistance(3.0F);
			this.setStepSound(soundStoneFootstep);
			this.setUnlocalizedName("appBlockLiquidMixer");
}

		
		@Override
		public void registerIcons(IconRegister iconRegister) {
			this.blockIcon = iconRegister.registerIcon("AlchemyPlusPlus:WIPLiquidMixer");
		}


		@Override
		public TileEntity createNewTileEntity(World world) {
			return new TileEntityLiquidMixer();
		}
		
		@Override
		public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float g) {
			if (!world.isRemote)
			player.openGui(AlchemyPlusPlus.instance, 0, world, x, y, z);
			return true;
		}
		
		public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
		{
		   return false;
		}
		
		public boolean isOpaqueCube()
		{
		   return false;
		}
		
		@SideOnly(Side.CLIENT)
	    public boolean addBlockHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	    {
	        return true;
	    }

	    @SideOnly(Side.CLIENT)
	    public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	    {
	        return true;
	    }

		@Override
		 public int idDropped(int par1, Random par2Random, int par3)
	    {
	        return ItemRegistry.appItemLiquidMixer.itemID;
	    }
		
	    @SideOnly(Side.CLIENT)
	    public int idPicked(World par1World, int par2, int par3, int par4)
	    {
	        return ItemRegistry.appItemLiquidMixer.itemID;
	    }
		
		
}
