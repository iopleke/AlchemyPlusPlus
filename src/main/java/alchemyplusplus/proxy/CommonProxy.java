package alchemyplusplus.proxy;

import alchemyplusplus.block.complex.diffuser.DiffuserTileEntity;
import alchemyplusplus.block.complex.distillery.DistilleryTileEntity;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntity;
import alchemyplusplus.block.complex.potionJug.PotionJugTileEntity;
import cpw.mods.fml.common.registry.GameRegistry;
import jakimbox.proxy.CommonProxyBase;

public class CommonProxy extends CommonProxyBase
{
    public void registerTileEntities()
    {
        // @TODO - add tileEntity names to Resources reference class per https://github.com/jakimfett/AlchemyPlusPlus/issues/6
        GameRegistry.registerTileEntity(DiffuserTileEntity.class, "diffuserTE");
        GameRegistry.registerTileEntity(DistilleryTileEntity.class, "distilleryTE");
        GameRegistry.registerTileEntity(FluidMixerTileEntity.class, "liquidMixerTE");
        GameRegistry.registerTileEntity(PotionJugTileEntity.class, "potionJugTE");
    }
}
