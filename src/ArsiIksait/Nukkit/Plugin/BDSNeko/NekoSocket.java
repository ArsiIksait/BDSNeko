package ArsiIksait.Nukkit.Plugin.BDSNeko;

import ArsiIksait.Nukkit.Plugin.BDSNeko.NetPacks.Message;
import ArsiIksait.Nukkit.Plugin.BDSNeko.NetPacks.NetTool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static ArsiIksait.Nukkit.Plugin.BDSNeko.NetPacks.NetTool.sendHeartbeat;

public class NekoSocket extends Thread {
    Socket socket;
    OutputStream outputStream;
    @Override
    public void run()
    {
        String ipAddress;
        int port;
        try
        {
            ipAddress = Data.IPAddressAndPort.split(":")[0];
            port = Integer.parseInt(Data.IPAddressAndPort.split(":")[1]);
        } catch (Exception ex) {
            Data.Logger.error("在启动Socket服务器时出现错误! 无法转换IP地址，请检查配置文件中是否正确填写！示例: 127.0.0.1:8000");
            throw ex;
        }

        while (true)
        {
            try (var socket = new Socket(ipAddress, port))
            {
                Data.Logger.info("已连接到Socket服务器: " + socket.getRemoteSocketAddress());
                this.socket = socket;
                // 建立连接后获得输出流
                outputStream = socket.getOutputStream();
                NetTool.sendMessage("BDSNeko","服务器上线了喵！");

                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[1024];
                int len;
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                while ((len = inputStream.read(bytes)) != -1) {
                    var msg = new String(bytes, 0, len, StandardCharsets.UTF_8);
                    messageReceived(msg);
                }

                inputStream.close();
                outputStream.close();
            } catch (Exception ex) {
                Data.Logger.error("Socket服务器出现错误: \r\n" + ex);
            }
            Data.Logger.info("连接已断开，10s后尝试重连");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Data.Logger.error("Socket服务器线程中断,已退出!");
                break;
            }
        }
    }
    public void sendMessage(String message)
    {
        try
        {
            if (outputStream != null)
            {
                outputStream.write((message.getBytes(StandardCharsets.UTF_8)));
                outputStream.flush();
            }
        } catch (IOException ex) {
            Data.Logger.error("发送消息时出错: " + ex);
        }
        //Data.Logger.info("发送消息: " + message);
    }
    void messageReceived(String jsonText)
    {
        try {
            //Data.Logger.info("收到JSON消息: " + jsonText);
            JSONObject jsonObject = JSON.parseObject(jsonText);

            switch ((String) jsonObject.get("Name")) {
                case "Heartbeat" -> sendHeartbeat();
                case "Message" -> {
                    Message message = JSON.parseObject(jsonText, Message.class);

                    if (messageCheck(message.Content)) {
                        Data.Logger.info("收到消息: " + message.Content);
                        String msg = "[QQ群推送] " + "[" + message.SenderName + "(" + message.SenderQQ + ")]: " + message.Content;
                        Data.Server.broadcastMessage(msg);
                    }
                }
            }
        } catch (Exception ex) {
            Data.Logger.alert("JSON消息序列化失败: " + ex);
        }
    }
    boolean messageCheck(String message)
    {
        if (message.toLowerCase().indexOf("jndi") == 0)
        {
            NetTool.sendMessage("SECheck","警告！您的消息中含有危险关键字JNDI，可能会引发CVE-2021-44228漏洞，已取消推送！");
            return false;
        }
        if (message.toLowerCase().indexOf("rmi") == 0)
        {
            NetTool.sendMessage("SECheck","警告！您的消息中含有危险关键字RMI，可能会引发CVE-2017-3241漏洞，已取消推送！");
            return false;
        }
        if (message.toLowerCase().indexOf("evilclass") == 0)
        {
            NetTool.sendMessage("SECheck","警告！您的消息中含有危险关键字EVILCLASS，可能会引发CVE-2017-18349漏洞，已取消推送！");
            return false;
        }
        if (message.toLowerCase().indexOf("ldap") == 0)
        {
            NetTool.sendMessage("SECheck","警告！您的消息中含有危险关键字LDAP，可能会引发CNVD‐2019‐22238漏洞，已取消推送！");
            return false;
        }
        return true;
    }
}