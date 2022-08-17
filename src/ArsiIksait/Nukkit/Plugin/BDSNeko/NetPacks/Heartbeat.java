package ArsiIksait.Nukkit.Plugin.BDSNeko.NetPacks;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class Heartbeat implements Serializable {
    @JSONField(name="Name")
    public String Name = "Heartbeat";
    @JSONField(name="Id")
    public int Id;

    public Heartbeat(int id)
    {
        Id = id;
    }
}