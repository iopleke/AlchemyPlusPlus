package mokonaDesu.alchemypp.blocks;

import mokonaDesu.alchemypp.APPIDManager;
import mokonaDesu.alchemypp.AlchemyPP;
import mokonaDesu.alchemypp.items.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockRegistry {

	//version 1.0
	
	public static final Block appBlockOrichalcumOre = new BlockOre(APPIDManager.nextBlockID());
	public static final Block appBlockPotionContainer = new BlockPotionContainer(APPIDManager.nextBlockID());
	public static final Block appBlockLiquidMixer = new BlockLiquidMixer(APPIDManager.nextBlockID());
	public static final Block appBlockOrichalcum = new APPBlock(APPIDManager.nextBlockID(), Material.iron, AlchemyPP.alternativeTextures ? "AlchemyPP:OrichalcumBlockAlt" : "AlchemyPP:OrichalcumBlock")
	.setCreativeTab(CreativeTabs.tabBlock).setHardness(3.0F).setResistance(5.0F).setUnlocalizedName("orichalcumBlock");
	public static final Block appBlockExtractor = new BlockExtractor(APPIDManager.nextBlockID());
	public static final Block appBlockFlesh = new BlockFlesh(APPIDManager.nextBlockID(), Material.cloth)
	.setCreativeTab(CreativeTabs.tabBlock).setHardness(3.0F).setResistance(5.0F).setUnlocalizedName("fleshBlock");
	
	//version 1.1
	public static final Block appBlockAlchemicalApparatus = new BlockAlchemicalApparatus(APPIDManager.nextBlockID(), Material.piston)
	.setUnlocalizedName("blockApparatus").setHardness(3.0F).setResistance(0.1F);
	
	public static void registerBlocks() {
	
	GameRegistry.registerBlock(appBlockOrichalcumOre, "blockOre");
	LanguageRegistry.addName(appBlockOrichalcumOre, "Orichalcum Ore");
	
	GameRegistry.registerBlock(appBlockPotionContainer, "blockPotionContainer");
	LanguageRegistry.addName(appBlockPotionContainer,  "Potion Bottle");
	
	GameRegistry.registerBlock(appBlockLiquidMixer, "blockLiquidMixer");
	LanguageRegistry.addName(appBlockLiquidMixer,  "Liquid Mixer");
	
	GameRegistry.registerBlock(appBlockOrichalcum, "blockOrichalcum");
	LanguageRegistry.addName(appBlockOrichalcum,  "Block of Orichalcum");
	
	GameRegistry.registerBlock(appBlockExtractor, "blockExtractor");
	LanguageRegistry.addName(appBlockExtractor, "Essence Extractor");
	
	GameRegistry.registerBlock(appBlockFlesh, "blockFlesh");
	LanguageRegistry.addName(appBlockFlesh, "Block of Flesh");
	
	GameRegistry.registerBlock(appBlockAlchemicalApparatus, "blockApparatus");
	}
	
	public static void registerBlockRecipes() {
	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appBlockOrichalcum, 1, 0), "xxx", "x x", "xxx", 'x', ItemRegistry.oreDictOrichalcum));
	GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.appItemOrichalcumBar, 8, 0), new ItemStack(appBlockOrichalcum) );
	
	GameRegistry.addRecipe(new ItemStack(appBlockFlesh, 1, 0),
			"xxx", "xsx", "xxx",
			'x', new ItemStack(Item.rottenFlesh, 1, 0),
			's', new ItemStack(Item.slimeBall, 1, 0));
	
	}
	
}
