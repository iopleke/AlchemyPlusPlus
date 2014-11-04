package alchemyplusplus;

import alchemyplusplus.gui.CreativeTab;
import alchemyplusplus.potion.PotionFluid;
import alchemyplusplus.potion.PotionFluidBlock;
import alchemyplusplus.potion.PotionFluidBlockItem;
import alchemyplusplus.reference.Settings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class PotionRegistry
{
	public static void init()
	{
		for (Potion pot : Potion.potionTypes)
		{
			if (pot != null)
			{
				Fluid potionFluid = new PotionFluid(pot);
				FluidRegistry.registerFluid(potionFluid);

				if (Settings.DebugMode)
				{
					AlchemyPlusPlus.LOGGER.info("Registered fluid for " + pot.getName());
				}

				PotionFluidBlock block = new PotionFluidBlock(potionFluid, Material.water, pot);
				block.setCreativeTab(CreativeTab.APP_TAB);

				if (Settings.PotionBucketCrafting)
				{
					ItemRegistry.registerPotionBucketRecipe(block, pot);
				}

				GameRegistry.registerBlock(block, PotionFluidBlockItem.class, potionFluid.getUnlocalizedName());
			}
		}
	}

}
