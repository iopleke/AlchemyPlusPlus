package alchemyplusplus.potion.custom;

import alchemyplusplus.Config;
import alchemyplusplus.handler.ZombieHandler;
import java.util.LinkedList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class PotionInfection extends PotionCustom
{

    public PotionInfection(int id)
    {
        super(id, true, 0);
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return duration == 0;
    }

    @Override
    public PotionEffect getEffect()
    {
        return new InfectedEffect();
    }

    public class InfectedEffect extends PotionEffect
    {

        public InfectedEffect()
        {
            super(PotionCustom.infectedPotion.id, Config.infectionTimer, 0, false);
            this.setCurativeItems(new LinkedList<ItemStack>());
        }

        @Override
        public void combine(PotionEffect effect)
        {
        }

        public boolean onUpdate(EntityLivingBase entity)
        {
            boolean notFinished = true;
            if (this.getDuration() > 0)
            {
                notFinished = super.onUpdate(entity);
            }
            if (!notFinished)
            {
                performEffect(entity);
            }
            return notFinished;
        }

        @Override
        public void performEffect(EntityLivingBase entityLivingBase)
        {
            ZombieHandler.zombify.add(entityLivingBase);
        }
    }
}
