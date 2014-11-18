package alchemyplusplus.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class ZombieHelper {

    public static boolean isPlayerZombie(EntityPlayer player)
    {
        return true;
    }

    public static EntityPlayer getNearestZombie(EntityCreature creature, int radius)
    {
        EntityPlayer result = null;
        double distanceSq = radius*(radius+1);
        AxisAlignedBB axisAlignedBB = AxisAlignedBB.getBoundingBox(creature.posX - radius, creature.posY - radius, creature.posZ - radius, creature.posX + radius, creature.posY + radius, creature.posZ + radius);
        for (Object player:creature.worldObj.getEntitiesWithinAABB(EntityPlayer.class,axisAlignedBB))
        {
            EntityPlayer thePlayer = (EntityPlayer)player;
            if (thePlayer.capabilities.isCreativeMode) continue;
            if (thePlayer.isDead) continue;
            if (isPlayerZombie(thePlayer))
            {
                double newDistanceSq = creature.getDistanceSqToEntity(thePlayer);
                if (newDistanceSq<distanceSq)
                {
                    distanceSq = newDistanceSq;
                    result = thePlayer;
                }
            }
        }

        return result;
    }
}
