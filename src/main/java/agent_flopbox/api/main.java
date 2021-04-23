package agent_flopbox.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class main {


    public static void main(String[] args) {
        ServersFtpApi serverApi = FtpApiClient.createService(ServersFtpApi.class);

        serverApi.getAllServers().enqueue(new Callback<Servers>() {
            @Override
            public void onResponse(Call<Servers> call, Response<Servers> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<Servers> call, Throwable t) {
                System.out.println("response.body()");
            }
        });
    }
}
