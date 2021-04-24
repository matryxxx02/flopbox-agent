package agent_flopbox.Models;

public class Servers {

    String alias;
    String url;

    /**
     * Allows to get server's alias
     * @return server's alias
     */
    public String getAlias(){
        return alias;
    }

    /**
     * Allows to set server's alias
     * @param alias server's alias
     */
    public void setAlias(String alias){
        this.alias = alias;
    }

    /**
     * Allows to get server's url with the port (url:port)
     * @return server's url
     */
    public String getUrl(){
        return url;
    }

    /**
     * Allows to set server's url (with this form url:port)
     * @param url server's url
     */
    public void setUrl(String url){
        this.url = url;
    }
}
