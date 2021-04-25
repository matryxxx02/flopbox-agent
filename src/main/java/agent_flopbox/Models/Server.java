package agent_flopbox.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Server {

    private String alias;
    private String url;

    public String getAlias() {
        return alias;
    }

    public String getUrl() {
        return url;
    }
}
