package alchemyplusplus.tileentities.mixer;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.items.ItemRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLiquidMixer extends BlockContainer
{

    public BlockLiquidMixer(int blockID)
    {
        super(blockID, Material.piston);
        this.setHardness(1.0F);
        this.setResistance(3.0F);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("appBlockLiquidMixer");
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

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityLiquidMixer();
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

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float g)
    {
        if (!world.isRemote)
        {
            player.openGui(AlchemyPlusPlus.instance, 0, world, x, y, z);
        }
        return true;
    }

    @Override
    public void registerIIcons(IIIconRegister iconRegister)
    {
        this.blockIIcon = iconRegister.registerIIcon("AlchemyPlusPlus:WIPLiquidMixer");
    }

    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }

}
