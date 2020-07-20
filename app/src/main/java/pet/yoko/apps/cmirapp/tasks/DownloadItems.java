package pet.yoko.apps.cmirapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pet.yoko.apps.cmirapp.Ferramenta;
import pet.yoko.apps.cmirapp.R;
import pet.yoko.apps.cmirapp.db.AppDatabase;
import pet.yoko.apps.cmirapp.db.Item;

public class DownloadItems extends AsyncTask <Void,Void,Void> {

    AppDatabase db;
    ProgressBar progresso;
    private OkHttpClient client = new OkHttpClient();
    Context context;

    public DownloadItems(AppDatabase db, ProgressBar progresso, Context context) {
        this.db = db;
        this.progresso = progresso;
        this.context = context;
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

    private void processarJSON() {
        String URL = context.getResources().getString(R.string.get_items) + Ferramenta.getPref("token","null") + "/items/listar/todos/";
        try {
            JSONArray arr = new JSONArray(run(URL));
            db.itemDao().delete_all();
            for (int i=0; i<arr.length();i++) {
                JSONObject obj = arr.getJSONObject(i);
                Item item = new Item(obj.getInt("id"),obj.getString("descricao_item"));
                db.itemDao().insert(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progresso.setVisibility(View.VISIBLE);
        progresso.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        this.processarJSON();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progresso.setVisibility(View.GONE);
        progresso.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
