package alchemyplusplus.utility;

import alchemyplusplus.AlchemyPlusPlus;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationHelper
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
