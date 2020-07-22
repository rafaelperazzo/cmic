package pet.yoko.apps.cmirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import pet.yoko.apps.cmirapp.db.DatabaseClient;
import pet.yoko.apps.cmirapp.tasks.DownloadItems;
import pet.yoko.apps.cmirapp.tasks.DownloadMovimentacoes;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    ImageView imgUser;
    TextView txtUser;
    ProgressBar progresso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Items controlados");
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        Ferramenta.setSharedPref(sharedPref);
        imgUser = (ImageView)findViewById(R.id.imgUser);
        txtUser = (TextView)findViewById(R.id.txtUser);
        progresso = (ProgressBar)findViewById(R.id.progresso);
        progresso.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Ferramenta.getPref("token","NULL").equals("NULL")) {
            verificarToken();
        }
        else {
            if (Ferramenta.getPref("items","NULL").equals("NULL")) {
                DownloadItems di = new DownloadItems(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),progresso,getApplicationContext());
                di.execute();
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                Ferramenta.setPref("items",timeStamp);
            }

            DownloadMovimentacoes dm = new DownloadMovimentacoes(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),getApplicationContext());
            dm.execute();
            this.imgUser.setImageResource(R.drawable.authentication);
            this.txtUser.setText(Ferramenta.getPref("nome","NULL"));
        }

    }

    private void verificarToken() {
        String token = Ferramenta.getPref("token","NULL");
        if (token.equals("NULL")) {
            Intent intent = new Intent(this, ConfigActivity.class);
            startActivity(intent);
        }
        else {
            imgUser.setImageResource(R.drawable.authentication);
            txtUser.setText(Ferramenta.getPref("nome","INDEFINIDO"));
        }
    }

    public void movimentacaoClick(View v) {
        Intent intent = new Intent(this, MovimentacaoActivity.class);
        startActivity(intent);

    }

    public void configClick(View v) {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }

    public void relatorioClick(View v) {

    }
}