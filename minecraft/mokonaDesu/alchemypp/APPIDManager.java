package mokonaDesu.alchemypp;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class APPIDManager {
    private static String[] alchemyPPBlocks = { "distillery", "potionContainer", "diffuser", "liquidMixer", "flesh", "extractor" };

    private static int dynamicBlockID = 400;
    private static int blockIDCounter = 0;
    private static int[] usedBlockIDs = new int[alchemyPPBlocks.length];

    private static int dynamicItemID = 2300;
    private static int itemIDCounter = 0;
    // @TODO - set the length of usedItemIDs dynamically
    private static int[] usedItemIDs = new int[30];

    public static void setup(Configuration config) {
        Arrays.sort(alchemyPPBlocks);

        for (String blockName : alchemyPPBlocks) {
            // Attempt to get the default block ID from the config file
            Property blockID = config.get(config.CATEGORY_BLOCK, blockName, 0);

            if (blockID.getInt() == 0) {
                System.err.println("Alchey++ did not find an ID for '" + blockName + "' in the config file!");

                blockID.set(nextBlockID());
            }
        }

    }

    public static int nextBlockID() {
        boolean blockIDInUse = checkIfBlockIDIsUsed(dynamicBlockID);
        while (Block.blocksList[dynamicBlockID] != null || blockIDInUse) {
            blockIDInUse = checkIfBlockIDIsUsed(dynamicBlockID);
            System.err.println("Block ID '" + dynamicBlockID + "' is occupied, checking next ID...");
            dynamicBlockID++;
        }
        System.err.println("Block ID set to " + dynamicBlockID);
        usedBlockIDs[blockIDCounter] = dynamicBlockID;
        blockIDCounter++;
        return dynamicBlockID;
    }

    private static boolean checkIfBlockIDIsUsed(int blockIDToCheck) {
        for (int singleUsedBlockID : usedBlockIDs) {
            if (singleUsedBlockID == blockIDToCheck) {
                return true;
            }
        }
        return false;
    }

    public static int nextItemID() {
        boolean itemIDInUse = checkIfItemIDIsUsed(dynamicItemID);
        while (Item.itemsList[dynamicItemID] != null || itemIDInUse) {
            itemIDInUse = checkIfItemIDIsUsed(dynamicItemID);
            System.err.println("Item ID '" + dynamicItemID + "' is occupied, checking next ID...");
            dynamicItemID++;
        }
        System.err.println("Item ID set to " + dynamicItemID);
        usedItemIDs[itemIDCounter] = dynamicItemID;
        itemIDCounter++;
        return dynamicItemID;
    }

    private static boolean checkIfItemIDIsUsed(int itemIDToCheck) {
        for (int singleUsedItemID : usedItemIDs) {
            if (singleUsedItemID == itemIDToCheck) {
                return true;
            }
        }
        return false;
    }
}
