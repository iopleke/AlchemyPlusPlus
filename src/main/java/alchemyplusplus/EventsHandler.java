package alchemyplusplus;

import alchemyplusplus.item.PotionBucket;
import alchemyplusplus.potion.PotionFluidBlock;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class EventsHandler
{
	public static EventsHandler INSTANCE = new EventsHandler();
	public Map<PotionFluidBlock, PotionBucket> buckets = new HashMap<PotionFluidBlock, PotionBucket>();

	private EventsHandler()
	{
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event)
	{

		ItemStack result = fillCustomBucket(event.world, event.target);

		if (result == null)
		{
			return;
		}

		event.result = result;
		event.setResult(Result.ALLOW);
	}

	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos)
	{

		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

		if (block instanceof PotionFluidBlock)
		{
			PotionBucket bucket = buckets.get((PotionFluidBlock) block);
			if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0)
			{
				world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
				return new ItemStack(bucket);
			}
		}
		return null;

	}
}
