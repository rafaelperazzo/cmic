package pet.yoko.apps.cmirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;
import java.util.concurrent.ExecutionException;

import pet.yoko.apps.cmirapp.db.DatabaseClient;
import pet.yoko.apps.cmirapp.db.Item;
import pet.yoko.apps.cmirapp.tasks.TaskCarregarItems;

public class MovimentacaoActivity extends AppCompatActivity {
    SearchableSpinner cmbItem;
    List<Item> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimentacao);
        setTitle("Movimentação de item");
        cmbItem = (SearchableSpinner) findViewById(R.id.cmbItem);
        cmbItem.setTitle("Escolha o item");
        cmbItem.setPositiveButton("OK");
        TaskCarregarItems tci = new TaskCarregarItems(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),cmbItem,getApplicationContext());
        try {
            items = tci.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}