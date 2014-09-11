package alchemyplusplus.proxy;

import alchemyplusplus.block.complex.diffuser.DiffuserTileEntity;
import alchemyplusplus.block.complex.distillery.DistilleryTileEntity;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntity;
import alchemyplusplus.block.complex.potionJug.PotionJugTileEntity;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommonProxy
{

    public static int RENDER_ID;
    public static String displayName;

    public void registerRenderers()
    {

    }

    public void registerTileEntitys()
    {
        // @TODO - add tileEntity names to Resources reference class per https://github.com/jakimfett/AlchemyPlusPlus/issues/6
        GameRegistry.registerTileEntity(DiffuserTileEntity.class, "diffuserTE");
        GameRegistry.registerTileEntity(DistilleryTileEntity.class, "distilleryTE");
        GameRegistry.registerTileEntity(FluidMixerTileEntity.class, "liquidMixerTE");
        GameRegistry.registerTileEntity(PotionJugTileEntity.class, "potionJugTE");
    }

    public World getClientWorld()
    {
        return null;
    }

    public void registerHooks()
    {
    }

    public EntityPlayer findEntityPlayerByName(String name)
    {

        EntityPlayer player;
        player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);

        if (player != null)
        {
            return player;
        }

        return null;
    }

    public String getCurrentLanguage()
    {
        return null;
    }

    public void addName(Object obj, String s)
    {
    }

    public void addLocalization(String s1, String string)
    {
    }

    public String getItemDisplayName(ItemStack newStack)
    {
        return this.displayName;
    }

    public EntityPlayer getPlayer(MessageContext context)
    {
        return context.getServerHandler().playerEntity;
    }
}
