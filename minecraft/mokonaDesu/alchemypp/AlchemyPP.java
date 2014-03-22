package mokonaDesu.alchemypp;

import mokonaDesu.alchemypp.blocks.BlockRegistry;
import mokonaDesu.alchemypp.client.APPClientPacketHandler;
import mokonaDesu.alchemypp.gui.APPGuiHandler;
import mokonaDesu.alchemypp.items.ItemRegistry;
import mokonaDesu.alchemypp.tileentities.TileEntityAlchemicalApparatus;
import mokonaDesu.alchemypp.tileentities.TileEntityDistillery;
import mokonaDesu.alchemypp.tileentities.TileEntityExtractor;
import mokonaDesu.alchemypp.tileentities.TileEntityLiquidMixer;
import mokonaDesu.alchemypp.tileentities.TileEntityPotionContainer;
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
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "Alchemy++" }, packetHandler = APPClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "Alchemy++" }, packetHandler = APPServerPacketHandler.class))
public class AlchemyPP {

    @Instance("AlchemyPlusPlus")
    public static AlchemyPP instance = new AlchemyPP();
    public static APPGuiHandler guiHandler = new APPGuiHandler();

    @SidedProxy(clientSide = "mokonaDesu.alchemypp.client.ClientProxy", serverSide = "mokonaDesu.alchemypp.CommonProxy")
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
        GameRegistry.registerTileEntity(TileEntityAlchemicalApparatus.class, "AlchemicalApparatusTE");

        BlockRegistry.registerBlocks();
        ItemRegistry.registerItems();

        BlockRegistry.registerBlockRecipes();
        ItemRegistry.registerItemRecipes();

        // Book.loadAll();

        if (APPConfigManager.appHardcoreModeEnabled) {
            ItemRegistry.registerHardcoreRecipes();
        }
        NetworkRegistry.instance().registerGuiHandler(AlchemyPP.instance, this.guiHandler);

        APPEvents.registerEventHooks();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

}
