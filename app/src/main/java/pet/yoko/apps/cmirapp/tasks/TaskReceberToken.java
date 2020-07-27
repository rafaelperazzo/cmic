package pet.yoko.apps.cmirapp.tasks;

import android.content.Context;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pet.yoko.apps.cmirapp.R;

public class TaskReceberToken extends AsyncTask<Void,Void,String> {

    Context context;
    String email;
    ProgressBar progresso;
    private OkHttpClient client = new OkHttpClient();

    public TaskReceberToken(Context context, String email,ProgressBar progresso) {
        this.context = context;
        this.email = email;
        this.progresso = progresso;
    }

    private void confirmacao(String msg) {
        new AlertDialog.Builder(this.context)
                .setTitle("ENVIO DO TOKEN")
                .setMessage(msg)
                .setIcon(context.getDrawable(R.drawable.smartphone))
                .setPositiveButton("OK", (dialog, whichButton) -> {

                }).show();
    }

    private String run() {
        RequestBody formBody = new FormBody.Builder()
                .add("email", String.valueOf(this.email))
                .build();
        Request request = new Request.Builder()
                .url("https://sci02-ter-jne.ufca.edu.br/cgs/receber_token.php")
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
        progresso.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        progresso.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        String retorno = run();
        return retorno;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String mensagem = "";
        if (s.equals("200")) {
            mensagem = "E-mail enviado com sucesso!";
        }
        else if (s.equals("500")) {
            mensagem = "Não foi possível enviar o e-mail. Contactar o desenvolvedor.";
        }
        else if (s.equals("404")){
            mensagem = "E-mail não cadastrado na base de dados.";
        }
        else {
            mensagem = "Erro não identificado.";
        }
        progresso.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        progresso.setVisibility(View.GONE);
        confirmacao(mensagem);
    }
}
