package pet.yoko.apps.cmirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import pet.yoko.apps.cmirapp.tasks.TaskGetURL;

public class ConfigActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    EditText txtToken;
    ProgressBar progresso;
    TextView txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setTitle("Configuração de acesso");
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        txtToken = (EditText)findViewById(R.id.txtConfigToken);
        progresso = (ProgressBar)findViewById(R.id.configProgresso);
        progresso.setVisibility(View.GONE);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
    }

    public void salvarClick(View v) {
        if (tokenValido(txtToken.getText().toString())) {
            txtStatus.setText("TOKEN VÁLIDO");
        }
        else {
            txtStatus.setText("TOKEN INVÁLIDO");
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