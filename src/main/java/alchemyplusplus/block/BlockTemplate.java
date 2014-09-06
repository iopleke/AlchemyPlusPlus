package alchemyplusplus.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockTemplate extends Block
{

    String icon;

    public BlockTemplate(int id, Material material, String icon)
    {
        super(material);

        this.icon = icon;
    }

    @Override
    public void registerIIcons(IIconRegister iconRegister)
    {
        this.blockIIcon = iconRegister.registerIIcon(icon);
    }

}
