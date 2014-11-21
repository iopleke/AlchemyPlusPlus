package alchemyplusplus.zombie.effects;

import alchemyplusplus.AlchemyPlusPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public abstract class CustomPotion extends Potion
{
    public static CustomPotion zombiePotion = new ZombiePotion(112).setIcon(0,1).setName("Zombie");
    public static CustomPotion infectedPotion =new InfectedPotion(113).setIcon(0,2).setName("Infected");
    private static final ResourceLocation texture = new ResourceLocation(AlchemyPlusPlus.ID,"textures/icons/potion.png");
    private boolean instant;
    private boolean bad;

    public CustomPotion(int id, boolean bad, int colour) {
        super(id, bad, colour);
    }

    public CustomPotion setIcon(int a, int b)
    {
        this.setIconIndex(a,b);
        return this;
    }

    public abstract PotionEffect getEffect();

    @Override
    public int getStatusIconIndex()
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
        return super.getStatusIconIndex();
    }

    public CustomPotion setBadEffect(boolean bad)
    {
        this.bad = bad;
        return this;
    }

    public CustomPotion setInstant(boolean instant)
    {
        this.instant = instant;
        return this;
    }

    public CustomPotion setName(String name)
    {
        this.setPotionName(name);
        return this;
    }

    @Override
    public boolean isInstant()
    {
        return this.instant;
    }

    @Override
    public boolean isBadEffect()
    {
        return this.bad;
    }
}