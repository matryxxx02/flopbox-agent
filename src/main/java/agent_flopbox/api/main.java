package agent_flopbox.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class main {


    public static void main(String[] args) {
        ServersFtpApi serverApi = FtpApiClient.createService(ServersFtpApi.class);

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
