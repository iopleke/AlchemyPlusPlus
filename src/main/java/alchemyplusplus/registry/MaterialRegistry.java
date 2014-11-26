package alchemyplusplus.registry;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialRegistry extends Material
{

    public static final Material flesh = (new Material(MapColor.adobeColor));

    public MaterialRegistry(MapColor color)
    {
        super(color);
    }

}
