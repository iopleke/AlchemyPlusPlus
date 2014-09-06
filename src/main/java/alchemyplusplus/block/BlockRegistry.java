package alchemyplusplus.block;

import alchemyplusplus.tileentities.potioncontainer.BlockPotionContainer;
import alchemyplusplus.tileentities.mixer.BlockLiquidMixer;
import alchemyplusplus.tileentities.extractor.BlockExtractor;
import alchemyplusplus.tileentities.distillery.BlockDistillery;
import alchemyplusplus.tileentities.diffuser.BlockDiffuser;
import alchemyplusplus.utility.ConfigManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.init.Items;

public class BlockRegistry
{
    public static final Block appBlockDiffuser = new BlockDiffuser(ConfigManager.appBlockDiffuser);
    public static final Block appBlockDistillery = new BlockDistillery(ConfigManager.appBlockDistillery);
    public static final Block appBlockExtractor = new BlockExtractor(ConfigManager.appBlockExtractor);
    public static final Block appBlockFlesh = new BlockFlesh(ConfigManager.appBlockFlesh, Material.cloth);
    public static final Block appBlockLiquidMixer = new BlockLiquidMixer(ConfigManager.appBlockLiquidMixer);
    public static final Block appBlockPotionContainer = new BlockPotionContainer(ConfigManager.appBlockPotionContainer);

    public static void registerBlockRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(appBlockFlesh, 1, 0), "xxx", "xsx", "xxx", 'x', new ItemStack(Items.rotten_flesh, 1, 0), 's', new ItemStack(Items.slime_ball, 1, 0));

    }

    public static void registerBlocks()
    {
        GameRegistry.registerBlock(appBlockPotionContainer, "blockPotionContainer");
        LanguageRegistry.addName(appBlockPotionContainer, "Potion Bottle");

        GameRegistry.registerBlock(appBlockLiquidMixer, "blockLiquidMixer");
        LanguageRegistry.addName(appBlockLiquidMixer, "Liquid Mixer");

        GameRegistry.registerBlock(appBlockExtractor, "blockExtractor");
        LanguageRegistry.addName(appBlockExtractor, "Essence Extractor");

        GameRegistry.registerBlock(appBlockDistillery, "blockDistillery");
        LanguageRegistry.addName(appBlockDistillery, "Distillery");

        GameRegistry.registerBlock(appBlockDiffuser, "blockDiffuser");
        LanguageRegistry.addName(appBlockDiffuser, "Diffuser");

        GameRegistry.registerBlock(appBlockFlesh, "blockFlesh");
        LanguageRegistry.addName(appBlockFlesh, "Block of Flesh");
    }

}
