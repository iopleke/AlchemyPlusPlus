package mokonaDesu.alchemypp.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import mokonaDesu.alchemypp.CommonProxy;
import mokonaDesu.alchemypp.entity.EntityAPPPotion;
import mokonaDesu.alchemypp.items.ItemRegistry;
import mokonaDesu.alchemypp.renderer.EntityAPPPotionRenderer;
import mokonaDesu.alchemypp.renderer.ItemPotionBottleRenderer;
import mokonaDesu.alchemypp.renderer.TileEntityAlchemicalApparatusRenderer;
import mokonaDesu.alchemypp.renderer.TileEntityExtractorRenderer;
import mokonaDesu.alchemypp.renderer.TileEntityLiquidMixerRenderer;
import mokonaDesu.alchemypp.renderer.TileEntityPotionContainerRenderer;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers() {
	ClientRegistry.bindTileEntitySpecialRenderer(mokonaDesu.alchemypp.tileentities.TileEntityPotionContainer.class, new TileEntityPotionContainerRenderer());
	ClientRegistry.bindTileEntitySpecialRenderer(mokonaDesu.alchemypp.tileentities.TileEntityExtractor.class, new TileEntityExtractorRenderer());
	ClientRegistry.bindTileEntitySpecialRenderer(mokonaDesu.alchemypp.tileentities.TileEntityLiquidMixer.class, new TileEntityLiquidMixerRenderer());
	ClientRegistry.bindTileEntitySpecialRenderer(mokonaDesu.alchemypp.tileentities.TileEntityAlchemicalApparatus.class,  new TileEntityAlchemicalApparatusRenderer());
	
	MinecraftForgeClient.registerItemRenderer(ItemRegistry.appItemPotionBottle.itemID, new ItemPotionBottleRenderer());
	}
}
