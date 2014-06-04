package alchemyplusplus.client;

import alchemyplusplus.network.CommonProxy;
import alchemyplusplus.items.ItemRegistry;
import alchemyplusplus.renderer.ItemPotionBottleRenderer;
import alchemyplusplus.renderer.TileEntityAlchemicalApparatusRenderer;
import alchemyplusplus.renderer.TileEntityDiffuserRenderer;
import alchemyplusplus.renderer.TileEntityDistilleryRenderer;
import alchemyplusplus.renderer.TileEntityExtractorRenderer;
import alchemyplusplus.renderer.TileEntityLiquidMixerRenderer;
import alchemyplusplus.renderer.TileEntityPotionContainerRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.TileEntityPotionContainer.class, new TileEntityPotionContainerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.TileEntityExtractor.class, new TileEntityExtractorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.TileEntityDistillery.class, new TileEntityDistilleryRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.TileEntityDiffuser.class, new TileEntityDiffuserRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.TileEntityLiquidMixer.class, new TileEntityLiquidMixerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.TileEntityAlchemicalApparatus.class, new TileEntityAlchemicalApparatusRenderer());

        MinecraftForgeClient.registerItemRenderer(ItemRegistry.appItemPotionBottle.itemID, new ItemPotionBottleRenderer());
    }
}
