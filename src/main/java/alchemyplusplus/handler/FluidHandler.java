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
        potionEffectIds[1] = Potion.moveSpeed.getId();//SPEED
        potionEffectIds[2] = Potion.moveSlowdown.getId();//SLOWNESS
        potionEffectIds[3] = Potion.digSpeed.getId();//HASTE
        potionEffectIds[4] = Potion.digSlowdown.getId();//SLOWMINING
        potionEffectIds[5] = Potion.damageBoost.getId();//TODO:STENGTH
        potionEffectIds[6] = Potion.heal.getId();//HEALTH
        potionEffectIds[7] = Potion.damageBoost.getId();//TODO:DAMAGE
        potionEffectIds[8] = Potion.jump.getId();//JUMP
        potionEffectIds[9] = Potion.confusion.getId();//CONFUSION
        potionEffectIds[10] = Potion.regeneration.getId();//REGENERATION
        potionEffectIds[11] = Potion.resistance.getId();//RESISTANCE
        potionEffectIds[12] = Potion.fireResistance.getId();//FIRERESISTANCE
        potionEffectIds[13] = Potion.waterBreathing.getId();//WATERBREATHING
        potionEffectIds[14] = Potion.invisibility.getId();//INVISIBILITY
        potionEffectIds[15] = Potion.blindness.getId();//BLINDNESS
        potionEffectIds[16] = Potion.nightVision.getId();//NIGHTVISION
        potionEffectIds[17] = Potion.hunger.getId();//HUNGER
        potionEffectIds[18] = Potion.weakness.getId();//WEAKNESS
        potionEffectIds[19] = Potion.poison.getId();//POISON
        potionEffectIds[20] = Potion.wither.getId();//WITHER
        potionEffectIds[21] = Potion.field_76434_w.getId();//HEALTHBOOST
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
