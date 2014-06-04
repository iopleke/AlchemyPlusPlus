package alchemyplusplus.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PotionEntity extends EntityPotion
{

    public PotionEntity(World world, EntityPlayer player, ItemStack stack)
    {
        super(world, player, stack);
    }

}
