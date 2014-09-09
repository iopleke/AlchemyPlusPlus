package alchemyplusplus.proxy.client;

import alchemyplusplus.items.ItemRegistry;
import alchemyplusplus.proxy.CommonProxy;
import alchemyplusplus.renderer.PotionBottleRenderer;
import alchemyplusplus.proxy.client.render.block.RenderDiffuserBlock;
import alchemyplusplus.proxy.client.render.block.RenderDistilleryBlock;
import alchemyplusplus.proxy.client.render.block.RenderLiquidMixerBlock;
import alchemyplusplus.proxy.client.render.block.RenderPotionJugBlock;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.potionjug.TileEntityPotionJug.class, new RenderPotionJugBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.distillery.TileEntityDistillery.class, new RenderDistilleryBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.diffuser.TileEntityDiffuser.class, new RenderDiffuserBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(alchemyplusplus.tileentities.mixer.TileEntityLiquidMixer.class, new RenderLiquidMixerBlock());
    }
}
