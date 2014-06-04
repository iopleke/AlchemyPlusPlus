package alchemyplusplus.gui;

import java.util.HashMap;

import alchemyplusplus.tileentities.ContainerDiffuser;
import alchemyplusplus.tileentities.ContainerDistillery;
import alchemyplusplus.tileentities.ContainerExtractor;
import alchemyplusplus.tileentities.ContainerLiquidMixer;
import alchemyplusplus.tileentities.TileEntityDiffuser;
import alchemyplusplus.tileentities.TileEntityDistillery;
import alchemyplusplus.tileentities.TileEntityExtractor;
import alchemyplusplus.tileentities.TileEntityLiquidMixer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class APPGuiHandler implements IGuiHandler {

    public static HashMap<String, Integer> guiHandlerRegistry = new HashMap<String, Integer>();

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {
        case 0: { // Potion mixer
            TileEntityLiquidMixer entity = (TileEntityLiquidMixer) world.getBlockTileEntity(x, y, z);
            return new ContainerLiquidMixer(player.inventory, entity);
        }
        case 1: { // Extractor
            TileEntityExtractor entity = (TileEntityExtractor) world.getBlockTileEntity(x, y, z);
            return new ContainerExtractor(player.inventory, entity);
        }
        case 2: { // Distillery
            TileEntityDistillery entity = (TileEntityDistillery) world.getBlockTileEntity(x, y, z);
            return new ContainerDistillery(player.inventory, entity);
        }
        case 3: { // Diffuser
            TileEntityDiffuser entity = (TileEntityDiffuser) world.getBlockTileEntity(x, y, z);
            return new ContainerDiffuser(player.inventory, entity);
        }

        default:
            return null;

        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
        case 0: { // Potion mixer
            TileEntityLiquidMixer entity = (TileEntityLiquidMixer) world.getBlockTileEntity(x, y, z);
            return new GuiLiquidMixer(player.inventory, entity);
        }
        case 1: { // Extractor
            TileEntityExtractor entity = (TileEntityExtractor) world.getBlockTileEntity(x, y, z);
            return new GuiExtractor(player.inventory, entity);
        }
        case 2: { // Distillery
            TileEntityDistillery entity = (TileEntityDistillery) world.getBlockTileEntity(x, y, z);
            return new GuiDistillery(player.inventory, entity);
        }
        case 3: { // Book
            return new GuiAlchemicalGuide(player, player.getHeldItem());
        }
        default:
            return null;
        }
    }

}
