package alchemyplusplus.helper;

import alchemyplusplus.potion.custom.PotionCustom;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public class ZombieHelper
{

    public static boolean isPlayerZombie(EntityPlayer player)
    {
        return player.getActivePotionEffect(PotionCustom.zombiePotion) != null;
    }

    public static EntityPlayer getNearestZombie(EntityCreature creature, double radius, boolean visible)
    {
        EntityPlayer result = null;
        double distanceSq = radius * (radius + 1);
        for (Object player : creature.worldObj.getEntitiesWithinAABB(EntityPlayer.class, creature.boundingBox.expand(radius, 3.0D, radius)))
        {
            EntityPlayer thePlayer = (EntityPlayer) player;
            if (thePlayer.capabilities.isCreativeMode || thePlayer.isDead)
            {
                continue;
            }
            if (visible && !creature.getEntitySenses().canSee(thePlayer))
            {
                continue;
            }
            if (isPlayerZombie(thePlayer))
            {
                double newDistanceSq = creature.getDistanceSqToEntity(thePlayer);
                if (newDistanceSq < distanceSq)
                {
                    distanceSq = newDistanceSq;
                    result = thePlayer;
                }
            }
        }

        return result;
    }
}
