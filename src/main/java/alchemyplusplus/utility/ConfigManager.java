package alchemyplusplus.utility;

import alchemyplusplus.gui.CreativeTab;
import java.io.File;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;

public class ConfigManager
{
    // Major thanks to micdoodle for configuration help

    // Defining the block ID variables
    public static int appBlockAlchemicalApparatus;
    public static int appBlockDiffuser;
    public static int appBlockDistillery;
    public static int appBlockExtractor;
    public static int appBlockFlesh;
    public static int appBlockLiquidMixer;
    public static int appBlockPotionContainer;
    // Configuration variables
    public static Configuration appConfig;
    private static boolean appConfigLoaded;
    public static CreativeTabs appCreativeTab;
    public static boolean appDebugMode;
    // General settings
    public static boolean appHardcoreModeEnabled;

    // Defining the item ID variables
    public static int appItemBottleStand;
    public static int appItemConfusion;
    public static int appItemDiffuser;
    public static int appItemDistillery;
    public static int appItemExtractor;
    public static int appItemFesteringFlesh;
    public static int appItemFilter;
    public static int appItemFishOil;
    public static int appItemIronPowder;
    public static int appItemLiquidMixer;
    public static int appItemPotionBottle;
    public static int appItemSpirit;
    public static int appItemSpiritLamp;
    public static int appItemSprayer;
    public static int appItemSpringyCord;
    public static int appItemSquidEye;

    public static boolean appVanillaBrewingOverride;
    public static boolean appVanillaPotionOverride;

    // Set the starting block and item IDs
    private static int dynamicBlockID = 400;
    private static int dynamicItemID = 2300;
    private static HashMap<Integer, Boolean> usedBlockIDs = new HashMap<Integer, Boolean>();
    private static HashMap<Integer, Boolean> usedItemIDs = new HashMap<Integer, Boolean>();

