package alchemyplusplus.block;

import alchemyplusplus.reference.Names;
import alchemyplusplus.tileentities.potionjug.BlockPotionJug;
import alchemyplusplus.tileentities.mixer.BlockLiquidMixer;
import alchemyplusplus.tileentities.distillery.BlockDistillery;
import alchemyplusplus.tileentities.diffuser.BlockDiffuser;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;

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
        
        diffuser = new BlockDiffuser(Names.Blocks.DIFFUSER);
        GameRegistry.registerBlock(diffuser, diffuser.getUnlocalizedName());
        
        distillery = new BlockDistillery(Names.Blocks.DISTILLERY);
        GameRegistry.registerBlock(distillery, distillery.getUnlocalizedName());
        
        fleshBlock = new BlockFlesh(Names.Blocks.FLESH);
        GameRegistry.registerBlock(fleshBlock, fleshBlock.getUnlocalizedName());
        
        liquidMixer = new BlockLiquidMixer(Names.Blocks.LIQUID_MIXER);
        GameRegistry.registerBlock(liquidMixer, liquidMixer.getUnlocalizedName());
        
        potionJug = new BlockPotionJug(Names.Blocks.POTION_JUG);
        GameRegistry.registerBlock(potionJug, potionJug.getUnlocalizedName());
    }

}
