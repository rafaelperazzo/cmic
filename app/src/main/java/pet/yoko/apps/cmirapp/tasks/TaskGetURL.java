package pet.yoko.apps.cmirapp.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskGetURL extends AsyncTask<Void, Void, String> {

    private String url;
    OkHttpClient client = new OkHttpClient();

    public TaskGetURL(String url) {
        this.url = url;
    }

    public String run(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            return ("0");
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        String retorno = this.run(url);
        return retorno;
    }
}
