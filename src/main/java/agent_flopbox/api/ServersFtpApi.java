package agent_flopbox.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ServersFtpApi {
    @GET("servers/")
    Call<Servers> getAllServers();

    @POST("servers/")
    Call<?> createServer(@Body Server server);

    //TODO typer le call + finir les methodes

    @GET("servers/{alias}/{path}")
    Call<?> getFileOrDir(@Path("alias") String alias, @Path("path") String path);


}
