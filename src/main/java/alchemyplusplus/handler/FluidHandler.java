package alchemyplusplus.handler;

import alchemyplusplus.item.PotionBucket;
import alchemyplusplus.potion.fluid.PotionFluidBlock;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Mouse;

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

    // patterning this off Enviromine https://github.com/Funwayguy/EnviroMine/blob/14add02db4b0ab1cfa8f33ea17949656c86804fc/src/main/java/enviromine/handlers/EM_EventManager.java#L271
    @SubscribeEvent
    public void onGlassBottleFill(PlayerInteractEvent event)
    {
        Mouse.setGrabbed(false);
        ItemStack equippedItem = event.entityPlayer.getCurrentEquippedItem();
        if (equippedItem != null)
        {
            if (event.getResult() != Result.DENY)
            {
                if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR)
                {
                    if (equippedItem.getItem() instanceof ItemGlassBottle)
                    {
                        int offsetX = 0;
                        int offsetY = 0;
                        int offsetZ = 0;
                        switch (event.face)
                        {
                            case 0:
                            {
                                offsetY = -1;
                                break;
                            }
                            case 1:
                            {
                                offsetY = 1;
                                break;
                            }
                            case 2:
                            {
                                offsetZ = -1;
                                break;
                            }
                            case 3:
                            {
                                offsetZ = 1;
                                break;
                            }
                            case 4:
                            {
                                offsetX = -1;
                                break;
                            }
                            case 5:
                            {
                                offsetX = 1;
                                break;
                            }
                        }
                        Block potentialFluidBlock = event.world.getBlock(event.x + offsetX, event.y + offsetY, event.z + offsetZ);
                        if (potentialFluidBlock instanceof PotionFluidBlock)
                        {
                            PotionFluidBlock potionFluidBlock = (PotionFluidBlock) potentialFluidBlock;
                            PotionEffect effect = new PotionEffect(potionFluidBlock.getPotionID(), 40, 0);
                            event.setCanceled(true);
                        }

                    }
                }
            }
        }

    }
}
