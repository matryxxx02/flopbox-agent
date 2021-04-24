package agent_flopbox.API;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface ServersFtpApi {
    @GET("server/")
    Call<List<Server>> getAllServers();

    @POST("server/")
    Call<?> createServer(@Body Server server);

    //TODO typer le call + finir les methodes

    @POST("file/{alias}/{path}")
    Call<ResponseBody> createFileOrDir(@Path("alias") String alias, @Path("path") String path);

    @GET("file/{alias}/{path}")
    Call<ResponseBody> getFileOrDir(@Path("alias") String alias, @Path("path") String path);

    @DELETE("servers/{alias}/{path}")
    Call<ResponseBody> deleteFile(@Path("alias") String alias, @Path("path") String path);

    @PUT("servers/{alias}/{path}")
    Call<ResponseBody> updateFileOrDir(@Path("alias") String alias, @Path("path") String path);

}
