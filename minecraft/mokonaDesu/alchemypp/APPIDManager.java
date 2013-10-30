package mokonaDesu.alchemypp;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class APPIDManager {

	private static int currentBlockID = 1234;
	private static int currentItemID = 12367;
	private static int currentBlockIDHerbs = 1334;
	private static int currentItemIDHerbs = 12467;
	
	public static void setup(Configuration config) {

		 Property blockID = config.get(config.CATEGORY_GENERAL, "firstBlockID", "notFound");
		 if (blockID.getString().equals("notFound")) {
			blockID.set(currentBlockID);
			
			System.err.println("Alchemy++ could not load starting block ID" +
					" from the config file! Default value of "
					+ currentBlockID + " is set!");
		 } else currentBlockID = blockID.getInt();
			blockID.comment = "Starting block ID for all the blocks from Alchemy++ core." +
					" (make sure to leave at least 10 free block ID's after first one" +
					" for possible future updates)";
		 
		 Property itemID = config.get(config.CATEGORY_GENERAL, "firstItemID", "notFound");
		 if (itemID.getString().equals("notFound")) {
			 itemID.set(currentItemID);
			 
				System.err.println("Alchemy++ could not load starting item ID" +
						" from the config file! Default value of "
						+ currentItemID + " is set!");
				
		 } else currentItemID = itemID.getInt();
			itemID.comment = "Starting item ID for all the items from Alchemy++ core." +
					" (make sure to leave at least 30 free item ID's after first one" +
					" for possible future updates)";
		
	}
	
	public static void setupHerbs(Configuration config) {
		
		Property herbsBlockID = config.get(config.CATEGORY_GENERAL, "firstHerbsBlockID", "notFound");
		 if (herbsBlockID.getString().equals("notFound")) {
			 herbsBlockID.set(currentBlockIDHerbs);
				
				System.err.println("Alchemy++ could not load starting block ID for herbs module" +
						" from the config file! Default value of "
						+ currentBlockIDHerbs + " is set!");
			 } else currentBlockIDHerbs = herbsBlockID.getInt();
			herbsBlockID.comment = "Starting block ID for all the blocks from Alchemy++ herbs module." +
					" (make sure to leave at least 10 free block ID's after first one" +
					" for possible future updates)";
		
		 Property herbsItemID = config.get(config.CATEGORY_GENERAL, "firstHerbsItemID", "notFound");
		 
		 if (herbsItemID.getString().equals("notFound")) {
			 herbsItemID.set(currentItemIDHerbs);
				
				System.err.println("Alchemy++ could not load starting item ID for herbs module" +
						" from the config file! Default value of "
						+ currentItemIDHerbs + " is set!");
			 } else currentItemIDHerbs = herbsItemID.getInt();
			herbsItemID.comment = "Starting item ID for all the blocks from Alchemy++ herbs module." +
					" (make sure to leave at least 10 free block ID's after first one" +
					" for possible future updates)";
		 
	}
	
	public static int nextBlockID() {
		currentBlockID++;
		return currentBlockID - 1;
	}
	
	public static int nextItemID() {
		currentItemID++;
		return currentItemID - 1;
	}
	
	public static int nextBlockIDHerbs() {
		currentBlockIDHerbs++;
		return currentBlockIDHerbs - 1;
	}
	
	public static int nextItemIDHerbs() {
		currentItemIDHerbs++;
		return currentItemIDHerbs - 1;
	}
	
	
	
}
