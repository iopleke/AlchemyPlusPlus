package alchemyplusplus.gui;

import alchemyplusplus.tileentities.diffuser.ContainerDiffuser;
import alchemyplusplus.tileentities.diffuser.TileEntityDiffuser;
import alchemyplusplus.tileentities.distillery.ContainerDistillery;
import alchemyplusplus.tileentities.distillery.GuiDistillery;
import alchemyplusplus.tileentities.distillery.TileEntityDistillery;
import alchemyplusplus.tileentities.extractor.ContainerExtractor;
import alchemyplusplus.tileentities.extractor.GuiExtractor;
import alchemyplusplus.tileentities.extractor.TileEntityExtractor;
import alchemyplusplus.tileentities.mixer.ContainerLiquidMixer;
import alchemyplusplus.tileentities.mixer.GuiLiquidMixer;
import alchemyplusplus.tileentities.mixer.TileEntityLiquidMixer;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.HashMap;

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
                TileEntityLiquidMixer entity = (TileEntityLiquidMixer) world.getTileEntity(x, y, z);
                return new GuiLiquidMixer(player.inventory, entity);
            }
            case 1:
            { // Extractor
                TileEntityExtractor entity = (TileEntityExtractor) world.getTileEntity(x, y, z);
                return new GuiExtractor(player.inventory, entity);
            }
            case 2:
            { // Distillery
                TileEntityDistillery entity = (TileEntityDistillery) world.getTileEntity(x, y, z);
                return new GuiDistillery(player.inventory, entity);
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
                TileEntityLiquidMixer entity = (TileEntityLiquidMixer) world.getTileEntity(x, y, z);
                return new ContainerLiquidMixer(player.inventory, entity);
            }
            case 1:
            { // Extractor
                TileEntityExtractor entity = (TileEntityExtractor) world.getTileEntity(x, y, z);
                return new ContainerExtractor(player.inventory, entity);
            }
            case 2:
            { // Distillery
                TileEntityDistillery entity = (TileEntityDistillery) world.getTileEntity(x, y, z);
                return new ContainerDistillery(player.inventory, entity);
            }
            case 3:
            { // Diffuser
                TileEntityDiffuser entity = (TileEntityDiffuser) world.getTileEntity(x, y, z);
                return new ContainerDiffuser(player.inventory, entity);
            }

            default:
                return null;

        }
    }

}
