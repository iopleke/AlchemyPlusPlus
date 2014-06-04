package alchemyplusplus.blocks;

import java.util.Random;

import alchemyplusplus.items.ItemRegistry;
import alchemyplusplus.tileentities.TileEntityDiffuser;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
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
        this.blockIcon = iconRegister.registerIcon("AlchemyPlusPlus:WIPLiquidMixer");
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityDiffuser();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int a, float b, float c, float g) {

        TileEntityDiffuser diffuserTE = (TileEntityDiffuser) world.getBlockTileEntity(x, y, z);

        if (player.getHeldItem() != null && player.getHeldItem().itemID == Item.potion.itemID && player.getHeldItem().getItemDamage() != 0 && !ItemPotion.isSplash(player.getHeldItem().getItemDamage())) {
            if (diffuserTE.potionStack == null) {
                if (!world.isRemote) {
                    player.addChatMessage("You pour the potion into the diffuser");
                    // player.openGui(AlchemyPlusPlus.instance, 3, world, x, y, z);
                }
                diffuserTE.potionStack = player.getHeldItem().copy();
                diffuserTE.setBottleColorValue(diffuserTE.potionStack.getItemDamage());
                if (!player.capabilities.isCreativeMode && !player.isSneaking()) {
                    player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Item.glassBottle);
                }
                diffuserTE.fluidLevel = 10;
                if (!diffuserTE.isDiffuserActive()) {
                    diffuserTE.toggleDiffusingState();
                }
            } else {

                if (!world.isRemote) {
                    player.addChatMessage("The diffuser is too full to hold any more liquid");
                }
            }
        } else if (player.getHeldItem() != null && player.getHeldItem().itemID == Item.glassBottle.itemID) {

            if (diffuserTE.potionStack != null) {
                if (diffuserTE.getFluidLevel() > 9) {

                    // Allow creative mode players to override this by sneaking
                    if (!player.capabilities.isCreativeMode) {
                        player.inventory.mainInventory[player.inventory.currentItem] = diffuserTE.potionStack;
                    }
                    // Wiping the diffuser data
                    diffuserTE.potionStack = null;
                    diffuserTE.setBottleColorValue(0);
                    if (diffuserTE.isDiffuserActive()) {
                        diffuserTE.toggleDiffusingState();
                    }
                } else {
                    if (!world.isRemote) {
                        player.addChatMessage("There doesn't seem to be enough to refill your bottle");
                    }
                }
            }
        } else if (player.getHeldItem() != null && player.getHeldItem().itemID == Item.potion.itemID && player.getHeldItem().getItemDamage() == 0) {
            if (!player.capabilities.isCreativeMode) {
                player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Item.glassBottle);
            }
            diffuserTE.potionStack = null;
            diffuserTE.fluidLevel = 0;
            diffuserTE.setBottleColorValue(0);
            if (diffuserTE.isDiffuserActive()) {
                diffuserTE.toggleDiffusingState();
            }
            if (!world.isRemote) {
                player.addChatMessage("The diffuser is washed clean, ready for re-use");
            }

        } else if (diffuserTE.canDiffuse() || diffuserTE.isDiffuserActive()) {
            String action;
            if (diffuserTE.isDiffuserActive()) {
                action = "cork";
            } else {
                action = "un-cork";
            }
            diffuserTE.toggleDiffusingState();
            if (!world.isRemote) {
                player.addChatMessage("You " + action + " the diffuser");
            }
        } else {
            if (!world.isRemote) {
                player.addChatMessage("Without a potion filling it, un-corking the diffuser seems pointless");
            }

        }

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
