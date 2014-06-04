package alchemyplusplus.blocks;

import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.tileentities.ApparatusApplicationBottleStand;
import alchemyplusplus.tileentities.ApparatusApplicationSpiritLamp;
import alchemyplusplus.tileentities.TileEntityAlchemicalApparatus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StringUtils;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAlchemicalApparatus extends BlockContainer {

	protected BlockAlchemicalApparatus(int id, Material material) {
		super(id, material);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityAlchemicalApparatus();
	}

	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon("AlchemyPlusPlus:WIPLiquidMixer");
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

	public boolean isOpaqueCube()
	{
	   return false;
	}

	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
	   return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float g) {
		if (!world.isRemote) {
		if (player.getHeldItem() != null && player.getHeldItem().itemID == Item.flintAndSteel.itemID) {
			if (((TileEntityAlchemicalApparatus)world.getBlockTileEntity(x, y, z)).hasBottom() && ((TileEntityAlchemicalApparatus)world.getBlockTileEntity(x, y, z)).bottom instanceof ApparatusApplicationSpiritLamp) {
			Random random = new Random();
			world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, random.nextFloat() * 0.4F + 0.8F);
		    player.getHeldItem().damageItem(1, player);
		    ((ApparatusApplicationSpiritLamp)((TileEntityAlchemicalApparatus)world.getBlockTileEntity(x, y, z)).bottom).setActive(true);
		    player.swingItem();
		    PacketDispatcher.sendPacketToAllPlayers(world.getBlockTileEntity(x, y, z).getDescriptionPacket());
		    return true;
			} else {
				player.addChatMessage("This apparatus doesn't have a spiritlamp.");
			}
		} else if (player.getHeldItem() != null && player.getHeldItem().itemID == Item.potion.itemID && !ItemPotion.isSplash(player.getHeldItem().getItemDamage())) {
			TileEntityAlchemicalApparatus te = (TileEntityAlchemicalApparatus)world.getBlockTileEntity(x, y, z);
			if (te.hasStand() && te.stand instanceof ApparatusApplicationBottleStand) {
				((ApparatusApplicationBottleStand)te.stand).eject(player);
				((ApparatusApplicationBottleStand)te.stand).placeBottle(player.getHeldItem().copy());
				player.getHeldItem().stackSize = 0;
				 PacketDispatcher.sendPacketToAllPlayers(world.getBlockTileEntity(x, y, z).getDescriptionPacket());
			} else {
				player.addChatMessage("This apparatus doesn't have a stand.");
			}
		} else
			//if (Stat-o-meter held)
		player.addChatMessage(((TileEntityAlchemicalApparatus)world.getBlockTileEntity(x, y, z)).getStat());
			}
		return true;
	}


	@SideOnly(Side.CLIENT)
	 public void randomDisplayTick(World world, int x, int y, int z, Random random)	    {
TileEntityAlchemicalApparatus te = (TileEntityAlchemicalApparatus) world.getBlockTileEntity(x, y, z);
		if (te.hasBottom() && te.bottom instanceof ApparatusApplicationSpiritLamp && ((ApparatusApplicationSpiritLamp)te.bottom).active) {
			world.spawnParticle("flame", (double)(x + 0.5), (double)y + 0.375, (double)(z + 0.5), 0.0D, 0.0D, 0.0D);
		}

	    }


	 public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		 if (!world.isRemote) {
			 TileEntityAlchemicalApparatus te = (TileEntityAlchemicalApparatus) world.getBlockTileEntity(x, y, z);
			 if (player.isSneaking() && te.hasStand() && te.stand instanceof ApparatusApplicationBottleStand) {
				 ((ApparatusApplicationBottleStand)((TileEntityAlchemicalApparatus)world.getBlockTileEntity(x, y, z)).stand).eject(player);
				PacketDispatcher.sendPacketToAllPlayers(world.getBlockTileEntity(x, y, z).getDescriptionPacket());
			 }

			if (te.hasBottom() && te.bottom instanceof ApparatusApplicationSpiritLamp) {
			    ((ApparatusApplicationSpiritLamp)te.bottom).setActive(false);
			    PacketDispatcher.sendPacketToAllPlayers(world.getBlockTileEntity(x, y, z).getDescriptionPacket());
			}
		 }
	 }


    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }


    @Override
    public void breakBlock(World world, int x, int y, int z, int damage, int a) {

    	TileEntityAlchemicalApparatus te = (TileEntityAlchemicalApparatus) world.getBlockTileEntity(x, y, z);
    	Random random = new Random();

    	if (te == null) return;

    	if (te.hasUpper() && te.upper.isSalvagable())
    		ejectStack(new ItemStack(te.upper.getItemID(), 1, 0), x, y, z, world);

    	if (te.hasStand() && te.stand.isSalvagable()) {
    		te.stand.eject(null);
    		ejectStack(new ItemStack(te.stand.getItemID(), 1, 0), x, y, z, world);
    	}

    	if (te.hasBottom() && te.bottom.isSalvagable())
    		ejectStack(new ItemStack(te.bottom.getItemID(), 1, 0), x, y, z, world);

    	super.breakBlock(world, x, y, z, damage, a);

    }

    private void ejectStack(ItemStack stack, int x, int y, int z, World world) {
    	Random random = new Random();

    	float f = random.nextFloat() * 0.8F + 0.1F;
        float f1 = random.nextFloat() * 0.8F + 0.1F;
        float f2 = random.nextFloat() * 0.8F + 0.1F;

        EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), stack);

        float f3 = 0.05F;
        entityitem.motionX = (double)((float)random.nextGaussian() * f3);
        entityitem.motionY = (double)((float)random.nextGaussian() * f3 + 0.2F);
        entityitem.motionZ = (double)((float)random.nextGaussian() * f3);
        world.spawnEntityInWorld(entityitem);
    }


}
