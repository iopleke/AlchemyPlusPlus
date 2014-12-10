package alchemyplusplus.handler;

import alchemyplusplus.item.PotionBucket;
import alchemyplusplus.potion.fluid.PotionFluidBlock;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class FluidHandler
{

    public static FluidHandler INSTANCE = new FluidHandler();
    public Map<PotionFluidBlock, PotionBucket> buckets = new HashMap<PotionFluidBlock, PotionBucket>();
    public Map<String, Integer> potionNameRef = new HashMap<String, Integer>();

    private FluidHandler()
    {
        potionNameRef.put("Mundane", 0);
        potionNameRef.put("Water Bottle", 0);
        potionNameRef.put("Regeneration", 1);
        potionNameRef.put("Swiftness", 2);
        potionNameRef.put("Fire Resistance", 3);
        potionNameRef.put("Poison", 4);
        potionNameRef.put("Healing", 5);
        potionNameRef.put("Night Vision", 6);
        potionNameRef.put("Clear", 7);
        potionNameRef.put("Weakness", 8);
        potionNameRef.put("Strength", 9);
        potionNameRef.put("Slowness", 10);
        potionNameRef.put("Leaping", 11);
        potionNameRef.put("Harming", 12);
        potionNameRef.put("Water Breathing", 13);
        potionNameRef.put("Invisibility", 14);
        potionNameRef.put("Thin", 15);
        potionNameRef.put("Awkward", 16);
        potionNameRef.put("Regeneration", 17);
        potionNameRef.put("Swiftness", 18);
        potionNameRef.put("Fire Resistance", 19);
        potionNameRef.put("Poison", 20);
        potionNameRef.put("Healing", 21);
        potionNameRef.put("Night Vision", 22);
        potionNameRef.put("Bungling", 23);
        potionNameRef.put("Weakness", 24);
        potionNameRef.put("Strength", 25);
        potionNameRef.put("Slowness", 26);
        potionNameRef.put("Leaping", 27);
        potionNameRef.put("Harming", 28);
        potionNameRef.put("Water Breathing", 29);
        potionNameRef.put("Invisibility", 30);
        potionNameRef.put("Debonair", 31);
        potionNameRef.put("Thick", 32);
        potionNameRef.put("Regeneration", 33);
        potionNameRef.put("Swiftness", 34);
        potionNameRef.put("Fire Resistance", 35);
        potionNameRef.put("Poison", 36);
        potionNameRef.put("Healing", 37);
        potionNameRef.put("Night Vision", 38);
        potionNameRef.put("Charming", 39);
        potionNameRef.put("Weakness", 40);
        potionNameRef.put("Strength", 41);
        potionNameRef.put("Slowness", 42);
        potionNameRef.put("Leaping", 43);
        potionNameRef.put("Harming", 44);
        potionNameRef.put("Water Breathing", 45);
        potionNameRef.put("Invisibility", 46);
        potionNameRef.put("Sparkling", 47);
        potionNameRef.put("Potent", 48);
        potionNameRef.put("Regeneration", 49);
        potionNameRef.put("Swiftness", 50);
        potionNameRef.put("Fire Resistance", 51);
        potionNameRef.put("Poison", 52);
        potionNameRef.put("Healing", 53);
        potionNameRef.put("Night Vision", 54);
        potionNameRef.put("Rank", 55);
        potionNameRef.put("Weakness", 56);
        potionNameRef.put("Strength", 57);
        potionNameRef.put("Slowness", 58);
        potionNameRef.put("Leaping", 59);
        potionNameRef.put("Harming", 60);
        potionNameRef.put("Water Breathing", 61);
        potionNameRef.put("Invisibility", 62);
        potionNameRef.put("Stinky", 63);

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
        ItemStack equippedItem = event.entityPlayer.getCurrentEquippedItem();
        if (equippedItem != null)
        {
            if (event.getResult() != Result.DENY)
            {
                if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR)
                {
                    if (equippedItem.getItem() instanceof ItemGlassBottle)
                    {
                        ForgeDirection dir = ForgeDirection.getOrientation(event.face);
                        int offsetX = dir.offsetX;
                        int offsetY = dir.offsetY;
                        int offsetZ = dir.offsetZ;
                        Block potentialFluidBlock = event.world.getBlock(event.x + offsetX, event.y + offsetY, event.z + offsetZ);
                        if (potentialFluidBlock instanceof PotionFluidBlock)
                        {
                            PotionFluidBlock potionFluidBlock = (PotionFluidBlock) potentialFluidBlock;
                            Potion fluidPotion = potionFluidBlock.getFluidStackPotion();
                            if (fluidPotion != null)
                            {
                                if (this.potionNameRef.containsKey(StatCollector.translateToLocal(fluidPotion.getName())))
                                {
                                    int potionDamage = this.potionNameRef.get(StatCollector.translateToLocal(fluidPotion.getName()));
                                    potionDamage = potionDamage + fluidPotion.id;
                                    ItemStack potionStack = new ItemStack(Items.potionitem, 1, potionDamage);
                                }
                            }

                            event.setCanceled(true);
                        }

                    }
                }
            }
        }

    }
}
