package mokonaDesu.alchemypp.items;

import java.util.ArrayList;

import mokonaDesu.alchemypp.APPIDManager;
import mokonaDesu.alchemypp.AlchemyPP;
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

    // version 1.0
    public static final Item appItemFilter = new ItemMixingFilter(
            APPIDManager.nextItemID(), "AlchemyPP:AlchemicalFilter", 600)
            .setUnlocalizedName("filter");

    public static final Item appItemFesteringFlesh = new APPItemFood(
            APPIDManager.nextItemID(), "AlchemyPP:FesteringFlesh", 7, 100, 20)
            .setUnlocalizedName("flesh").setCreativeTab(AlchemyPP.alchemyPPTab);
    public static final Item appItemSquidEye = new APPItem(
            APPIDManager.nextItemID(), "AlchemyPP:SquidEye")
            .setUnlocalizedName("squidEye").setCreativeTab(
                    AlchemyPP.alchemyPPTab);
    public static final Item appItemConfusion = new APPItem(
            APPIDManager.nextItemID(), "AlchemyPP:confusion")
            .setUnlocalizedName("confusion").setCreativeTab(
                    AlchemyPP.alchemyPPTab);
    public static final Item appItemSpringyCord = new APPItem(
            APPIDManager.nextItemID(), "AlchemyPP:spring").setUnlocalizedName(
            "spring").setCreativeTab(AlchemyPP.alchemyPPTab);
    public static final Item appItemIronPowder = new APPItem(
            APPIDManager.nextItemID(), "AlchemyPP:ironDust")
            .setUnlocalizedName("obsidian").setCreativeTab(
                    AlchemyPP.alchemyPPTab);
    public static final Item appItemFishOil = new ItemFishOil(
            APPIDManager.nextItemID()).setUnlocalizedName("fishoil")
            .setCreativeTab(AlchemyPP.alchemyPPTab);
    public static final Item appItemPotionBottle = new ItemPotionBottle(
            APPIDManager.nextItemID()).setUnlocalizedName("potionBottle")
            .setCreativeTab(AlchemyPP.alchemyPPTab);
    public static final Item appItemExtractor = new ItemExtractor(
            APPIDManager.nextItemID(), "AlchemyPP:Extractor")
            .setUnlocalizedName("extractorItem").setCreativeTab(
                    AlchemyPP.alchemyPPTab);
    public static final Item appItemDistillery = new ItemDistillery(
            APPIDManager.nextItemID(), "AlchemyPP:Distillery")
            .setUnlocalizedName("distilleryItem").setCreativeTab(
                    AlchemyPP.alchemyPPTab);
    public static final Item appItemDiffuser = new ItemDiffuser(
            APPIDManager.nextItemID(), "AlchemyPP:Diffuser")
            .setUnlocalizedName("diffuserItem").setCreativeTab(
                    AlchemyPP.alchemyPPTab);
    public static final Item appItemLiquidMixer = new ItemLiquidMixer(
            APPIDManager.nextItemID(), "AlchemyPP:Mixer").setUnlocalizedName(
            "mixerItem").setCreativeTab(AlchemyPP.alchemyPPTab);

    public static Item appItemPotion = null;

    public static final Item appItemSpirit = new ItemSpirit(
            APPIDManager.nextItemID()).setCreativeTab(AlchemyPP.alchemyPPTab)
            .setUnlocalizedName("spiritItem");
    public static final Item appItemSpiritLamp = new ItemSpiritLamp(
            APPIDManager.nextItemID(), "AlchemyPP:SpiritLamp")
            .setUnlocalizedName("spiritlampItem").setCreativeTab(
                    AlchemyPP.alchemyPPTab);
    public static final Item appItemBottleStand = new ItemBottleStand(
            APPIDManager.nextItemID(), "AlchemyPP:BottleStand")
            .setUnlocalizedName("bottleStandItem").setCreativeTab(
                    AlchemyPP.alchemyPPTab);
    public static final Item appItemSprayer = new ItemSprayer(
            APPIDManager.nextItemID(), "AlchemyPP:Sprayer").setUnlocalizedName(
            "sprayer").setCreativeTab(AlchemyPP.alchemyPPTab);

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

        if (AlchemyPP.potionOverride) {
            ItemPotion p = Item.potion;
            Item.itemsList[117] = null;
            appItemPotion = new ItemAPPPotion(117, p).setUnlocalizedName(
                    "potion").setTextureName("potion");
            Item.potion = (ItemPotion) appItemPotion;
            GameRegistry.registerItem(appItemPotion, "potion");
        }

    }

    public static void registerItemRecipes() {

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
                appItemPotionBottle), "gwg", "o o", "gog", 'g', new ItemStack(
                Block.thinGlass), 'w', "logWood", 'o', Block.blockIron));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(appItemFilter), " og", "oDo", "go ", 'g',
                new ItemStack(Block.thinGlass), 'D',
                new ItemStack(Item.diamond), 'o', Block.blockIron));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(appItemFilter), "go ", "oDo", " og", 'g',
                new ItemStack(Block.thinGlass), 'D',
                new ItemStack(Item.diamond), 'o', Block.blockIron));

        GameRegistry.addShapelessRecipe(new ItemStack(appItemFishOil, 1, 0),
                new ItemStack(Item.fishRaw), new ItemStack(Item.glassBottle));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
                appItemLiquidMixer), "x x", "xix", "oOo", 'x', new ItemStack(
                Block.thinGlass), 'i', new ItemStack(Item.ingotIron), 'o',
                Block.blockIron, 'O', new ItemStack(Block.blockIron)));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
                appItemExtractor), "iGi", "I O", "oOo", 'i', new ItemStack(
                Block.blockGold), 'I', new ItemStack(Block.blockIron), 'g',
                new ItemStack(Item.ingotGold), 'o', Block.blockIron, 'O',
                new ItemStack(Block.blockIron)));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
                appItemSpiritLamp), "gog", "gsg", "gog", 'g', new ItemStack(
                Block.thinGlass), 's', new ItemStack(Item.silk), 'o',
                Block.blockIron));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(appItemSprayer), "ofo", "-w-", "-w-", 'o',
                Block.blockIron, 'f', new ItemStack(appItemFilter), 'w',
                new ItemStack(Block.cloth)));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
                appItemBottleStand), "obo", "o-o", "o-o", 'o', Block.blockIron,
                'b', new ItemStack(Block.fenceIron)));

        GameRegistry.addRecipe(
                new ItemStack(appItemDistillery, 1, 0),
                new Object[] { "Bi-", "C-P", "F-S", Character.valueOf('B'),
                        Item.brewingStand, Character.valueOf('i'),
                        Item.ingotIron, Character.valueOf('C'), Item.cauldron,
                        Character.valueOf('P'), appItemPotionBottle,
                        Character.valueOf('F'), Block.furnaceIdle,
                        Character.valueOf('S'), appItemBottleStand });

        GameRegistry.addRecipe(new ItemStack(appItemDiffuser, 1, 0),
                new Object[] { "--s", "-b-", "w--", Character.valueOf('s'),
                        Item.silk, Character.valueOf('b'), Item.glassBottle,
                        Character.valueOf('w'), Item.bowlEmpty });
    }

    public static void registerHardcoreRecipes() {

        System.out
                .println("Alchemy++ is registering its hardcore recipes! This requires modifyng vanilla recipe list.");

        // first remove the standard vanilla ones!
        ArrayList list = (ArrayList) CraftingManager.getInstance()
                .getRecipeList();

        for (int i = 0; i < list.size(); i++) {
            if (ItemStack.areItemStacksEqual(((IRecipe) list.get(i))
                    .getRecipeOutput(), new ItemStack(Item.speckledMelon))) {
                System.out
                        .println("Alchemy++ removes \"speckeledMelon\" recipe from the recipe list! Be advised!");
                list.remove(i);
            }

        }

        GameRegistry.addRecipe(new ItemStack(Item.speckledMelon), "ggg", "gmg",
                "ggg", 'g', new ItemStack(Item.ingotGold), 'm', new ItemStack(
                        Item.melon));

    }

}
