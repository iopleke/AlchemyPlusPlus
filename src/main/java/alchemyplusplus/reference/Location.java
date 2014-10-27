package alchemyplusplus.reference;

import alchemyplusplus.AlchemyPlusPlus;
import net.minecraft.util.ResourceLocation;

public class Location
{
	public static ResourceLocation getResourceLocation(String modId, String path)
	{
		return new ResourceLocation(modId, path);
	}

	public static ResourceLocation getResourceLocation(String path)
	{
		return getResourceLocation(AlchemyPlusPlus.ID.toLowerCase(), path);
	}
}