    public static void initialSetup(File config)
    {

        if (!appConfigLoaded)
        {
            appConfig = new Configuration(config);
        }

        try
        {

            // And so it begins...
            appConfig.load();

            // Setting all the block IDs
            appBlockAlchemicalApparatus = appConfig.get(Configuration.CATEGORY_BLOCK, "appAlchemicalApparatusID", nextBlockID()).getInt(dynamicBlockID);
            appBlockDiffuser = appConfig.get(Configuration.CATEGORY_BLOCK, "appDiffuserID", nextBlockID()).getInt(dynamicBlockID);
            appBlockDistillery = appConfig.get(Configuration.CATEGORY_BLOCK, "appDistilleryID", nextBlockID()).getInt(dynamicBlockID);
            appBlockExtractor = appConfig.get(Configuration.CATEGORY_BLOCK, "appExtractorID", nextBlockID()).getInt(dynamicBlockID);
            appBlockFlesh = appConfig.get(Configuration.CATEGORY_BLOCK, "appBlockFleshID", nextBlockID()).getInt(dynamicBlockID);
            appBlockLiquidMixer = appConfig.get(Configuration.CATEGORY_BLOCK, "appBlockLiquidMixerID", nextBlockID()).getInt(dynamicBlockID);
            appBlockPotionContainer = appConfig.get(Configuration.CATEGORY_BLOCK, "appPotionContainerID", nextBlockID()).getInt(dynamicBlockID);

            // Setting all the item IDs
            appItemBottleStand = appConfig.get(Configuration.CATEGORY_ITEM, "appBottleStandID", nextItemID()).getInt(dynamicItemID);
            appItemConfusion = appConfig.get(Configuration.CATEGORY_ITEM, "appConfusionID", nextItemID()).getInt(dynamicItemID);
            appItemDiffuser = appConfig.get(Configuration.CATEGORY_ITEM, "appDiffuserID", nextItemID()).getInt(dynamicItemID);
            appItemDistillery = appConfig.get(Configuration.CATEGORY_ITEM, "appDistilleryID", nextItemID()).getInt(dynamicItemID);
            appItemExtractor = appConfig.get(Configuration.CATEGORY_ITEM, "appExtractorID", nextItemID()).getInt(dynamicItemID);
            appItemFesteringFlesh = appConfig.get(Configuration.CATEGORY_ITEM, "appFesteringFleshID", nextItemID()).getInt(dynamicItemID);
            appItemFilter = appConfig.get(Configuration.CATEGORY_ITEM, "appFilterID", nextItemID()).getInt(dynamicItemID);
            appItemFishOil = appConfig.get(Configuration.CATEGORY_ITEM, "appFishOilID", nextItemID()).getInt(dynamicItemID);
            appItemIronPowder = appConfig.get(Configuration.CATEGORY_ITEM, "appIronPowderID", nextItemID()).getInt(dynamicItemID);
            appItemLiquidMixer = appConfig.get(Configuration.CATEGORY_ITEM, "appLiquidMixerID", nextItemID()).getInt(dynamicItemID);
            appItemPotionBottle = appConfig.get(Configuration.CATEGORY_ITEM, "appPotionBottleID", nextItemID()).getInt(dynamicItemID);
            appItemSpirit = appConfig.get(Configuration.CATEGORY_ITEM, "appSpiritID", nextItemID()).getInt(dynamicItemID);
            appItemSpiritLamp = appConfig.get(Configuration.CATEGORY_ITEM, "appSpiritLampID", nextItemID()).getInt(dynamicItemID);
            appItemSprayer = appConfig.get(Configuration.CATEGORY_ITEM, "appSprayerID", nextItemID()).getInt(dynamicItemID);
            appItemSpringyCord = appConfig.get(Configuration.CATEGORY_ITEM, "appSpringyCordID", nextItemID()).getInt(dynamicItemID);
            appItemSquidEye = appConfig.get(Configuration.CATEGORY_ITEM, "appSquidEyeID", nextItemID()).getInt(dynamicItemID);

            // Setting the general options
            appHardcoreModeEnabled = appConfig.get(Configuration.CATEGORY_GENERAL, "appHardcoreModeEnabled", false, "Enable hardcore game mechanic, making the game more challenging").getBoolean(false);
            appVanillaBrewingOverride = appConfig.get(Configuration.CATEGORY_GENERAL, "appVanillaBrewingOverride", false, "Enable this to override vanilla brewing mechanics").getBoolean(false);
            appVanillaPotionOverride = appConfig.get(Configuration.CATEGORY_GENERAL, "appVanillaPotionOverride", false, "Enable this to override vanilla potion mechanics").getBoolean(false);
            appDebugMode = appConfig.get(Configuration.CATEGORY_GENERAL, "appDebugMode", false, "Enable extra logging (May spam your console. You've been warned.)").getBoolean(false);

            // That wasn't so bad, now was it?
            setupCreativeTab();

        } catch (final Exception e)
        {
            System.err.println("Problem loading config");

        } finally
        {
            if (appConfig.hasChanged())
            {
                appConfig.save();
            }

            appConfigLoaded = true;
        }

        // Use nextBlockID() to get the next possible block ID
    }

    public static int nextBlockID()
    {
        boolean blockIDInUse = usedBlockIDs.containsKey(dynamicBlockID);
        while (Block.blocksList[dynamicBlockID] != null || blockIDInUse)
        {
            blockIDInUse = usedBlockIDs.containsKey(dynamicBlockID);
            if (appDebugMode)
            {
                System.err.println("Alchemy++: Block ID '" + dynamicBlockID + "' is occupied, checking next ID...");
            }
            dynamicBlockID++;
        }
        if (appDebugMode)
        {
            System.err.println("Alchemy++: Block ID set to " + dynamicBlockID);
        }
        usedBlockIDs.put(dynamicBlockID, true);
        return dynamicBlockID;
    }

    public static int nextItemID()
    {
        boolean itemIDInUse = usedItemIDs.containsKey(dynamicItemID);
        while (Item.itemsList[dynamicItemID] != null || itemIDInUse)
        {
            itemIDInUse = usedItemIDs.containsKey(dynamicItemID);
            if (appDebugMode)
            {
                System.err.println("Alchemy++: Item ID '" + dynamicItemID + "' is occupied, checking next ID...");
            }
            dynamicItemID++;
        }
        if (appDebugMode)
        {
            System.err.println("Alchemy++: Item ID set to " + dynamicItemID);
        }
        usedItemIDs.put(dynamicItemID, true);
        return dynamicItemID;
    }

    public static void setupCreativeTab()
    {
        // Set the creative tab
        appCreativeTab = new CreativeTab(CreativeTabs.getNextID(), "Alchemy++");
    }
}
