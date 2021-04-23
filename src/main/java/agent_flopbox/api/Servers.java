package agent_flopbox.api;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Servers {

        @Expose
        private final List<Server> beers = null;

        public List<Server> getBeers() {
            return beers;
        }

}
