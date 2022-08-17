package ArsiIksait.Nukkit.Plugin.BDSNeko.NetPacks;

import ArsiIksait.Nukkit.Plugin.BDSNeko.Data;
import com.alibaba.fastjson.JSON;

import java.util.Random;

public class NetTool {
    static void serializerAndSend(Object classObject)
    {
        String jsonMessage = JSON.toJSONString(classObject);
        Data.NekoSocket.sendMessage(jsonMessage);
    }
    public static void sendHeartbeat()
    {
        Random random = new Random();
        int id = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        Heartbeat heartbeat = new Heartbeat(id);
        serializerAndSend(heartbeat);
    }
    public static void sendMessage(String senderName,String content)
    {
        Message message = new Message(senderName,0,content);
        serializerAndSend(message);
    }
}
