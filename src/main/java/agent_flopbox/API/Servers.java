package agent_flopbox.API;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Servers {

        @Expose
        private final List<Server> servers = null;

        public List<Server> getServers() {
            return servers;
        }

}
