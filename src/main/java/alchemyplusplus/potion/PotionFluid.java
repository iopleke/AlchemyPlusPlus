package alchemyplusplus.potion;

import java.awt.Color;
import net.minecraftforge.fluids.Fluid;

public class PotionFluid extends Fluid
{
	public int fluidColor;

	public PotionFluid(String fluidName)
	{
		super(fluidName);
		this.fluidColor = new Color(0, 0, 0, 0.25F).getRGB();
	}

	public PotionFluid(String fluidName, int fluidColor)
	{
		super(fluidName);
		this.fluidColor = fluidColor;
	}

	@Override
	public int getColor()
	{
		return this.fluidColor;
	}
}
