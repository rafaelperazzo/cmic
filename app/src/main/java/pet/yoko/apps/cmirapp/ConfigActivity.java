package pet.yoko.apps.cmirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import pet.yoko.apps.cmirapp.tasks.TaskGetURL;

public class ConfigActivity extends AppCompatActivity {
    EditText txtToken;
    ProgressBar progresso;
    TextView txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setTitle("Configuração de acesso");
        txtToken = (EditText)findViewById(R.id.txtConfigToken);
        String token = Ferramenta.getPref("token","NULL");
        txtToken.setText(token);
        progresso = (ProgressBar)findViewById(R.id.configProgresso);
        progresso.setVisibility(View.GONE);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
    }

    public void salvarClick(View v) {
        if (tokenValido(txtToken.getText().toString())) {
            Ferramenta.setPref("token",txtToken.getText().toString());
        }
        else {
            txtStatus.setText("TOKEN INVÁLIDO");
            txtStatus.setTextColor(Color.WHITE);
            txtStatus.setBackgroundColor(Color.RED);
        }
    }

    public void sairClick(View v) {
        finishAffinity();
    }

    public boolean tokenValido(String token) {
        String URL = getResources().getString(R.string.token_valido) + token;
        TaskGetURL processo = new TaskGetURL(URL);
        String retorno = "0";
        try {
            progresso.setVisibility(View.VISIBLE);
            retorno = processo.execute().get();
            progresso.setVisibility(View.GONE);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(retorno)==1) {
            return(true);
        }
        else {
            return(false);
        }
    }

}