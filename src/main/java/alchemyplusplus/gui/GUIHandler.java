package alchemyplusplus.gui;

import alchemyplusplus.gui.AlchemicalGuide;
import alchemyplusplus.tileentities.mixer.GuiLiquidMixer;
import alchemyplusplus.tileentities.extractor.GuiExtractor;
import alchemyplusplus.tileentities.distillery.GuiDistillery;
import java.util.HashMap;

import alchemyplusplus.tileentities.diffuser.ContainerDiffuser;
import alchemyplusplus.tileentities.distillery.ContainerDistillery;
import alchemyplusplus.tileentities.extractor.ContainerExtractor;
import alchemyplusplus.tileentities.mixer.ContainerLiquidMixer;
import alchemyplusplus.tileentities.diffuser.TileEntityDiffuser;
import alchemyplusplus.tileentities.distillery.TileEntityDistillery;
import alchemyplusplus.tileentities.extractor.TileEntityExtractor;
import alchemyplusplus.tileentities.mixer.TileEntityLiquidMixer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{

    public static HashMap<String, Integer> guiHandlerRegistry = new HashMap<String, Integer>();

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case 0:
            { // Potion mixer
                TileEntityLiquidMixer entity = (TileEntityLiquidMixer) world.getBlockTileEntity(x, y, z);
                return new GuiLiquidMixer(player.inventory, entity);
            }
            case 1:
            { // Extractor
                TileEntityExtractor entity = (TileEntityExtractor) world.getBlockTileEntity(x, y, z);
                return new GuiExtractor(player.inventory, entity);
            }
            case 2:
            { // Distillery
                TileEntityDistillery entity = (TileEntityDistillery) world.getBlockTileEntity(x, y, z);
                return new GuiDistillery(player.inventory, entity);
            }
            case 3:
            { // Book
                return new AlchemicalGuide(player, player.getHeldItem());
            }
            default:
                return null;
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        switch (ID)
        {
            case 0:
            { // Potion mixer
                TileEntityLiquidMixer entity = (TileEntityLiquidMixer) world.getBlockTileEntity(x, y, z);
                return new ContainerLiquidMixer(player.inventory, entity);
            }
            case 1:
            { // Extractor
                TileEntityExtractor entity = (TileEntityExtractor) world.getBlockTileEntity(x, y, z);
                return new ContainerExtractor(player.inventory, entity);
            }
            case 2:
            { // Distillery
                TileEntityDistillery entity = (TileEntityDistillery) world.getBlockTileEntity(x, y, z);
                return new ContainerDistillery(player.inventory, entity);
            }
            case 3:
            { // Diffuser
                TileEntityDiffuser entity = (TileEntityDiffuser) world.getBlockTileEntity(x, y, z);
                return new ContainerDiffuser(player.inventory, entity);
            }

            default:
                return null;

        }
    }

}
