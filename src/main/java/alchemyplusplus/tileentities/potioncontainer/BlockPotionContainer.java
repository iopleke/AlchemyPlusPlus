package alchemyplusplus.tileentities.potioncontainer;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import alchemyplusplus.items.ItemRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPotionContainer extends BlockContainer
{

    public BlockPotionContainer(int blockID)
    {
        super(blockID, Material.iron);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("appBlockPotionContainer");
    }

    @SideOnly(Side.CLIENT)
    public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean addBlockHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
    {
        return true;
    }

    public void breakBlock(World world, int x, int y, int z, int meta, int mmm)
    {
        ItemStack stack = new ItemStack(ItemRegistry.appItemPotionBottle.itemID, 1, 0);
        Random random = new Random();

        if (world.getBlockTileEntity(x, y, z) != null)
        {
            int potionID = (((TileEntityPotionContainer) world.getBlockTileEntity(x, y, z)).potionID);
            int has = (((TileEntityPotionContainer) world.getBlockTileEntity(x, y, z)).containerHas);
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

        world.spawnEntityInWorld(entityitem);

        super.breakBlock(world, x, y, z, meta, mmm);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityPotionContainer();
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int par5)
    {
        TileEntityPotionContainer te = (TileEntityPotionContainer) world.getBlockTileEntity(x, y, z);
        return (int) Math.floor(te.containerHas * 16f / te.containerMax);
    }

    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return ItemRegistry.appItemPotionBottle.itemID;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float g)
    {
        world.notifyBlockChange(x, y, z, 0);

        if (player.isSneaking())
        {

            if (((TileEntityPotionContainer) world.getBlockTileEntity(x, y, z)).containerHas > 0)
            {
                ((TileEntityPotionContainer) world.getBlockTileEntity(x, y, z)).containerHas--;

                List effectList = PotionHelper.getPotionEffects(((TileEntityPotionContainer) world.getBlockTileEntity(x, y, z)).potionID, true);
                for (int i = 0; i < effectList.size(); i++)
                {
                    player.addPotionEffect((PotionEffect) effectList.get(i));
                }
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
                {
                    PacketDispatcher.sendPacketToAllPlayers(world.getBlockTileEntity(x, y, z).getDescriptionPacket());
                }

            }
        }

        ItemStack stack = player.getCurrentEquippedItem();
        TileEntityPotionContainer te = (TileEntityPotionContainer) world.getBlockTileEntity(x, y, z);
        if (stack == null)
        {
            return true;
        }
        if (stack.itemID == Item.glassBottle.itemID && stack.getItemDamage() == 0)
        {
            if (te.containerHas > 0)
            {
                stack.stackSize--;
                te.containerHas--;
                ItemStack potion = new ItemStack(Item.potion.itemID, 1, te.potionID);
                player.inventory.addItemStackToInventory(potion);
            }
        } else if (stack.itemID == Item.potion.itemID && stack.getItemDamage() > 0 && !stack.hasTagCompound())
        {		//Custom potions are not allowed!
            if (te.containerHas == 0 || (stack.getItemDamage() == te.potionID && (te.containerHas < te.containerMax)))
            {
                te.containerHas++;
                te.potionID = stack.getItemDamage();
                stack.stackSize--;
                ItemStack potion = new ItemStack(Item.glassBottle, 1, 0);
                player.inventory.addItemStackToInventory(potion);
            }
        }
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
        {
            PacketDispatcher.sendPacketToAllPlayers(world.getBlockTileEntity(x, y, z).getDescriptionPacket());
        }
        return true;
    }

    @Override
    public void registerIIcons(IIIconRegister iconRegister)
    {
        this.blockIIcon = iconRegister.registerIIcon("AlchemyPlusPlus:PotionBottle");
    }

    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }

}
