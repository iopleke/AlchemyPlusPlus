package alchemyplusplus.gui;

import alchemyplusplus.block.complex.diffuser.DiffuserContainer;
import alchemyplusplus.block.complex.diffuser.DiffuserTileEntity;
import alchemyplusplus.block.complex.distillery.DistilleryContainer;
import alchemyplusplus.block.complex.distillery.DistilleryGUI;
import alchemyplusplus.block.complex.distillery.DistilleryTileEntity;
import alchemyplusplus.block.complex.extractor.ExtractorContainer;
import alchemyplusplus.block.complex.extractor.ExtractorTileEntity;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerContainer;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntity;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.HashMap;
import net.minecraft.tileentity.TileEntity;

public class GUIHandler implements IGuiHandler
{

    public static HashMap<String, Integer> guiHandlerRegistry = new HashMap<String, Integer>();

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        System.out.println("Got to the client GUI element method");
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof DistilleryTileEntity)
        {
            return new DistilleryGUI(player.inventory, (DistilleryTileEntity) tileEntity);
        }
        return null;
//        switch (ID)
//        {
//            case 0:
//            { // Potion mixer
//                FluidMixerTileEntity entity = (FluidMixerTileEntity) world.getTileEntity(x, y, z);
//                return new FluidMixerGUI(player.inventory, entity);
//            }
//            case 1:
//            { // Extractor
//                ExtractorTileEntity entity = (ExtractorTileEntity) world.getTileEntity(x, y, z);
//                return new ExtractorGUI(player.inventory, entity);
//            }
//            case 2:
//            { // Distillery
//                DistilleryTileEntity entity = (DistilleryTileEntity) world.getTileEntity(x, y, z);
//                return new DistilleryGUI(player.inventory, entity);
//            }
//            default:
//                return null;
//        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        System.out.println("Got to the server GUI element method");
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(tileEntity instanceof DistilleryTileEntity){
            return new DistilleryGUI(player.inventory, (DistilleryTileEntity) tileEntity);
        }
        return null;

//        switch (ID)
//        {
//            case 0:
//            { // Potion mixer
//                FluidMixerTileEntity entity = (FluidMixerTileEntity) world.getTileEntity(x, y, z);
//                return new FluidMixerContainer(player.inventory, entity);
//            }
//            case 1:
//            { // Extractor
//                ExtractorTileEntity entity = (ExtractorTileEntity) world.getTileEntity(x, y, z);
//                return new ExtractorContainer(player.inventory, entity);
//            }
//            case 2:
//            { // Distillery
//                DistilleryTileEntity entity = (DistilleryTileEntity) world.getTileEntity(x, y, z);
//                return new DistilleryContainer(player.inventory, entity);
//            }
//            case 3:
//            { // Diffuser
//                DiffuserTileEntity entity = (DiffuserTileEntity) world.getTileEntity(x, y, z);
//                return new DiffuserContainer(player.inventory, entity);
//            }
//
//            default:
//                return null;
//
//        }
    }

}
