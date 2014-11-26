package alchemyplusplus.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class NotificationHandler
{

    public static void sendPlayerChatMessage(EntityPlayer player, String message)
    {
        player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(message)));
    }
}
