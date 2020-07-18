package pet.yoko.apps.cmirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Items controlados");
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        Ferramenta.setSharedPref(sharedPref);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verificarToken();
    }

    private void verificarToken() {
        String token = Ferramenta.getPref("token","NULL");
        if (token.equals("NULL")) {
            Intent intent = new Intent(this, ConfigActivity.class);
            startActivity(intent);
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