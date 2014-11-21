package alchemyplusplus.zombie;

import alchemyplusplus.reference.Settings;
import alchemyplusplus.zombie.entity.ai.EntityAIAttackZombiePlayer;
import alchemyplusplus.zombie.entity.ai.EntityAIAvoidZombiePlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ZombieEventHandler {
    public static ZombieEventHandler INSTANCE = new ZombieEventHandler();
    @SubscribeEvent
    public void onEntitySpawn(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityAnimal)
        {
            if (Settings.hostileAnimals)((EntityAnimal)event.entity).tasks.addTask(0,new EntityAIAttackZombiePlayer((EntityAnimal)event.entity,1.4F,15D,false));
        }
        if (event.entity instanceof EntityVillager)
        {
            ((EntityVillager)event.entity).tasks.addTask(1,new EntityAIAvoidZombiePlayer((EntityVillager)event.entity,15F,0.9D,1.1D));
        }
    }
}
