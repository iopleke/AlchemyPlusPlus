package mokonaDesu.alchemypp.blocks;

import java.util.Random;

import mokonaDesu.alchemypp.AlchemyPP;
import mokonaDesu.alchemypp.items.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockOre extends Block {

    public BlockOre(int blockID) {
        super(blockID, Material.rock);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundStoneFootstep);
        this.setCreativeTab(AlchemyPP.alchemyPPTab);
        this.setUnlocalizedName("appBlockOre");

    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcon = iconRegister
                .registerIcon("AlchemyPP:blockOrichalcumOre");

    }

    @Override
    public int idDropped(int par1, Random random, int par3) {
        return ItemRegistry.appItemOrichalcumOre.itemID;
    }

    @Override
    public int quantityDroppedWithBonus(int par1, Random random) {
        if (par1 > 0) {
            int j = random.nextInt(par1 + 2) - 1;

            if (j < 0) {
                j = 0;
            }

            return this.quantityDropped(random) * (j + 1);
        } else {
            return this.quantityDropped(random);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3,
            int par4, int par5, float par6, int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5,
                par6, par7);
        int j1 = MathHelper.getRandomIntegerInRange(par1World.rand, 3, 7);
        this.dropXpOnBlockBreak(par1World, par2, par3, par4, j1);
    }

}
