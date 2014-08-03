package alchemyplusplus.network;

import alchemyplusplus.items.ItemRegistry;
import alchemyplusplus.renderer.PotionBottleRenderer;
import alchemyplusplus.tileentities.diffuser.TileEntityDiffuserRenderer;
import alchemyplusplus.tileentities.distillery.TileEntityDistilleryRenderer;
import alchemyplusplus.tileentities.extractor.TileEntityExtractorRenderer;
import alchemyplusplus.tileentities.mixer.TileEntityLiquidMixerRenderer;
import alchemyplusplus.tileentities.potioncontainer.TileEntityPotionContainerRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.potioncontainer.TileEntityPotionContainer.class, new TileEntityPotionContainerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.extractor.TileEntityExtractor.class, new TileEntityExtractorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.distillery.TileEntityDistillery.class, new TileEntityDistilleryRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.diffuser.TileEntityDiffuser.class, new TileEntityDiffuserRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.mixer.TileEntityLiquidMixer.class, new TileEntityLiquidMixerRenderer());

        MinecraftForgeClient.registerItemRenderer(ItemRegistry.appItemPotionBottle.itemID, new PotionBottleRenderer());
    }
}
