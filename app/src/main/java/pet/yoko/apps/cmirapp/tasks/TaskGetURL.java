package pet.yoko.apps.cmirapp.tasks;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskGetURL extends AsyncTask<Void, Void, JSONArray> {

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
            return ("[]");
        }
    }

    @Override
    protected JSONArray doInBackground(Void... voids) {
        String retorno = this.run(url);
        JSONArray arr = null;
        try {
            arr = new JSONArray(retorno);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
