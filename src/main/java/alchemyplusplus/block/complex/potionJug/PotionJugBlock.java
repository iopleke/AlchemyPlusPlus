package alchemyplusplus.block.complex.potionJug;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.BlockRegistry;
import alchemyplusplus.gui.CreativeTab;
import alchemyplusplus.reference.Textures;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PotionJugBlock extends BlockContainer
{
	public PotionJugBlock(String blockname)
	{
		super(Material.glass);
		this.setStepSound(Block.soundTypeGlass);
		this.setBlockName(blockname);
		this.setCreativeTab(CreativeTab.APP_TAB);
		this.setBlockTextureName(Textures.Icon.POTION_JUG);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new PotionJugTileEntity();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		ItemStack stack = new ItemStack(BlockRegistry.potionJug, 1, 0);
		Random random = new Random();

		if (world.getTileEntity(x, y, z) != null)
		{
			int potionID = (((PotionJugTileEntity) world.getTileEntity(x, y, z)).potionID);
			int has = (((PotionJugTileEntity) world.getTileEntity(x, y, z)).containerHas);
			if (potionID != 0 && has != 0)
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.getTagCompound().setShort("effectID", (short) potionID);
				stack.getTagCompound().setShort("containerHas", (short) has);
			}
		}

		float f = random.nextFloat() * 0.8F + 0.1F;
		float f1 = random.nextFloat() * 0.8F + 0.1F;
		float f2 = random.nextFloat() * 0.8F + 0.1F;

		EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2),
				stack);

		entityitem.motionX = (double) ((float) random.nextGaussian() * 0.05F);
		entityitem.motionY = (double) ((float) random.nextGaussian() * 0.05F + 0.2F);
		entityitem.motionZ = (double) ((float) random.nextGaussian() * 0.05F);

		AlchemyPlusPlus.LOGGER.info("Spawning potion jug item entity");

		world.spawnEntityInWorld(entityitem);

		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int par5)
	{
		PotionJugTileEntity te = (PotionJugTileEntity) world.getTileEntity(x, y, z);
		return (int) Math.floor(te.containerHas * 16f / te.containerMax);
	}

	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		world.notifyBlockChange(x, y, z, this);

		if (player.isSneaking() || player.getCurrentEquippedItem() == null)
		{

			if (((PotionJugTileEntity) world.getTileEntity(x, y, z)).containerHas > 0)
			{
				((PotionJugTileEntity) world.getTileEntity(x, y, z)).containerHas--;

				List effectList = PotionHelper.getPotionEffects(((PotionJugTileEntity) world.getTileEntity(x, y, z)).potionID, true);
				for (int i = 0; i < effectList.size(); i++)
				{
					player.addPotionEffect((PotionEffect) effectList.get(i));
				}
				if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
				{
					//PacketDispatcher.sendPacketToAllPlayers(world.getTileEntity(x, y, z).getDescriptionPacket());
				}

			}
		}

		ItemStack stack = player.getCurrentEquippedItem();
		PotionJugTileEntity te = (PotionJugTileEntity) world.getTileEntity(x, y, z);
		if (stack == null)
		{
			return true;
		}
		if (stack.getItem() == Items.glass_bottle && stack.getItemDamage() == 0)
		{
			if (te.containerHas > 0)
			{
				if (!player.capabilities.isCreativeMode)
				{
					stack.stackSize--;
				}
				te.containerHas--;
				ItemStack potion = new ItemStack(Items.potionitem, 1, te.potionID);
				if (!player.capabilities.isCreativeMode)
				{
					player.inventory.addItemStackToInventory(potion);
				}
			}
		} else if (stack.getItem() == Items.potionitem && stack.getItemDamage() > 0 && !stack.hasTagCompound())
		{		//Custom potions are not allowed!
			if (te.containerHas == 0 || (stack.getItemDamage() == te.potionID && (te.containerHas < te.containerMax)))
			{

				te.potionID = stack.getItemDamage();

				if (!player.capabilities.isCreativeMode)
				{
					stack.stackSize--;

					ItemStack potion = new ItemStack(Items.glass_bottle, 1, 0);
					player.inventory.addItemStackToInventory(potion);
				}
				te.containerHas++;
			}
		}
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			//PacketDispatcher.sendPacketToAllPlayers(world.getTileEntity(x, y, z).getDescriptionPacket());
		}
		return true;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
