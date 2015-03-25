package alchemyplusplus.registry;

import alchemyplusplus.Config;
import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.item.FishOil;
import alchemyplusplus.item.MixingFilter;
import alchemyplusplus.item.MortarAndPestle;
import alchemyplusplus.item.PotionBucket;
import alchemyplusplus.item.flora.BasicFlora;
import alchemyplusplus.reference.Naming;
import cpw.mods.fml.common.registry.GameRegistry;
import jakimbox.prefab.item.BasicItem;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemRegistry
{

    public static Item confusionPotion;
    public static Item fishOil;
    public static Item ironPowder;
    public static Item mixingFilter;
    public static Item springyCord;
    public static Item squidEye;
    public static Item woodAlcohol;
    public static Item mortarAndPestle;

    // plant stuffs
    public static Item crushedGrass;
    public static Item pasteGrass;
    public static Item crushedLeaves;
    public static Item pasteLeaves;


    /*
     * public static final int digSpeedID = APPIDManager.nextItemID();
     * //placeholder public static final int digSlowdownID =
     * APPIDManager.nextItemID(); //placeholder public static final int
     * healthBoost = APPIDManager.nextItemID(); //placeholder public static
     * final int absorption = APPIDManager.nextItemID(); //placeholder public
     * static final int health = APPIDManager.nextItemID(); //placeholder
     */
    public static void registerItems()
    {
        confusionPotion = new BasicItem(AlchemyPlusPlus.ID, Naming.Items.CONFUSION_POTION);
        GameRegistry.registerItem(confusionPotion, confusionPotion.getUnlocalizedName());

        fishOil = new FishOil(Naming.Items.FISH_OIL);
        GameRegistry.registerItem(fishOil, fishOil.getUnlocalizedName());

        ironPowder = new BasicItem(AlchemyPlusPlus.ID, Naming.Items.IRON_POWDER);
        GameRegistry.registerItem(ironPowder, ironPowder.getUnlocalizedName());

        mixingFilter = new MixingFilter(Naming.Items.MIXING_FILTER);
        GameRegistry.registerItem(mixingFilter, mixingFilter.getUnlocalizedName());

        springyCord = new BasicItem(AlchemyPlusPlus.ID, Naming.Items.SPRINGY_CORD);
        GameRegistry.registerItem(springyCord, springyCord.getUnlocalizedName());

        squidEye = new BasicItem(AlchemyPlusPlus.ID, Naming.Items.SQUID_EYE);
        GameRegistry.registerItem(squidEye, squidEye.getUnlocalizedName());

        woodAlcohol = new MixingFilter(Naming.Items.WOOD_ALCOHOL);
        GameRegistry.registerItem(woodAlcohol, woodAlcohol.getUnlocalizedName());

        mortarAndPestle = new MortarAndPestle(Naming.Items.MortarAndPestle);
        GameRegistry.registerItem(mortarAndPestle, mortarAndPestle.getUnlocalizedName());

        // Register plant stuffs
        crushedGrass = new BasicFlora(Naming.Items.CrushedGrass);
        GameRegistry.registerItem(crushedGrass, crushedGrass.getUnlocalizedName());
        pasteGrass = new BasicFlora(Naming.Items.PasteGrass);
        GameRegistry.registerItem(pasteGrass, pasteGrass.getUnlocalizedName());

        crushedLeaves = new BasicFlora(Naming.Items.CrushedLeaves);
        GameRegistry.registerItem(crushedLeaves, crushedLeaves.getUnlocalizedName());
        pasteLeaves = new BasicFlora(Naming.Items.PasteLeaves);
        GameRegistry.registerItem(pasteLeaves, pasteLeaves.getUnlocalizedName());

// @TODO - override vanilla potions
        {
            if (Config.BrewingOverride)
            {
//              @TODO - see if this is even still possible...maybe check how Railcraft does it?
//              ItemPotion potion = Items.potionitem;
//              Item.itemRegistry = null;
//              appItemPotion = new PotionTemplate(potion).setUnlocalizedName("potion").setTextureName("potion");
//              Items.potionitem = (ItemPotion) appItemPotion;
//              GameRegistry.registerItem(appItemPotion, "potion");
            }
        }

    }

    public static void addCreativePotionBucket(Block block, Potion potion)
    {
        PotionBucket potionBucket = new PotionBucket(block, potion);
        potionBucket.setCreativeTab(CreativeTabRegistry.APP_TAB);
        potionBucket.setUnlocalizedName(potion.getName());
        GameRegistry.registerItem(potionBucket, potion.getName());
    }

    public static void registerHardcoreRecipes()
    {

        if (Config.DebugMode)
        {
            AlchemyPlusPlus.logger.info("Alchemy++ is registering its hardcore recipes! This requires modifyng vanilla recipe list.");
        }
        // first remove the standard vanilla ones!
        ArrayList list = (ArrayList) CraftingManager.getInstance().getRecipeList();

        for (int i = 0; i < list.size(); i++)
        {
            if (ItemStack.areItemStacksEqual(((IRecipe) list.get(i)).getRecipeOutput(), new ItemStack(Items.speckled_melon)))
            {
                if (Config.DebugMode)
                {
                    AlchemyPlusPlus.logger.info("Alchemy++ removes \"speckeledMelon\" recipe from the recipe list! Be advised!");
                }
                list.remove(i);
            }

        }

        GameRegistry.addRecipe(new ItemStack(Items.speckled_melon), "ggg", "gmg", "ggg", 'g', new ItemStack(Items.gold_ingot), 'm', new ItemStack(Items.melon));

    }

    public static void registerItemRecipes()
    {

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockRegistry.potionJug), "gwg", "o o", "gog", 'g', new ItemStack(Blocks.glass_pane), 'w', "logWood", 'o', Blocks.iron_block));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mixingFilter), " og", "oDo", "go ", 'g', new ItemStack(Blocks.glass_pane), 'D', new ItemStack(Items.diamond), 'o', Blocks.iron_block));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mixingFilter), "go ", "oDo", " og", 'g', new ItemStack(Blocks.glass_pane), 'D', new ItemStack(Items.diamond), 'o', Blocks.iron_block));
        GameRegistry.addShapelessRecipe(new ItemStack(fishOil, 1, 0), new ItemStack(Items.fish), new ItemStack(Items.glass_bottle));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockRegistry.fluidMixer), "x x", "xix", "oOo", 'x', new ItemStack(Blocks.glass_pane), 'i', new ItemStack(Items.iron_ingot), 'o', Blocks.iron_block, 'O', new ItemStack(Blocks.iron_block)));
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.distillery, 1, 0), new Object[]
        {
            "Bi-", "C-P", "F-S", Character.valueOf('B'), Items.brewing_stand, Character.valueOf('i'), Items.iron_ingot, Character.valueOf('C'), Items.cauldron, Character.valueOf('P'), BlockRegistry.potionJug, Character.valueOf('F'), Blocks.furnace, Character.valueOf('S'), Items.iron_ingot
        });
    }

}
