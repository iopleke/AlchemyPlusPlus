package alchemyplusplus.handler;

import alchemyplusplus.block.complex.distillery.DistilleryContainer;
import alchemyplusplus.block.complex.distillery.DistilleryGUI;
import alchemyplusplus.block.complex.distillery.DistilleryTileEntity;
import alchemyplusplus.block.complex.extractor.ExtractorContainer;
import alchemyplusplus.block.complex.extractor.ExtractorGUI;
import alchemyplusplus.block.complex.extractor.ExtractorTileEntity;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerContainer;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerGUI;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntity;
import cpw.mods.fml.common.network.IGuiHandler;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

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
                FluidMixerTileEntity entity = (FluidMixerTileEntity) world.getTileEntity(x, y, z);
                return new FluidMixerGUI(player.inventory, entity);
            }
            case 1:
            { // Extractor
                ExtractorTileEntity entity = (ExtractorTileEntity) world.getTileEntity(x, y, z);
                return new ExtractorGUI(player.inventory, entity);
            }
            case 2:
            { // Distillery
                DistilleryTileEntity entity = (DistilleryTileEntity) world.getTileEntity(x, y, z);
                return new DistilleryGUI(player.inventory, entity);
            }
            default:
                return null;
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        // @TODO - refactor this to use instanceof
        switch (ID)
        {
            case 0:
            { // Fluid mixer
                FluidMixerTileEntity entity = (FluidMixerTileEntity) world.getTileEntity(x, y, z);
                return new FluidMixerContainer(player.inventory, entity);
            }
            case 1:
            { // Extractor
                ExtractorTileEntity entity = (ExtractorTileEntity) world.getTileEntity(x, y, z);
                return new ExtractorContainer(player.inventory, entity);
            }
            case 2:
            { // Distillery
                DistilleryTileEntity entity = (DistilleryTileEntity) world.getTileEntity(x, y, z);
                return new DistilleryContainer(player.inventory, entity);
            }

            default:
                return null;

        }
    }

}
