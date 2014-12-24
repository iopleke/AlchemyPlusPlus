package alchemyplusplus.potion.fluid;

import java.util.List;

import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fluids.FluidStack;

public class PotionFluidStack extends FluidStack
{

    public List<PotionEffect> potionEffects;

    public PotionFluidStack(PotionFluid fluid, int amount)
    {
        super(fluid, amount);
        this.potionEffects = fluid.potionEffects;
    }

}
