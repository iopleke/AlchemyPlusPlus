package alchemyplusplus.zombie;

import alchemyplusplus.reference.Settings;
import alchemyplusplus.zombie.effects.CustomPotion;
import alchemyplusplus.zombie.entity.ai.EntityAIAttackZombiePlayer;
import alchemyplusplus.zombie.entity.ai.EntityAIAvoidZombiePlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.util.LinkedHashSet;
import java.util.Set;

public class ZombieEventHandler {
    public static ZombieEventHandler INSTANCE = new ZombieEventHandler();
    public static Set<EntityLivingBase> zombify = new LinkedHashSet<EntityLivingBase>();

    public void register()
    {
        FMLCommonHandler.instance().bus().register(new TickHandler());
        MinecraftForge.EVENT_BUS.register(new SpawnHandler());
    }

    public class SpawnHandler{
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

    public class TickHandler{
        @SubscribeEvent
        public void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (zombify.size()==0) return;
            for (EntityLivingBase entity:zombify)
            {
                entity.addPotionEffect(CustomPotion.zombiePotion.getEffect());
            }
            if (event.player.worldObj.isRemote) return;
            zombify.clear();
        }
    }
}
