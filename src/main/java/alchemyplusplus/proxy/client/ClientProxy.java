package alchemyplusplus.proxy.client;

import alchemyplusplus.block.BlockRegistry;
import alchemyplusplus.proxy.CommonProxy;
import alchemyplusplus.proxy.client.render.block.RenderDiffuserBlock;
import alchemyplusplus.proxy.client.render.block.RenderDistilleryBlock;
import alchemyplusplus.proxy.client.render.block.RenderLiquidMixerBlock;
import alchemyplusplus.proxy.client.render.block.RenderPotionJugBlock;
import alchemyplusplus.proxy.client.render.item.RenderDiffuserItem;
import alchemyplusplus.proxy.client.render.item.RenderDistilleryItem;
import alchemyplusplus.proxy.client.render.item.RenderLiquidMixerItem;
import alchemyplusplus.proxy.client.render.item.RenderPotionJugItem;
import alchemyplusplus.tileentities.diffuser.TileEntityDiffuser;
import alchemyplusplus.tileentities.distillery.TileEntityDistillery;
import alchemyplusplus.tileentities.mixer.TileEntityLiquidMixer;
import alchemyplusplus.tileentities.potionjug.TileEntityPotionJug;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRenderers()
    {
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.diffuser), new RenderDiffuserItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.distillery), new RenderDistilleryItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.liquidMixer), new RenderLiquidMixerItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.potionJug), new RenderPotionJugItem());
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPotionJug.class, new RenderPotionJugBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDistillery.class, new RenderDistilleryBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDiffuser.class, new RenderDiffuserBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLiquidMixer.class, new RenderLiquidMixerBlock());
    }
}
