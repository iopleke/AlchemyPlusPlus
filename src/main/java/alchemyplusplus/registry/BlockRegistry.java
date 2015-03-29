package alchemyplusplus.registry;

import alchemyplusplus.block.basic.FleshBlock;
import alchemyplusplus.block.complex.diffuser.DiffuserBlock;
import alchemyplusplus.block.complex.diffuser.DiffuserTileEntity;
import alchemyplusplus.block.complex.distillery.DistilleryBlock;
import alchemyplusplus.block.complex.distillery.DistilleryTileEntity;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerBlock;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntity;
import alchemyplusplus.block.complex.potionJug.PotionJugBlock;
import alchemyplusplus.block.complex.potionJug.PotionJugTileEntity;
import alchemyplusplus.reference.Naming;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlockRegistry
{

    public static Block diffuser;
    public static Block distillery;
    public static Block fleshBlock;
    public static Block fluidMixer;
    public static Block potionJug;

    public static void registerBlockRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(fleshBlock, 1, 0), "xxx", "xsx", "xxx", 'x', new ItemStack(Items.rotten_flesh, 1, 0), 's', new ItemStack(Items.slime_ball, 1, 0));
        GameRegistry.addRecipe(new ItemStack(diffuser), ".d.", ".b.", "...", 'd', new ItemStack(Items.glass_bottle), 'b', new ItemStack(Items.bowl));

    }

    public static void registerBlocks()
    {

        diffuser = new DiffuserBlock().setCreativeTab(CreativeTabRegistry.APP_TAB);
        GameRegistry.registerBlock(diffuser, diffuser.getUnlocalizedName());
        GameRegistry.registerTileEntity(DiffuserTileEntity.class, diffuser.getUnlocalizedName() + "TileEntity");

        distillery = new DistilleryBlock().setCreativeTab(CreativeTabRegistry.APP_TAB);
        GameRegistry.registerBlock(distillery, distillery.getUnlocalizedName());
        GameRegistry.registerTileEntity(DistilleryTileEntity.class, distillery.getUnlocalizedName() + "TileEntity");

        fleshBlock = new FleshBlock(Naming.Blocks.FLESH).setCreativeTab(CreativeTabRegistry.APP_TAB);
        GameRegistry.registerBlock(fleshBlock, fleshBlock.getUnlocalizedName());

        fluidMixer = new FluidMixerBlock(Naming.Blocks.FLUID_MIXER).setCreativeTab(CreativeTabRegistry.APP_TAB);
        GameRegistry.registerBlock(fluidMixer, fluidMixer.getUnlocalizedName());
        GameRegistry.registerTileEntity(FluidMixerTileEntity.class, fluidMixer.getUnlocalizedName() + "TileEntity");

        potionJug = new PotionJugBlock(Naming.Blocks.POTION_JUG).setCreativeTab(CreativeTabRegistry.APP_TAB);
        GameRegistry.registerBlock(potionJug, potionJug.getUnlocalizedName());
        GameRegistry.registerTileEntity(PotionJugTileEntity.class, potionJug.getUnlocalizedName() + "TileEntity");
    }

}
