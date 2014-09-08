package alchemyplusplus.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class NotificationManager
{
    public static void sendChatMessage(EntityPlayer player, String message){
        player.addChatMessage(new ChatComponentText(message));
    }
}
