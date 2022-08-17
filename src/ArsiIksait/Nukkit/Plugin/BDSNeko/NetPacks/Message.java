package ArsiIksait.Nukkit.Plugin.BDSNeko.NetPacks;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class Message implements Serializable {
    @JSONField(name="Name")
    public String Name = "Message";
    @JSONField(name="SenderName")
    public String SenderName;
    @JSONField(name="SenderQQ")
    public long SenderQQ;
    @JSONField(name="Content")
    public String Content;

    public Message(String senderName,long senderQQ,String content)
    {
        SenderName = senderName;
        SenderQQ = senderQQ;
        Content = content;
    }
}
