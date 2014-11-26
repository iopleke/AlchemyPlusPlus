package alchemyplusplus.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class NotificationHelper
{

    public static void sendPlayerChatMessage(EntityPlayer player, String message)
    {
        player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(message)));
    }
}
