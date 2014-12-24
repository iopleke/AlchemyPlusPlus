package alchemyplusplus.handler;

import alchemyplusplus.item.PotionBucket;
import alchemyplusplus.potion.fluid.PotionFluidBlock;
import alchemyplusplus.reference.Settings;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class FluidHandler
{

    public static FluidHandler INSTANCE = new FluidHandler();
    public Map<PotionFluidBlock, PotionBucket> buckets = new HashMap<PotionFluidBlock, PotionBucket>();
    public static int[] potionEffectIds;

    private FluidHandler()
    {
        potionEffectIds = new int[Potion.potionTypes.length];
        potionEffectIds[1] = 2;//SPEED
        potionEffectIds[2] = 10;//SLOWNESS
        potionEffectIds[3] = Potion.digSpeed.getId();//HASTE
        potionEffectIds[4] = Potion.digSlowdown.getId();//SLOWMINING
        potionEffectIds[5] = 9;//STRENGTH
        potionEffectIds[6] = Potion.heal.getId();//HEALTH
        potionEffectIds[7] = 12;//DAMAGE ?= instantDamage
        potionEffectIds[8] = 11;//JUMP
        potionEffectIds[9] = Potion.confusion.getId();//CONFUSION
        potionEffectIds[10] = 1;//REGENERATION
        potionEffectIds[11] = Potion.resistance.getId();//RESISTANCE
        potionEffectIds[12] = 3;//FIRERESISTANCE
        potionEffectIds[13] = 13;//WATERBREATHING
        potionEffectIds[14] = 14;//INVISIBILITY
        potionEffectIds[15] = Potion.blindness.getId();//BLINDNESS
        potionEffectIds[16] = 6;//NIGHTVISION
        potionEffectIds[17] = Potion.hunger.getId();//HUNGER
        potionEffectIds[18] = 8;//WEAKNESS
        potionEffectIds[19] = 4;//POISON
        potionEffectIds[20] = Potion.wither.getId();//WITHER
        potionEffectIds[21] = 5;//HEALTHBOOST =? instantHealth
        potionEffectIds[22] = Potion.field_76444_x.getId();//ABSORPTION
        potionEffectIds[23] = Potion.field_76443_y.getId();//SATURATION
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
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGlassBottleFill(PlayerInteractEvent event)
    {
        ItemStack equippedItem = event.entityPlayer.getCurrentEquippedItem();
        if (equippedItem != null && equippedItem.getItem() instanceof ItemGlassBottle)
        {
            if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR)
            {
                ForgeDirection dir = ForgeDirection.getOrientation(event.face);
                int x = event.x + dir.offsetX;
                int y = event.y + dir.offsetY;
                int z = event.z + dir.offsetZ;
                if (!event.entityPlayer.canPlayerEdit(x, y, z, event.face, equippedItem))
                {
                    return;
                }
                Block potentialFluidBlock = event.world.getBlock(x, y, z);
                if (potentialFluidBlock instanceof PotionFluidBlock)
                {
                    PotionFluidBlock potionFluidBlock = (PotionFluidBlock) potentialFluidBlock;
                    if (potionFluidBlock.isSourceBlock(event.world, x, y, z))
                    {
                        Potion fluidPotion = potionFluidBlock.getFluidStackPotion();
                        if (fluidPotion != null)
                        {
                            int potionTier = 0; //0 for T1, 1 for T2
                            int potionExtended = 0; //1 for true
                            int potionSplash = 1; //1 for drinkable, 2 for splash
                            int potionEffect = potionEffectIds[fluidPotion.getId()];
                            if (potionEffect>0)
                            {
                                int potionDamage = potionEffect + (potionTier << 5) + (potionExtended << 6) + (potionSplash << 13);
                                ItemStack potionStack = new ItemStack(Items.potionitem, 1, potionDamage);
                                if (!event.entityPlayer.capabilities.isCreativeMode)
                                {
                                    event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
                                    if (Settings.consumeSourceBlocks && event.world.rand.nextFloat() < Settings.consumeSourceBlocksChance)
                                        event.world.setBlockToAir(x, y, z);
                                }
                                if (!event.entityPlayer.inventory.addItemStackToInventory(potionStack))
                                {
                                    event.entityPlayer.dropPlayerItemWithRandomChoice(potionStack, false);
                                }
                            }
                        }
                    }
                    if (event.world.isRemote)
                    {
                        return;
                    }
                    event.setCanceled(true);
                }
            }
        }
    }

}
