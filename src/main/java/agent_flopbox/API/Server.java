package agent_flopbox.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Server {
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("url")
    @Expose
    private String url;

    public Server(String alias, String url) {
        this.alias = alias;
        this.url = url;
    }

    public String getAlias(){
        return  this.alias;
    }

    public String getUrl(){
        return this.url;
    }
}
