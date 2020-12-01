package pet.yoko.apps.cmirapp.tasks;

import android.content.Context;
import android.os.AsyncTask;

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
import pet.yoko.apps.cmirapp.db.Movimentacao;

public class DownloadMovimentacoes extends AsyncTask <Void,Void,Void> {

    AppDatabase db;
    Context context;
    private OkHttpClient client = new OkHttpClient();

    public DownloadMovimentacoes(AppDatabase db, Context context) {
        this.db = db;
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

    @Override
    protected Void doInBackground(Void... voids) {
        String resposta = run(context.getResources().getString(R.string.url_base) + Ferramenta.getPref("token","NULL") + context.getResources().getString(R.string.get_movimentacoes));
        try {
            JSONArray arr = new JSONArray(resposta);
            db.movimentacaoDao().delete_all();
            for (int i=0; i<arr.length();i++) {
                JSONObject obj = arr.getJSONObject(i);
                Movimentacao movimentacao = new Movimentacao(obj.getInt("id"),obj.getString("descricao_item"),(float)obj.getDouble("quantidade"),obj.getString("data"),obj.getString("unidade"),obj.getString("finalidade"),obj.getString("username"),obj.getString("descricao_medida"));
                db.movimentacaoDao().insert(movimentacao);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
