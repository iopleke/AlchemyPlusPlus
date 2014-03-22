package mokonaDesu.alchemypp.items;

import java.util.ArrayList;

import mokonaDesu.alchemypp.APPConfigManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemRegistry {

    public static final Item appItemBottleStand = new ItemBottleStand(APPConfigManager.appItemBottleStand, "AlchemyPP:BottleStand").setUnlocalizedName("bottleStandItem").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemConfusion = new APPItem(APPConfigManager.appItemConfusion, "AlchemyPP:confusion").setUnlocalizedName("confusion").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemDiffuser = new ItemDiffuser(APPConfigManager.appItemDiffuser, "AlchemyPP:Diffuser").setUnlocalizedName("diffuserItem").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemDistillery = new ItemDistillery(APPConfigManager.appItemDistillery, "AlchemyPP:Distillery").setUnlocalizedName("distilleryItem").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemExtractor = new ItemExtractor(APPConfigManager.appItemExtractor, "AlchemyPP:Extractor").setUnlocalizedName("extractorItem").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemFesteringFlesh = new APPItemFood(APPConfigManager.appItemFesteringFlesh, "AlchemyPP:FesteringFlesh", 7, 100, 20).setUnlocalizedName("flesh").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemFilter = new ItemMixingFilter(APPConfigManager.appItemFilter, "AlchemyPP:AlchemicalFilter", 600).setUnlocalizedName("filter");
    public static final Item appItemFishOil = new ItemFishOil(APPConfigManager.appItemFishOil).setUnlocalizedName("fishoil").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemIronPowder = new APPItem(APPConfigManager.appItemIronPowder, "AlchemyPP:ironDust").setUnlocalizedName("obsidian").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemLiquidMixer = new ItemLiquidMixer(APPConfigManager.appItemLiquidMixer, "AlchemyPP:Mixer").setUnlocalizedName("mixerItem").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemPotionBottle = new ItemPotionBottle(APPConfigManager.appItemPotionBottle).setUnlocalizedName("potionBottle").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemSpirit = new ItemSpirit(APPConfigManager.appItemSpirit).setCreativeTab(APPConfigManager.appCreativeTab).setUnlocalizedName("spiritItem");
    public static final Item appItemSpiritLamp = new ItemSpiritLamp(APPConfigManager.appItemSpiritLamp, "AlchemyPP:SpiritLamp").setUnlocalizedName("spiritlampItem").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemSprayer = new ItemSprayer(APPConfigManager.appItemSprayer, "AlchemyPP:Sprayer").setUnlocalizedName("sprayer").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemSpringyCord = new APPItem(APPConfigManager.appItemSpringyCord, "AlchemyPP:spring").setUnlocalizedName("spring").setCreativeTab(APPConfigManager.appCreativeTab);
    public static final Item appItemSquidEye = new APPItem(APPConfigManager.appItemSquidEye, "AlchemyPP:SquidEye").setUnlocalizedName("squidEye").setCreativeTab(APPConfigManager.appCreativeTab);
    public static Item appItemPotion = null;

    /*
     * public static final int digSpeedID = APPIDManager.nextItemID();
     * //placeholder public static final int digSlowdownID =
     * APPIDManager.nextItemID(); //placeholder public static final int
     * healthBoost = APPIDManager.nextItemID(); //placeholder public static
     * final int absorption = APPIDManager.nextItemID(); //placeholder public
     * static final int health = APPIDManager.nextItemID(); //placeholder
     */

    public static void registerItems() {

        GameRegistry.registerItem(appItemFilter, "Filter");
        LanguageRegistry.addName(appItemFilter, "Filter");

        GameRegistry.registerItem(appItemFesteringFlesh, "festeringFlesh");
        LanguageRegistry.addName(appItemFesteringFlesh, "Festering Flesh");

        GameRegistry.registerItem(appItemSquidEye, "squidEye");
        LanguageRegistry.addName(appItemSquidEye, "Squid's Eye");

        GameRegistry.registerItem(appItemConfusion, "confusion");
        LanguageRegistry.addName(appItemConfusion, "Awkward Feeling");

        GameRegistry.registerItem(appItemSpringyCord, "spring");
        LanguageRegistry.addName(appItemSpringyCord, "Springy Cord");

        GameRegistry.registerItem(appItemIronPowder, "obsidian");
        LanguageRegistry.addName(appItemIronPowder, "Iron Powder");

        GameRegistry.registerItem(appItemFishOil, "fishOil");
        LanguageRegistry.addName(appItemFishOil, "Fish Oil");

        GameRegistry.registerItem(appItemPotionBottle, "potionBottle");
        LanguageRegistry.addName(appItemPotionBottle, "Potion Bottle");

        GameRegistry.registerItem(appItemExtractor, "extractorItem");
        LanguageRegistry.addName(appItemExtractor, "Essence Extractor");

        GameRegistry.registerItem(appItemDistillery, "distilleryItem");
        LanguageRegistry.addName(appItemDistillery, "Distillery");

        GameRegistry.registerItem(appItemDiffuser, "diffuserItem");
        LanguageRegistry.addName(appItemDiffuser, "Diffuser");

        GameRegistry.registerItem(appItemLiquidMixer, "mixerItem");
        LanguageRegistry.addName(appItemLiquidMixer, "Liquid Mixer");

        GameRegistry.registerItem(appItemSpirit, "spiritItem");
        LanguageRegistry.addName(appItemSpirit, "Spirit Bottle");

        GameRegistry.registerItem(appItemSpiritLamp, "spiritlampItem");
        LanguageRegistry.addName(appItemSpiritLamp, "Spirit Lamp");

        GameRegistry.registerItem(appItemBottleStand, "bottleStandItem");
        LanguageRegistry.addName(appItemBottleStand, "Bottle Stand");

        GameRegistry.registerItem(appItemSprayer, "sprayer");
        LanguageRegistry.addName(appItemSprayer, "Diffuser");
        // TODO: overriding potion

        if (APPConfigManager.appVanillaPotionOverride) {
            ItemPotion p = Item.potion;
            Item.itemsList[117] = null;
            appItemPotion = new ItemAPPPotion(117, p).setUnlocalizedName("potion").setTextureName("potion");
            Item.potion = (ItemPotion) appItemPotion;
            GameRegistry.registerItem(appItemPotion, "potion");
        }

    }

    public static void registerItemRecipes() {

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appItemPotionBottle), "gwg", "o o", "gog", 'g', new ItemStack(Block.thinGlass), 'w', "logWood", 'o', Block.blockIron));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appItemFilter), " og", "oDo", "go ", 'g', new ItemStack(Block.thinGlass), 'D', new ItemStack(Item.diamond), 'o', Block.blockIron));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appItemFilter), "go ", "oDo", " og", 'g', new ItemStack(Block.thinGlass), 'D', new ItemStack(Item.diamond), 'o', Block.blockIron));

        GameRegistry.addShapelessRecipe(new ItemStack(appItemFishOil, 1, 0), new ItemStack(Item.fishRaw), new ItemStack(Item.glassBottle));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appItemLiquidMixer), "x x", "xix", "oOo", 'x', new ItemStack(Block.thinGlass), 'i', new ItemStack(Item.ingotIron), 'o', Block.blockIron, 'O', new ItemStack(Block.blockIron)));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appItemExtractor), "iGi", "I O", "oOo", 'i', new ItemStack(Block.blockGold), 'I', new ItemStack(Block.blockIron), 'g', new ItemStack(Item.ingotGold), 'o', Block.blockIron, 'O', new ItemStack(Block.blockIron)));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appItemSpiritLamp), "gog", "gsg", "gog", 'g', new ItemStack(Block.thinGlass), 's', new ItemStack(Item.silk), 'o', Block.blockIron));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appItemSprayer), "ofo", "-w-", "-w-", 'o', Block.blockIron, 'f', new ItemStack(appItemFilter), 'w', new ItemStack(Block.cloth)));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(appItemBottleStand), "obo", "o-o", "o-o", 'o', Block.blockIron, 'b', new ItemStack(Block.fenceIron)));

        GameRegistry.addRecipe(new ItemStack(appItemDistillery, 1, 0), new Object[] { "Bi-", "C-P", "F-S", Character.valueOf('B'), Item.brewingStand, Character.valueOf('i'), Item.ingotIron, Character.valueOf('C'), Item.cauldron, Character.valueOf('P'), appItemPotionBottle, Character.valueOf('F'), Block.furnaceIdle, Character.valueOf('S'), appItemBottleStand });

        GameRegistry.addRecipe(new ItemStack(appItemDiffuser, 1, 0), new Object[] { "--s", "-b-", "w--", Character.valueOf('s'), Item.silk, Character.valueOf('b'), Item.glassBottle, Character.valueOf('w'), Item.bowlEmpty });
    }

    public static void registerHardcoreRecipes() {

        System.out.println("Alchemy++ is registering its hardcore recipes! This requires modifyng vanilla recipe list.");

        // first remove the standard vanilla ones!
        ArrayList list = (ArrayList) CraftingManager.getInstance().getRecipeList();

        for (int i = 0; i < list.size(); i++) {
            if (ItemStack.areItemStacksEqual(((IRecipe) list.get(i)).getRecipeOutput(), new ItemStack(Item.speckledMelon))) {
                System.out.println("Alchemy++ removes \"speckeledMelon\" recipe from the recipe list! Be advised!");
                list.remove(i);
            }

        }

        GameRegistry.addRecipe(new ItemStack(Item.speckledMelon), "ggg", "gmg", "ggg", 'g', new ItemStack(Item.ingotGold), 'm', new ItemStack(Item.melon));

    }

}
