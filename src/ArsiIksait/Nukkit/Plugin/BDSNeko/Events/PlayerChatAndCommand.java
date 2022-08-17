package ArsiIksait.Nukkit.Plugin.BDSNeko.Events;

import ArsiIksait.Nukkit.Plugin.BDSNeko.Data;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;

import static ArsiIksait.Nukkit.Plugin.BDSNeko.NetPacks.NetTool.sendMessage;

public class PlayerChatAndCommand implements Listener {
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event)
    {
        if ((Boolean) Data.Config.get("SubscribeEvent.PlayerChatAndCommand"))
        {
            sendMessage(event.getPlayer().getName(), event.getMessage());
        }
    }
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event)
    {
        if ((Boolean) Data.Config.get("SubscribeEvent.PlayerChatAndCommand"))
        {
            sendMessage(event.getPlayer().getName() + " " + event.getPlayer().getPosition(), "执行了命令: " + event.getMessage());
        }
    }
}
