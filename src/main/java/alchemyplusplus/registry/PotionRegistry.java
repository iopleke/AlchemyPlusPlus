package alchemyplusplus.registry;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.handler.FluidHandler;
import alchemyplusplus.item.PotionBucket;
import alchemyplusplus.potion.fluid.PotionFluid;
import alchemyplusplus.potion.fluid.PotionFluidBlock;
import alchemyplusplus.potion.fluid.PotionFluidBlockItem;
import alchemyplusplus.reference.Settings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class PotionRegistry
{

    public static final Material materialPotion = new MaterialLiquid(MapColor.waterColor);

    public static void init()
    {
        for (Potion potion : Potion.potionTypes)
        {
            if (potion != null)
            {
                PotionFluid potionFluid = new PotionFluid(potion);
                FluidRegistry.registerFluid((Fluid) potionFluid);
                PotionFluidBlock block = new PotionFluidBlock((Fluid) potionFluid, materialPotion, potion);

                //ItemRegistry.addCreativePotionBucket(block, potion);
                PotionBucket potionBucket = new PotionBucket(block, potion);
                potionBucket.setCreativeTab(CreativeTabRegistry.APP_TAB);
                potionBucket.setUnlocalizedName(potion.getName());
                GameRegistry.registerItem(potionBucket, potion.getName());

                FluidContainerRegistry.registerFluidContainer((Fluid) potionFluid, new ItemStack(new PotionBucket(block, potion)), new ItemStack(Items.bucket));

                FluidHandler.INSTANCE.buckets.put(block, potionBucket);

                block.setCreativeTab(CreativeTabRegistry.APP_TAB);

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
