package alchemyplusplus.handler;

import alchemyplusplus.item.PotionBucket;
import alchemyplusplus.potion.fluid.PotionFluidBlock;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class FluidHandler
{

    public static FluidHandler INSTANCE = new FluidHandler();
    public Map<PotionFluidBlock, PotionBucket> buckets = new HashMap<PotionFluidBlock, PotionBucket>();

    private FluidHandler()
    {
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event)
    {
        Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);

        if (block != null && block instanceof PotionFluidBlock)
        {
            PotionBucket bucket = buckets.get((PotionFluidBlock) block);
            if (bucket != null && event.world.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ) == 0)
            {
                event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
                event.result = new ItemStack(bucket);
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

}
