package alchemyplusplus.proxy.client;

import alchemyplusplus.block.complex.diffuser.DiffuserTileEntity;
import alchemyplusplus.block.complex.diffuser.DiffuserTileEntityRenderer;
import alchemyplusplus.block.complex.distillery.DistilleryBlockRender;
import alchemyplusplus.block.complex.distillery.DistilleryTileEntity;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerBlockRender;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntity;
import alchemyplusplus.block.complex.potionJug.PotionJugBlockRender;
import alchemyplusplus.block.complex.potionJug.PotionJugTileEntity;
import alchemyplusplus.helper.PotionFluidHelper;
import alchemyplusplus.proxy.CommonProxy;
import alchemyplusplus.registry.BlockRegistry;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import jakimbox.prefab.render.BasicItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerHooks()
    {
        MinecraftForge.EVENT_BUS.register(new PotionFluidHelper());
    }

    @Override
    public void registerRenderers()
    {
        RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

        ClientRegistry.bindTileEntitySpecialRenderer(PotionJugTileEntity.class, new PotionJugBlockRender());
        ClientRegistry.bindTileEntitySpecialRenderer(DistilleryTileEntity.class, new DistilleryBlockRender());
        ClientRegistry.bindTileEntitySpecialRenderer(FluidMixerTileEntity.class, new FluidMixerBlockRender());
        DiffuserTileEntityRenderer diffuserRenderer = new DiffuserTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(DiffuserTileEntity.class, diffuserRenderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.diffuser), new BasicItemRenderer(diffuserRenderer, new DiffuserTileEntity()));
    }

    @Override
    public EntityPlayer getPlayer(MessageContext context)
    {
        return Minecraft.getMinecraft().thePlayer;
    }
}
