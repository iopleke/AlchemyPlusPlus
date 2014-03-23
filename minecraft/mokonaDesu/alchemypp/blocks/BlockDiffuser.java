package mokonaDesu.alchemypp.blocks;

import java.util.Random;

import mokonaDesu.alchemypp.items.ItemRegistry;
import mokonaDesu.alchemypp.tileentities.TileEntityDiffuser;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiffuser extends BlockContainer {

    public boolean isDiffusing = false;

    public BlockDiffuser(int blockID) {
        super(blockID, Material.piston);
        this.setHardness(1.0F);
        this.setResistance(3.0F);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("appBlockDiffuser");
        this.setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.8F, 0.8F);
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        // @TODO - figure out how to hide the dummy block in NEI
        // @TODO - change this from "WIPLiquidMixer" to something more generic
        this.blockIcon = iconRegister.registerIcon("AlchemyPP:WIPLiquidMixer");
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityDiffuser();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float g) {

        TileEntityDiffuser diffuserTE = (TileEntityDiffuser) world.getBlockTileEntity(x, y, z);

        if (player.getHeldItem() != null && player.getHeldItem().itemID == Item.potion.itemID) {
            if (!world.isRemote) {
                player.addChatMessage("You pour the potion into the diffuser.");

                // player.openGui(AlchemyPP.instance, 3, world, x, y, z);
            }
            diffuserTE.placeBottle(player.getHeldItem().copy());
            player.getHeldItem().stackSize = 0;
            if (!diffuserTE.isDiffuserActive()) {
                diffuserTE.toggleDiffusingState();
            }
        } else if (diffuserTE.canDiffuse() || diffuserTE.isDiffuserActive()) {
            diffuserTE.toggleDiffusingState();
        } else {
            if (!world.isRemote) {
                player.addChatMessage("You uncork the diffuser, but nothing happens.");
                player.addChatMessage("Perhaps if you filled it with a potion?");
            }

        }
        System.err.println("Diffuser is diffusing: " + diffuserTE.isDiffuserActive());

        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addBlockHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return ItemRegistry.appItemDiffuser.itemID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4) {
        return ItemRegistry.appItemDiffuser.itemID;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

}
