package alchemyplusplus;

import alchemyplusplus.network.CommonProxy;
import alchemyplusplus.network.PacketHandler;
import alchemyplusplus.blocks.BlockRegistry;
import alchemyplusplus.client.APPClientPacketHandler;
import alchemyplusplus.gui.APPGuiHandler;
import alchemyplusplus.items.ItemRegistry;
import alchemyplusplus.tileentities.TileEntityAlchemicalApparatus;
import alchemyplusplus.tileentities.TileEntityDiffuser;
import alchemyplusplus.tileentities.TileEntityDistillery;
import alchemyplusplus.tileentities.TileEntityExtractor;
import alchemyplusplus.tileentities.TileEntityLiquidMixer;
import alchemyplusplus.tileentities.TileEntityPotionContainer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "AlchemyPlusPlus", name = "AlchemyPlusPlus", version = "release 1.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "Alchemy++" }, packetHandler = APPClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "Alchemy++" }, packetHandler = PacketHandler.class))
public class AlchemyPlusPlus {

    @Instance("AlchemyPlusPlus")
    public static AlchemyPlusPlus instance = new AlchemyPlusPlus();
    public static APPGuiHandler guiHandler = new APPGuiHandler();

    @SidedProxy(clientSide = "alchemyplusplus.client.ClientProxy", serverSide = "alchemyplusplus.CommonProxy")
    public static CommonProxy proxy;

    /*
     * Herbs addon loaded
     */
    public static boolean herbs = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        APPConfigManager.initialSetup(event.getSuggestedConfigurationFile());

    }

    @EventHandler
    public void load(FMLInitializationEvent event) {

        proxy.registerRenderers();
        GameRegistry.registerTileEntity(TileEntityPotionContainer.class, "potionKegTE");
        GameRegistry.registerTileEntity(TileEntityLiquidMixer.class, "LiquidMixerTE");
        GameRegistry.registerTileEntity(TileEntityExtractor.class, "ExtractorTE");
        GameRegistry.registerTileEntity(TileEntityDistillery.class, "DistilleryTE");
        GameRegistry.registerTileEntity(TileEntityDiffuser.class, "DiffuserTE");
        GameRegistry.registerTileEntity(TileEntityAlchemicalApparatus.class, "AlchemicalApparatusTE");

        BlockRegistry.registerBlocks();
        ItemRegistry.registerItems();

        BlockRegistry.registerBlockRecipes();
        ItemRegistry.registerItemRecipes();

        // Book.loadAll();

        if (APPConfigManager.appHardcoreModeEnabled) {
            ItemRegistry.registerHardcoreRecipes();
        }
        NetworkRegistry.instance().registerGuiHandler(AlchemyPlusPlus.instance, this.guiHandler);

        APPEvents.registerEventHooks();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

}
