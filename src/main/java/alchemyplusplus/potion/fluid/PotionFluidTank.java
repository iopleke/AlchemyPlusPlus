package alchemyplusplus.potion.fluid;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fluids.FluidTank;

public class PotionFluidTank extends FluidTank
{

    public List<PotionEffect> potionEffects;

    public PotionFluidTank(PotionFluidStack stack, int capacity)
    {
        super(stack, capacity);

        this.potionEffects = stack.potionEffects;
    }

    public PotionFluidTank(int capacity)
    {
        super(null, capacity);
        this.potionEffects = new ArrayList<PotionEffect>();
    }

}
