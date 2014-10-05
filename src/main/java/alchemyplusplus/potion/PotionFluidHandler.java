package alchemyplusplus.potion;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.gui.CreativeTab;
import alchemyplusplus.reference.Settings;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.Map;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class PotionFluidHandler
{
	public static Map<String, Fluid> registry;

	public static void registerPotionFluids()
	{
		if (Settings.DebugMode)
		{
			AlchemyPlusPlus.LOGGER.info("Registering potions as fluids");
		}
		for (Potion pot : Potion.potionTypes)
		{
			if (pot != null)
			{
				Fluid potionFluid = new PotionFluid(pot);
				FluidRegistry.registerFluid(potionFluid);

				PotionFluidBlock block = new PotionFluidBlock(potionFluid, Material.water, pot);
				block.setCreativeTab(CreativeTab.APP_TAB);

				GameRegistry.registerBlock(block, PotionFluidBlockItem.class, potionFluid.getUnlocalizedName());
				if (Settings.DebugMode)
				{
					AlchemyPlusPlus.LOGGER.info("Potion ID: " + pot.getId());
					AlchemyPlusPlus.LOGGER.info("Potion Name: " + pot.getName());
					AlchemyPlusPlus.LOGGER.info("Potion Color: " + pot.getLiquidColor());
					AlchemyPlusPlus.LOGGER.info("Potion Name: " + pot.getEffectiveness());
				}
			}

		}
		if (Settings.DebugMode)
		{
			AlchemyPlusPlus.LOGGER.info("Potion fluid registration done");
		}
	}

}
