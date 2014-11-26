package alchemyplusplus.potion.custom;

import alchemyplusplus.handler.ZombieHandler;
import java.util.LinkedList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class PotionZombification extends PotionCustom
{

    public PotionZombification(int id)
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
        ZombieHandler.zombify.add(entityLivingBase);
    }

    public class ZombieEffect extends PotionEffect
    {

        public ZombieEffect()
        {
            super(PotionCustom.zombiePotion.id, 1, 0, false);
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
