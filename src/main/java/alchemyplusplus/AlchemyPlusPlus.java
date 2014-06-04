package alchemyplusplus;

import alchemyplusplus.utility.EventManager;
import alchemyplusplus.utility.ConfigManager;
import alchemyplusplus.network.CommonProxy;
import alchemyplusplus.network.PacketHandler;
import alchemyplusplus.utility.BlockRegistry;
import alchemyplusplus.network.ClientPacketHandler;
import alchemyplusplus.utility.GUIHandler;
import alchemyplusplus.utility.ItemRegistry;
import alchemyplusplus.tileentities.apparatus.TileEntityAlchemicalApparatus;
import alchemyplusplus.tileentities.diffuser.TileEntityDiffuser;
import alchemyplusplus.tileentities.distillery.TileEntityDistillery;
import alchemyplusplus.tileentities.extractor.TileEntityExtractor;
import alchemyplusplus.tileentities.mixer.TileEntityLiquidMixer;
import alchemyplusplus.tileentities.potioncontainer.TileEntityPotionContainer;
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
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels =
{
    "Alchemy++"
}, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels =
{
    "Alchemy++"
}, packetHandler = PacketHandler.class))
public class AlchemyPlusPlus
{

    public static GUIHandler guiHandler = new GUIHandler();


    /*
     * Herbs addon loaded
     */
    public static boolean herbs = false;
    @Instance(value = "AlchemyPlusPlus")
    public static AlchemyPlusPlus instance = new AlchemyPlusPlus();
    @SidedProxy(clientSide = "alchemyplusplus.client.ClientProxy", serverSide = "alchemyplusplus.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void load(FMLInitializationEvent event)
    {

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
        if (ConfigManager.appHardcoreModeEnabled)
        {
            ItemRegistry.registerHardcoreRecipes();
        }
        NetworkRegistry.instance().registerGuiHandler(AlchemyPlusPlus.instance, this.guiHandler);

        EventManager.registerEventHooks();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        ConfigManager.initialSetup(event.getSuggestedConfigurationFile());

    }

}
