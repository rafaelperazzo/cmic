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

public class TaskDeletarMovimentacao extends AsyncTask<Void,Void,Void> {

    int id;
    private OkHttpClient client = new OkHttpClient();

    public TaskDeletarMovimentacao(int id) {
        this.id = id;
    }

    public void run(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String resposta = response.body().string();
            int a = 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //String URL = context.getResources().getString(R.string.url_base) + Ferramenta.getPref("token","null") + context.getResources().getString(R.string.deletar_movimentacao) + this.id;
        String URL = "https://sci02-ter-jne.ufca.edu.br/cgs/api/" + Ferramenta.getPref("token","null") + "/deletar/id/" + this.id;
        run(URL);
        return null;
    }
}
