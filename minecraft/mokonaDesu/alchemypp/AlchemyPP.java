package mokonaDesu.alchemypp;

import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPotion;
import net.minecraftforge.common.Configuration;
import mokonaDesu.alchemypp.blocks.BlockRegistry;
import mokonaDesu.alchemypp.client.APPClientPacketHandler;
import mokonaDesu.alchemypp.gui.APPGuiHandler;
import mokonaDesu.alchemypp.gui.AlchemyPPCreativeTab;
import mokonaDesu.alchemypp.gui.Book;
import mokonaDesu.alchemypp.gui.AlchemyPPCreativeTab;
import mokonaDesu.alchemypp.items.ItemRegistry;
import mokonaDesu.alchemypp.tileentities.TileEntityAlchemicalApparatus;
import mokonaDesu.alchemypp.tileentities.TileEntityExtractor;
import mokonaDesu.alchemypp.tileentities.TileEntityLiquidMixer;
import mokonaDesu.alchemypp.tileentities.TileEntityPotionContainer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "AlchemyPlusPlus", name = "AlchemyPlusPlus", version = "release 1.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "Alchemy++" }, packetHandler = APPClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "Alchemy++" }, packetHandler = APPServerPacketHandler.class))
public class AlchemyPP {

    @Instance("AlchemyPlusPlus")
    public static AlchemyPP instance = new AlchemyPP();
    public static APPGuiHandler guiHandler = new APPGuiHandler();
    public static APPWorldGenerator worldGen = new APPWorldGenerator();

    @SidedProxy(clientSide = "mokonaDesu.alchemypp.client.ClientProxy", serverSide = "mokonaDesu.alchemypp.CommonProxy")
    public static CommonProxy proxy;

    /*
     * Is base potion class overrided with APP potion class to provide extra
     * functionality (WIP)
     */
    public static boolean potionOverride = false;

    /*
     * Herbs addon loaded
     */
    public static boolean herbs = false;

    /*
     * Hardcore alchemy recipes
     */
    public static boolean hardcoreAlchemy = false;

    /*
     * Orichalcum generation enabled
     */
    public static boolean generateOrichalcum = true;

    /*
     * Alternative orichalcum texture usage
     */
    public static boolean alternativeTextures = false;

    /*
     * Adding the creative tab
     */
    public static CreativeTabs alchemyPPTab;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        APPConfigurator.loadConfig(event);
        alchemyPPTab = new AlchemyPPCreativeTab(CreativeTabs.getNextID(),
                "Alchemy++");

    }

    @EventHandler
    public void load(FMLInitializationEvent event) {

        proxy.registerRenderers();
        GameRegistry.registerTileEntity(TileEntityPotionContainer.class,
                "potionKegTE");
        GameRegistry.registerTileEntity(TileEntityLiquidMixer.class,
                "LiquidMixerTE");
        GameRegistry.registerTileEntity(TileEntityExtractor.class,
                "ExtractorTE");
        GameRegistry.registerTileEntity(TileEntityAlchemicalApparatus.class,
                "AlchemicalApparatusTE");

        BlockRegistry.registerBlocks();
        ItemRegistry.registerItems();

        BlockRegistry.registerBlockRecipes();
        ItemRegistry.registerItemRecipes();

        // Book.loadAll();

        if (hardcoreAlchemy)
            ItemRegistry.registerHardcoreRecipes();

        NetworkRegistry.instance().registerGuiHandler(AlchemyPP.instance,
                this.guiHandler);
        GameRegistry.registerWorldGenerator(worldGen);

        APPEvents.registerEventHooks();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

}
