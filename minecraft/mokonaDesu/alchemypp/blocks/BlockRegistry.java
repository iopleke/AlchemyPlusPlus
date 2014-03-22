package mokonaDesu.alchemypp.blocks;

import mokonaDesu.alchemypp.APPConfigManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockRegistry {
    public static final Block appBlockAlchemicalApparatus = new BlockAlchemicalApparatus(APPConfigManager.appBlockAlchemicalApparatus, Material.piston).setUnlocalizedName("blockApparatus").setHardness(3.0F).setResistance(0.1F);
    public static final Block appBlockDiffuser = new BlockDiffuser(APPConfigManager.appBlockDiffuser);
    public static final Block appBlockDistillery = new BlockDistillery(APPConfigManager.appBlockDistillery);
    public static final Block appBlockExtractor = new BlockExtractor(APPConfigManager.appBlockExtractor);
    public static final Block appBlockFlesh = new BlockFlesh(APPConfigManager.appBlockFlesh, Material.cloth).setCreativeTab(APPConfigManager.appCreativeTab).setHardness(3.0F).setResistance(5.0F).setUnlocalizedName("fleshBlock");
    public static final Block appBlockLiquidMixer = new BlockLiquidMixer(APPConfigManager.appBlockLiquidMixer);
    public static final Block appBlockPotionContainer = new BlockPotionContainer(APPConfigManager.appBlockPotionContainer);

    public static void registerBlocks() {
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

        GameRegistry.registerBlock(appBlockAlchemicalApparatus, "blockApparatus");
    }

    public static void registerBlockRecipes() {
        GameRegistry.addRecipe(new ItemStack(appBlockFlesh, 1, 0), "xxx", "xsx", "xxx", 'x', new ItemStack(Item.rottenFlesh, 1, 0), 's', new ItemStack(Item.slimeBall, 1, 0));

    }

}
