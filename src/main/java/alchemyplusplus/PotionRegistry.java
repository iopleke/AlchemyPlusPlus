package alchemyplusplus;

import alchemyplusplus.gui.CreativeTab;
import alchemyplusplus.item.PotionBucket;
import alchemyplusplus.potion.PotionFluid;
import alchemyplusplus.potion.PotionFluidBlock;
import alchemyplusplus.potion.PotionFluidBlockItem;
import alchemyplusplus.reference.Settings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class PotionRegistry
{
    public static void init()
    {
        for (Potion potion : Potion.potionTypes)
        {
            if (potion != null)
            {
                PotionFluid potionFluid = new PotionFluid(potion);
                FluidRegistry.registerFluid((Fluid) potionFluid);
                PotionFluidBlock block = new PotionFluidBlock((Fluid) potionFluid, Material.water, potion);

                //ItemRegistry.addCreativePotionBucket(block, potion);
                PotionBucket potionBucket = new PotionBucket(block, potion);
                potionBucket.setCreativeTab(CreativeTab.APP_TAB);
                potionBucket.setUnlocalizedName(potion.getName());
                GameRegistry.registerItem(potionBucket, potion.getName());

                FluidContainerRegistry.registerFluidContainer((Fluid) potionFluid, new ItemStack(new PotionBucket(block, potion)), new ItemStack(Items.bucket));

                EventsHandler.INSTANCE.buckets.put(block, potionBucket);

                block.setCreativeTab(CreativeTab.APP_TAB);

                if (Settings.PotionBucketCrafting)
                {

                }

                GameRegistry.registerBlock(block, PotionFluidBlockItem.class, potionFluid.getUnlocalizedName());

                if (Settings.DebugMode)
                {
                    AlchemyPlusPlus.LOGGER.info("Registered fluid for " + potion.getName());
                }
            }
        }
    }

}
