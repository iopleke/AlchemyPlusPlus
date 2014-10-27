package alchemyplusplus.potion;

import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.fluids.FluidTank;

public class PotionFluidTank extends FluidTank
{
	public final List potionEffects;

	public PotionFluidTank(PotionFluidStack stack, int capacity)
	{
		super(stack, capacity);

		this.potionEffects = stack.potionEffects;
	}

	public PotionFluidTank(int capacity)
	{
		super(null, capacity);
		this.potionEffects = new ArrayList();
	}

}
