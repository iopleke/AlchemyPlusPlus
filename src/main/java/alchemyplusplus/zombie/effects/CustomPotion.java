package alchemyplusplus.zombie.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class CustomPotion extends Potion
{
    public static CustomPotion zombiePotion;
    public static CustomPotion infectedPotion;
    private ResourceLocation texture;
    private boolean instant;
    private boolean bad;

    public CustomPotion(int id, boolean bad, int colour) {
        super(id, bad, colour);
    }

    public CustomPotion setIcon(ResourceLocation location, int a, int b)
    {
        this.texture = location;
        this.setIconIndex(a,b);
        return this;
    }

    @Override
    public int getStatusIconIndex()
    {
        if (this.texture != null)
        {
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
        }
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