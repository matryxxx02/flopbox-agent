package agent_flopbox.api;

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
}
