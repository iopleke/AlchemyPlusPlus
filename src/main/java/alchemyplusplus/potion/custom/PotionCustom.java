package alchemyplusplus.potion.custom;

import alchemyplusplus.AlchemyPlusPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public abstract class PotionCustom extends Potion
{

    public static PotionCustom zombiePotion = new PotionZombification(112).setIcon(0, 0).setName("Zombie");
    public static PotionCustom infectedPotion = new PotionInfection(113).setIcon(1, 0).setName("Infected");
    private static final ResourceLocation texture = new ResourceLocation(AlchemyPlusPlus.ID, "textures/icons/potion.png");
    private boolean instant;
    private boolean bad;

    public PotionCustom(int id, boolean bad, int colour)
    {
        super(id, bad, colour);
    }

    public PotionCustom setIcon(int a, int b)
    {
        this.setIconIndex(a, b);
        return this;
    }

    public abstract PotionEffect getEffect();

    @Override
    public int getStatusIconIndex()
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
        return super.getStatusIconIndex();
    }

    public PotionCustom setBadEffect(boolean bad)
    {
        this.bad = bad;
        return this;
    }

    public PotionCustom setInstant(boolean instant)
    {
        this.instant = instant;
        return this;
    }

    public PotionCustom setName(String name)
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
