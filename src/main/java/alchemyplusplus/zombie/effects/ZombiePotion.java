package alchemyplusplus.zombie.effects;

import alchemyplusplus.reference.Settings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.LinkedList;

public class ZombiePotion extends CustomPotion {
    public ZombiePotion(int id) {
        super(id, true, 0);
    }

    @Override
    public PotionEffect getEffect() {
        return new ZombieEffect();
    }


    public class ZombieEffect extends PotionEffect
    {
        public ZombieEffect() {
            super(CustomPotion.zombiePotion.id, 0, 0, false);
            this.setCurativeItems(new LinkedList<ItemStack>());
        }

        @Override
        public void combine(PotionEffect effect)
        {
        }

        @Override
        public boolean onUpdate(EntityLivingBase entity)
        {
            return true;
        }

        @Override
        public boolean getIsPotionDurationMax() {
            return true;
        }
    }
//    @Override
//    public boolean shouldRenderInvText(PotionEffect effect) {
//        return false;
//    }
}
