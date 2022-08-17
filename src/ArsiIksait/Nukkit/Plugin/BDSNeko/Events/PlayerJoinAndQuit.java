package ArsiIksait.Nukkit.Plugin.BDSNeko.Events;

import ArsiIksait.Nukkit.Plugin.BDSNeko.Data;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.event.player.PlayerQuitEvent;

import static ArsiIksait.Nukkit.Plugin.BDSNeko.NetPacks.NetTool.sendMessage;

public class PlayerJoinAndQuit implements Listener {
    boolean kick;
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Data.OnlinePlayer++;
        if ((Boolean) Data.Config.get("SubscribeEvent.PlayerJoinAndQuit"))
        {
            sendMessage("BDSNeko","玩家 " + event.getPlayer().getName() + "加入了游戏喵！~ (" + Data.OnlinePlayer + "/" + Data.MaxPlayer + ")");
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if (Data.OnlinePlayer > 0)
            Data.OnlinePlayer--;

        if ((Boolean) Data.Config.get("SubscribeEvent.PlayerJoinAndQuit"))
        {
            if (!kick)
            {
                sendMessage("BDSNeko","玩家 " + event.getPlayer().getName() + "退出了游戏喵！~ (" + Data.OnlinePlayer + "/" + Data.MaxPlayer + ")");
            }
            else
            {
                kick = false;
            }
        }
    }
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
        if (Data.OnlinePlayer > 0)
            Data.OnlinePlayer--;

        if ((Boolean) Data.Config.get("SubscribeEvent.PlayerJoinAndQuit"))
        {
            sendMessage("BDSNeko","玩家 " + event.getPlayer().getName() + "被踢出了游戏喵！~ 原因: " + event.getReason() + " (" + Data.OnlinePlayer + "/" + Data.MaxPlayer + ")");
            kick = true;
        }
    }
}