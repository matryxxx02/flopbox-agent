package agent_flopbox.API;

import agent_flopbox.Services.Controllers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.InputStream;
import java.util.List;

public class main {


    public static void main(String[] args) {
        Controllers controllers = new Controllers();

        controllers.get("home", "Main.py");
    }
}
