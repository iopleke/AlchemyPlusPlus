package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.block.BlockComplex;
import alchemyplusplus.reference.Naming;
import alchemyplusplus.utility.NotificationManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DiffuserBlock extends BlockComplex
{
	public DiffuserBlock()
	{
		super(Material.wood, Naming.Blocks.DIFFUSER, Block.soundTypeWood);
		this.setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.8F, 0.8F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		return new DiffuserTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		DiffuserTileEntity diffuser = (DiffuserTileEntity) world.getTileEntity(x, y, z);

		if (player.getHeldItem() != null)
		{
			if (player.getHeldItem().getItem() == Items.potionitem)
			{
				if (player.getHeldItem().getItemDamage() != 0 && !ItemPotion.isSplash(player.getHeldItem().getItemDamage()))
				{
					ItemStack potionItemStack = player.getHeldItem();
					if (potionItemStack != null)
					{
						if (diffuser.fluidTank.getFluidAmount() == diffuser.fluidTank.getCapacity())
						{
							if (!world.isRemote)
							{
								NotificationManager.sendChatMessage(player, "diffuser.full");
							}
						} else if (diffuser.fluidTank.getFluidAmount() == 0 || diffuser.fluidTank.getFluid().fluidID == ItemPotion.getIdFromItem(player.getHeldItem().getItem()))
						{// @TODO - move potion match check to the TileEntity fill method
							if (diffuser.getFluidAmount() != 0)
							{
								if (!world.isRemote)
								{
									NotificationManager.sendChatMessage(player, "diffuser.combine.success");
								}
							} else if ((diffuser.getFluidAmount() == 0))
							{
								if (!world.isRemote)
								{
									NotificationManager.sendChatMessage(player, "diffuser.pour");
								}
							}
							diffuser.fillWithOverRide(player.getHeldItem());
							if (!player.capabilities.isCreativeMode && !player.isSneaking())
							{
								player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.glass_bottle);
							}
						} else
						{
							if (!world.isRemote)
							{
								NotificationManager.sendChatMessage(player, "diffuser.combine.failure");
							}

						}

					}
				} else if (player.getHeldItem().getItemDamage() == 0)// Item damage of zero is a water bottle
				{
					if (!player.capabilities.isCreativeMode)
					{
						player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.glass_bottle);
					}

					if (diffuser.fluidTank.getFluid() != null)
					{
						diffuser.resetDiffuser();

						if (!world.isRemote)
						{
							NotificationManager.sendChatMessage(player, "diffuser.wash.success");
						}

					} else
					{
						NotificationManager.sendChatMessage(player, "diffuser.wash.failure");
					}

				}
			} else if (player.getHeldItem().getItem() == Items.glass_bottle)
			{
				if (diffuser.fluidTank.getFluidAmount() > 0)
				{

					// Set item to potion
					// @TODO - make this work
					player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.potionitem);

					// Wiping the diffuser data
					diffuser.setBottleColorValue(0);
					if (diffuser.isDiffuserActive())
					{
						diffuser.toggleDiffusingState();
					}

				} else
				{
					if (!world.isRemote)
					{
						NotificationManager.sendChatMessage(player, "diffuser.bottle.refill.failure");
					}
				}
			}
		} else if (diffuser.canDiffuse() || diffuser.isDiffuserActive())
		{
			if (diffuser.isDiffuserActive())
			{
				if (!world.isRemote)
				{
					NotificationManager.sendChatMessage(player, "diffuser.cork");
				}
			} else
			{
				if (!world.isRemote)
				{
					NotificationManager.sendChatMessage(player, "diffuser.uncork");
				}
			}
			diffuser.toggleDiffusingState();

		} else
		{
			if (!world.isRemote)
			{
				NotificationManager.sendChatMessage(player, "diffuser.uncork.failure");
			}

		}
		AlchemyPlusPlus.LOGGER.info("Fluid amount: " + diffuser.getFluidAmount());
		return false;
	}
}
