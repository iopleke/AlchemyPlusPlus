package alchemyplusplus;

import alchemyplusplus.block.basic.FleshBlock;
import alchemyplusplus.reference.Naming;
import alchemyplusplus.block.complex.diffuser.DiffuserBlock;
import alchemyplusplus.block.complex.distillery.DistilleryBlock;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerBlock;
import alchemyplusplus.block.complex.potionJug.PotionJugBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlockRegistry
{
    public static Block diffuser;
    public static Block distillery;
    public static Block fleshBlock;
    public static Block liquidMixer;
    public static Block potionJug;

    public static void registerBlockRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(fleshBlock, 1, 0), "xxx", "xsx", "xxx", 'x', new ItemStack(Items.rotten_flesh, 1, 0), 's', new ItemStack(Items.slime_ball, 1, 0));

    }

    public static void registerBlocks()
    {
        
        diffuser = new DiffuserBlock(Naming.Blocks.DIFFUSER);
        GameRegistry.registerBlock(diffuser, diffuser.getUnlocalizedName());
        
        distillery = new DistilleryBlock(Naming.Blocks.DISTILLERY);
        GameRegistry.registerBlock(distillery, distillery.getUnlocalizedName());
        
        fleshBlock = new FleshBlock(Naming.Blocks.FLESH);
        GameRegistry.registerBlock(fleshBlock, fleshBlock.getUnlocalizedName());
        
        liquidMixer = new FluidMixerBlock(Naming.Blocks.LIQUID_MIXER);
        GameRegistry.registerBlock(liquidMixer, liquidMixer.getUnlocalizedName());
        
        potionJug = new PotionJugBlock(Naming.Blocks.POTION_JUG);
        GameRegistry.registerBlock(potionJug, potionJug.getUnlocalizedName());
    }

}
