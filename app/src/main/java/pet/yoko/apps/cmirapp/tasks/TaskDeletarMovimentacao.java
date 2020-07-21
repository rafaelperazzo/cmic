package pet.yoko.apps.cmirapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pet.yoko.apps.cmirapp.Ferramenta;
import pet.yoko.apps.cmirapp.R;
import pet.yoko.apps.cmirapp.db.AppDatabase;

public class TaskDeletarMovimentacao extends AsyncTask<Void,Void,String> {

    AppDatabase db;
    int id;
    ProgressBar progresso;
    Context context;
    private OkHttpClient client = new OkHttpClient();

    public TaskDeletarMovimentacao(AppDatabase db, int id, ProgressBar progresso, Context context) {
        this.db = db;
        this.id = id;
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
            return ("ERRO: IOException");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progresso.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progresso.setVisibility(View.GONE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        String URL = context.getResources().getString(R.string.url_base) + Ferramenta.getPref("token","null") + context.getResources().getString(R.string.deletar_movimentacao) + String.valueOf(this.id);
        String resposta = run(URL);
        return resposta;
    }
}
