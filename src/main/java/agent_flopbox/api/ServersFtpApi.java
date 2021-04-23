package agent_flopbox.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;


public interface ServersFtpApi {
    @GET("servers/")
    Call<List<Server>> getAllServers();

    @POST("servers/")
    Call<?> createServer(@Body Server server);

    //TODO typer le call + finir les methodes

    @GET("servers/{alias}/{path}")
    Call<?> getFileOrDir(@Path("alias") String alias, @Path("path") String path);


}
