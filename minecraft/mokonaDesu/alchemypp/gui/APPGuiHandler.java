package mokonaDesu.alchemypp.gui;

import java.util.HashMap;

import mokonaDesu.alchemypp.tileentities.ContainerDiffuser;
import mokonaDesu.alchemypp.tileentities.ContainerDistillery;
import mokonaDesu.alchemypp.tileentities.ContainerExtractor;
import mokonaDesu.alchemypp.tileentities.ContainerLiquidMixer;
import mokonaDesu.alchemypp.tileentities.TileEntityDiffuser;
import mokonaDesu.alchemypp.tileentities.TileEntityDistillery;
import mokonaDesu.alchemypp.tileentities.TileEntityExtractor;
import mokonaDesu.alchemypp.tileentities.TileEntityLiquidMixer;
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
