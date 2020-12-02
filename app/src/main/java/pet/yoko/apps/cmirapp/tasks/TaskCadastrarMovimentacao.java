package pet.yoko.apps.cmirapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pet.yoko.apps.cmirapp.Ferramenta;
import pet.yoko.apps.cmirapp.db.AppDatabase;

public class TaskCadastrarMovimentacao extends AsyncTask<Void,Void,String> {

    AppDatabase db;
    ProgressBar progresso;
    Context context;
    int item;
    float quantidade;
    String medida;
    String setor;
    String finalidade;
    String detalhes;
    OkHttpClient client = new OkHttpClient();
    TaskCadastrarMovimentacaoResponse delegate;

    public TaskCadastrarMovimentacao(AppDatabase db, ProgressBar progresso, Context context, int item, float quantidade, String setor, String finalidade, String detalhes,String medida,TaskCadastrarMovimentacaoResponse delegate) {
        this.db = db;
        this.progresso = progresso;
        this.context = context;
        this.item = item;
        this.quantidade = quantidade;
        this.medida = medida;
        this.setor = setor;
        this.finalidade = finalidade;
        this.detalhes = detalhes;
        this.delegate = delegate;
    }

    private String run() {
        RequestBody formBody = new FormBody.Builder()
                .add("item", String.valueOf(this.item))
                .add("quantidade", String.valueOf(this.quantidade))
                .add("finalidade", this.finalidade)
                .add("descricao", this.detalhes)
                .add("ua", this.setor)
                .add("medida", this.medida)
                .add("token", Ferramenta.getPref("token","null"))
                .build();
        Request request = new Request.Builder()
                .url("https://sci02-ter-jne.ufca.edu.br/cgs/processar_movimentacao.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        String resposta = "";
        try {
            Response response = call.execute();
            resposta= response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            resposta = "ERRO: Informar ao desenvolvedor!";
        }
        return (resposta.trim());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progresso.setVisibility(View.VISIBLE);
        progresso.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String resposta = run();
        return (resposta);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progresso.setVisibility(View.GONE);
        progresso.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.delegate.cadastrarMovimentacaoFinish(s);
    }
}
