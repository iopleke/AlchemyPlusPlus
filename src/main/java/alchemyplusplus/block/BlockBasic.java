package alchemyplusplus.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockBasic extends Block
{

	String icon;

	public BlockBasic(Material material, String icon)
	{
		super(material);

		this.icon = icon;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(icon);
	}

}
