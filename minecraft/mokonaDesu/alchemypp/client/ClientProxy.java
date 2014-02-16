package mokonaDesu.alchemypp.client;

import mokonaDesu.alchemypp.CommonProxy;
import mokonaDesu.alchemypp.items.ItemRegistry;
import mokonaDesu.alchemypp.renderer.ItemPotionBottleRenderer;
import mokonaDesu.alchemypp.renderer.TileEntityAlchemicalApparatusRenderer;
import mokonaDesu.alchemypp.renderer.TileEntityDistilleryRenderer;
import mokonaDesu.alchemypp.renderer.TileEntityExtractorRenderer;
import mokonaDesu.alchemypp.renderer.TileEntityLiquidMixerRenderer;
import mokonaDesu.alchemypp.renderer.TileEntityPotionContainerRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        ClientRegistry
                .bindTileEntitySpecialRenderer(
                        mokonaDesu.alchemypp.tileentities.TileEntityPotionContainer.class,
                        new TileEntityPotionContainerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(
                mokonaDesu.alchemypp.tileentities.TileEntityExtractor.class,
                new TileEntityExtractorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(
                mokonaDesu.alchemypp.tileentities.TileEntityDistillery.class,
                new TileEntityDistilleryRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(
                mokonaDesu.alchemypp.tileentities.TileEntityLiquidMixer.class,
                new TileEntityLiquidMixerRenderer());
        ClientRegistry
                .bindTileEntitySpecialRenderer(
                        mokonaDesu.alchemypp.tileentities.TileEntityAlchemicalApparatus.class,
                        new TileEntityAlchemicalApparatusRenderer());

        MinecraftForgeClient.registerItemRenderer(
                ItemRegistry.appItemPotionBottle.itemID,
                new ItemPotionBottleRenderer());
    }
}
