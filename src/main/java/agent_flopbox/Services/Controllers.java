package agent_flopbox.Services;

import agent_flopbox.API.FtpApiClient;
import agent_flopbox.API.Server;
import agent_flopbox.API.ServersFtpApi;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class Controllers {
    protected ServersFtpApi serverApi;
    protected FunctionsApi functionsApi = new FunctionsApi();

    public Controllers(){
        this.serverApi = FtpApiClient.createService(ServersFtpApi.class);
    }

    /**
     *
     * @param alias
     * @param path
     */
    public void get(final String alias, final String path){
        this.serverApi.getFileOrDir(alias, path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    functionsApi.saveFile(response.body(), alias,  path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Fail to get File or Directory");
            }
        });
    }

    /**
     *
     * @param alias
     * @param path
     */
    public void create(final String alias, final String path){
        this.serverApi.createFileOrDir(alias, path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    functionsApi.uploadFile(alias, path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Fail to update File or Directory");
            }
        });
    }

    /**
     *
     * @param alias
     * @param path
     */
    public void delete(String alias, String path){

        this.serverApi.deleteFile(alias, path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Fail to delete file");
            }
        });
    }

    public void update(String alias, String path){
        this.serverApi.updateFileOrDir(alias, path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Fail to update File or Directory");
            }
        });
    }

    public void getServers(){
        serverApi.getAllServers().enqueue(new Callback<List<Server>>() {
            @Override
            public void onResponse(Call<List<Server>> call, Response<List<Server>> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Server>> call, Throwable t) {
                System.out.println("cxvxcv");
            }
        });
    }

}
