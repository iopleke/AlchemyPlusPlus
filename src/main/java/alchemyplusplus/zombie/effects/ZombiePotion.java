package alchemyplusplus.zombie.effects;

import alchemyplusplus.zombie.ZombieEventHandler;
import java.util.LinkedList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ZombiePotion extends CustomPotion
{

    public ZombiePotion(int id)
    {
        super(id, true, 0);
    }

    @Override
    public PotionEffect getEffect()
    {
        return new ZombieEffect();
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier)
    {
        ZombieEventHandler.zombify.add(entityLivingBase);
    }

    public class ZombieEffect extends PotionEffect
    {

        public ZombieEffect()
        {
            super(CustomPotion.zombiePotion.id, 1, 0, false);
            this.setCurativeItems(new LinkedList<ItemStack>());
        }

        @Override
        public void combine(PotionEffect effect)
        {
            this.setPotionDurationMax(true);
        }

        @Override
        public boolean onUpdate(EntityLivingBase entity)
        {
            return true;
        }

        @Override
        public boolean getIsPotionDurationMax()
        {
            return true;
        }
    }
//    @Override
//    public boolean shouldRenderInvText(PotionEffect effect) {
//        return false;
//    }
}
