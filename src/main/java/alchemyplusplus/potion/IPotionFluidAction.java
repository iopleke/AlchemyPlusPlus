package alchemyplusplus.potion;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Random;

public interface IPotionFluidAction {
	public void onTouch(World world, int x, int y, int z, Entity entity);

	public void onTick(World world, int x, int y, int z, Random rand);
}
