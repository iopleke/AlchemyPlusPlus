package alchemyplusplus.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAPPPotion extends EntityPotion {

	public EntityAPPPotion(World world, EntityPlayer player, ItemStack stack) {
		super(world, player, stack);
	}

}
