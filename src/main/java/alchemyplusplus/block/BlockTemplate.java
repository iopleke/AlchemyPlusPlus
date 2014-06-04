package alchemyplusplus.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockTemplate extends Block
{

    String icon;

    public BlockTemplate(int id, Material material, String icon)
    {
        super(id, material);

        this.icon = icon;
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(icon);
    }

}