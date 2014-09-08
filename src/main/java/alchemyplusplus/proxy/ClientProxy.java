package alchemyplusplus.proxy;

import alchemyplusplus.items.ItemRegistry;
import alchemyplusplus.renderer.PotionBottleRenderer;
import alchemyplusplus.tileentities.diffuser.TileEntityDiffuserRenderer;
import alchemyplusplus.tileentities.distillery.TileEntityDistilleryRenderer;
import alchemyplusplus.tileentities.mixer.TileEntityLiquidMixerRenderer;
import alchemyplusplus.tileentities.potionjug.TileEntityPotionContainerRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.potionjug.TileEntityPotionContainer.class, new TileEntityPotionContainerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.distillery.TileEntityDistillery.class, new TileEntityDistilleryRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.diffuser.TileEntityDiffuser.class, new TileEntityDiffuserRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.mixer.TileEntityLiquidMixer.class, new TileEntityLiquidMixerRenderer());
    }
}
