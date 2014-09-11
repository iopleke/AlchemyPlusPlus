package alchemyplusplus;

import alchemyplusplus.item.FishOil;
import alchemyplusplus.item.ItemBasic;
import alchemyplusplus.item.MixingFilter;
import alchemyplusplus.reference.Naming;
import alchemyplusplus.utility.ConfigManager;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;

public class ItemRegistry
{

    public static Item confusionPotion;
    public static Item fishOil;
    public static Item ironPowder;
    public static Item mixingFilter;
    public static Item springyCord;
    public static Item squidEye;
    public static Item woodAlcohol;


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
        confusionPotion = new ItemBasic(Naming.Items.CONFUSION_POTION);
        GameRegistry.registerItem(confusionPotion, confusionPotion.getUnlocalizedName());
        
        fishOil = new FishOil(Naming.Items.FISH_OIL);
        GameRegistry.registerItem(fishOil, fishOil.getUnlocalizedName());
        
        ironPowder = new ItemBasic(Naming.Items.IRON_POWDER);
        GameRegistry.registerItem(ironPowder, ironPowder.getUnlocalizedName());
        
        mixingFilter = new MixingFilter(Naming.Items.MIXING_FILTER);
        GameRegistry.registerItem(mixingFilter, mixingFilter.getUnlocalizedName());
        
        springyCord = new ItemBasic(Naming.Items.SPRINGY_CORD);
        GameRegistry.registerItem(springyCord, springyCord.getUnlocalizedName());
        
        squidEye = new ItemBasic(Naming.Items.SQUID_EYE);
        GameRegistry.registerItem(squidEye, squidEye.getUnlocalizedName());
        
        woodAlcohol = new MixingFilter(Naming.Items.WOOD_ALCOHOL);
        GameRegistry.registerItem(woodAlcohol, woodAlcohol.getUnlocalizedName());

        // work in progress: override vanilla potions
        if (ConfigManager.appVanillaPotionOverride)
        {
            // @TODO - see if this is even still possible...maybe check how Railcraft does it?
//            ItemPotion potion = Items.potionitem;
//            Item.itemRegistry = null;
//            appItemPotion = new PotionTemplate(potion).setUnlocalizedName("potion").setTextureName("potion");
//            Items.potionitem = (ItemPotion) appItemPotion;
//            GameRegistry.registerItem(appItemPotion, "potion");
        }

    }

    public static void registerHardcoreRecipes()
    {

        System.out.println("Alchemy++ is registering its hardcore recipes! This requires modifyng vanilla recipe list.");

        // first remove the standard vanilla ones!
        ArrayList list = (ArrayList) CraftingManager.getInstance().getRecipeList();

        for (int i = 0; i < list.size(); i++)
        {
            if (ItemStack.areItemStacksEqual(((IRecipe) list.get(i)).getRecipeOutput(), new ItemStack(Items.speckled_melon)))
            {
                System.out.println("Alchemy++ removes \"speckeledMelon\" recipe from the recipe list! Be advised!");
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
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockRegistry.liquidMixer), "x x", "xix", "oOo", 'x', new ItemStack(Blocks.glass_pane), 'i', new ItemStack(Items.iron_ingot), 'o', Blocks.iron_block, 'O', new ItemStack(Blocks.iron_block)));
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.distillery, 1, 0), new Object[]
        {
            "Bi-", "C-P", "F-S", Character.valueOf('B'), Items.brewing_stand, Character.valueOf('i'), Items.iron_ingot, Character.valueOf('C'), Items.cauldron, Character.valueOf('P'), BlockRegistry.potionJug, Character.valueOf('F'), Blocks.furnace, Character.valueOf('S'), Items.iron_ingot
        });

        // GameRegistry.addRecipe(new ItemStack(appItemDiffuser, 1, 0), new Object[] { "--s", "-b-", "w--", Character.valueOf('s'), Item.silk, Character.valueOf('b'), Item.glassBottle, Character.valueOf('w'), Item.bowlEmpty });
    }

}
