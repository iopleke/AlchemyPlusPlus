package alchemyplusplus.proxy.client;

import alchemyplusplus.proxy.CommonProxy;
import alchemyplusplus.proxy.client.render.block.RenderDiffuserBlock;
import alchemyplusplus.proxy.client.render.block.RenderDistilleryBlock;
import alchemyplusplus.proxy.client.render.block.RenderLiquidMixerBlock;
import alchemyplusplus.proxy.client.render.block.RenderPotionJugBlock;
import alchemyplusplus.tileentities.diffuser.TileEntityDiffuser;
import alchemyplusplus.tileentities.distillery.TileEntityDistillery;
import alchemyplusplus.tileentities.mixer.TileEntityLiquidMixer;
import alchemyplusplus.tileentities.potionjug.TileEntityPotionJug;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPotionJug.class, new RenderPotionJugBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDistillery.class, new RenderDistilleryBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDiffuser.class, new RenderDiffuserBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLiquidMixer.class, new RenderLiquidMixerBlock());
    }
}
