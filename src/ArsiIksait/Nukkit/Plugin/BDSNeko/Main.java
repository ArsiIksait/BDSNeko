package ArsiIksait.Nukkit.Plugin.BDSNeko;

import ArsiIksait.Nukkit.Plugin.BDSNeko.Events.PlayerChatAndCommand;
import ArsiIksait.Nukkit.Plugin.BDSNeko.Events.PlayerJoinAndQuit;
import cn.nukkit.plugin.PluginBase;

import static ArsiIksait.Nukkit.Plugin.BDSNeko.NetPacks.NetTool.sendMessage;

public class Main extends PluginBase {
    @Override
    public void onEnable() {
        getDataFolder().mkdir();
        saveDefaultConfig();
        Data.Config = getConfig();
        Data.Logger = getLogger();
        Data.Server = getServer();
        Data.MaxPlayer = Data.Server.getMaxPlayers();
        Data.IPAddressAndPort = (String) getConfig().get("SocketAddress");
        getServer().getPluginManager().registerEvents(new PlayerJoinAndQuit(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatAndCommand(), this);
        Data.Logger.info("欢迎使用BDSNeko！这是一个能让您在QQ群中管理基岩版服务器的插件喵~");
        Data.Logger.info("正在连接到Socket服务器...");
        Data.NekoSocket = new NekoSocket();
        Data.NekoSocket.start();
    }

    @Override
    public void onDisable() {
        getLogger().info("再见喵！");
        if (Boolean.parseBoolean(String.valueOf(getConfig().get("SubscribeEvent.ServerOnlineAndStop")).replace(",", "")))
        {
            sendMessage("BDSNeko","服务器已离线！");
        }
    }
}