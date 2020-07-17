package pet.yoko.apps.cmirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MovimentacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimentacao);
        setTitle("Movimentação de item");
    }
}