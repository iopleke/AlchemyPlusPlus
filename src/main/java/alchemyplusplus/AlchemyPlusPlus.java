package alchemyplusplus;

import alchemyplusplus.utility.EventManager;
import alchemyplusplus.utility.ConfigManager;
import alchemyplusplus.block.BlockRegistry;
import alchemyplusplus.gui.GUIHandler;
import alchemyplusplus.items.ItemRegistry;
import alchemyplusplus.proxy.CommonProxy;
import alchemyplusplus.tileentities.diffuser.TileEntityDiffuser;
import alchemyplusplus.tileentities.distillery.TileEntityDistillery;
import alchemyplusplus.tileentities.extractor.TileEntityExtractor;
import alchemyplusplus.tileentities.mixer.TileEntityLiquidMixer;
import alchemyplusplus.tileentities.potionjug.TileEntityPotionContainer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = AlchemyPlusPlus.ID, name = "AlchemyPlusPlus", version = "release 1.1")
public class AlchemyPlusPlus {
    
    // Internal name for referring to the mod
    public static final String ID = "alchemyplusplus";
    // Set channel name for use in NetworkMod
    public static final String CHANNEL_NAME = ID;
    
    // Human readible mod name
    public static final String NAME = "AlchemyPlusPlus";
    
    public static final String V_MAJOR = "@MAJOR@";
    public static final String V_MINOR = "@MINOR@";
    public static final String V_REVIS = "@REVIS@";
    public static final String V_BUILD = "@BUILD@";
    public static final String VERSION_FULL = V_MAJOR + "." + V_MINOR + V_REVIS + "." + V_BUILD;
    
    // Texture basedir
    public static final String textureBase = "minechem:";

    // Instancing
    @Instance(value = CHANNEL_NAME)
    public static AlchemyPlusPlus INSTANCE;

    public static GUIHandler guiHandler = new GUIHandler();


    /*
     * Herbs addon loaded
     */
    public static boolean herbs = false;
    @Instance(value = "AlchemyPlusPlus")
    public static AlchemyPlusPlus instance = new AlchemyPlusPlus();
    @SidedProxy(clientSide = "alchemyplusplus.proxy.ClientProxy", serverSide = "alchemyplusplus.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void load(FMLInitializationEvent event) {

	proxy.registerRenderers();
	GameRegistry.registerTileEntity(TileEntityPotionContainer.class, "potionKegTE");
	GameRegistry.registerTileEntity(TileEntityLiquidMixer.class, "LiquidMixerTE");
	GameRegistry.registerTileEntity(TileEntityExtractor.class, "ExtractorTE");
	GameRegistry.registerTileEntity(TileEntityDistillery.class, "DistilleryTE");
	GameRegistry.registerTileEntity(TileEntityDiffuser.class, "DiffuserTE");

	BlockRegistry.registerBlocks();
	ItemRegistry.registerItems();

	BlockRegistry.registerBlockRecipes();
	ItemRegistry.registerItemRecipes();

	// Book.loadAll();
	if (ConfigManager.appHardcoreModeEnabled) {
	    ItemRegistry.registerHardcoreRecipes();
	}

	EventManager.registerEventHooks();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

	ConfigManager.initialSetup(event.getSuggestedConfigurationFile());

    }

}
