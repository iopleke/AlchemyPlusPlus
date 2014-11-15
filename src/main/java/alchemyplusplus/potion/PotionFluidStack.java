package alchemyplusplus.potion;

import java.util.List;
import net.minecraftforge.fluids.FluidStack;

public class PotionFluidStack extends FluidStack
{
    public List potionEffects;

    public PotionFluidStack(PotionFluid fluid, int amount)
    {
        super(fluid, amount);
        this.potionEffects = fluid.potionEffects;
    }

}
