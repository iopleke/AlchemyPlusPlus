package mokonaDesu.alchemypp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class APPBlock extends Block {
	
String icon;
	
public APPBlock(int id, Material material, String icon) {
		super(id, material);
		
		this.icon = icon;
	}

@Override
public void registerIcons(IconRegister iconRegister) {
	this.blockIcon = iconRegister.registerIcon(icon);
}

}
