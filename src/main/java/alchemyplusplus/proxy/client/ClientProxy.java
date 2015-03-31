package alchemyplusplus.proxy.client;

import alchemyplusplus.block.complex.diffuser.DiffuserTileEntity;
import alchemyplusplus.block.complex.diffuser.DiffuserTileEntityRenderer;
import alchemyplusplus.block.complex.distillery.DistilleryTileEntity;
import alchemyplusplus.block.complex.distillery.DistilleryTileEntityRenderer;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntityRenderer;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntity;
import alchemyplusplus.block.complex.potionJug.PotionJugTileEntity;
import alchemyplusplus.block.complex.potionJug.PotionJugTileEntityRenderer;
import alchemyplusplus.helper.PotionFluidHelper;
import alchemyplusplus.registry.BlockRegistry;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import jakimbox.prefab.render.BasicItemRenderer;
import jakimbox.proxy.CommonProxyBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxyBase
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

        PotionJugTileEntityRenderer potionJugRenderer = new PotionJugTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(PotionJugTileEntity.class, potionJugRenderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.potionJug), new BasicItemRenderer(potionJugRenderer, new PotionJugTileEntity()));

        DistilleryTileEntityRenderer distilleryRenderer = new DistilleryTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(DistilleryTileEntity.class, distilleryRenderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.distillery), new BasicItemRenderer(distilleryRenderer, new DistilleryTileEntity()));

        ClientRegistry.bindTileEntitySpecialRenderer(FluidMixerTileEntity.class, new FluidMixerTileEntityRenderer());

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
