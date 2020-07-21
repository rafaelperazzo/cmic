package pet.yoko.apps.cmirapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pet.yoko.apps.cmirapp.db.DatabaseClient;
import pet.yoko.apps.cmirapp.db.Item;
import pet.yoko.apps.cmirapp.db.Movimentacao;
import pet.yoko.apps.cmirapp.tasks.TaskCadastrarMovimentacao;
import pet.yoko.apps.cmirapp.tasks.TaskCarregarItems;
import pet.yoko.apps.cmirapp.tasks.TaskGetMovimentacoes;

public class MovimentacaoActivity extends AppCompatActivity {
    SearchableSpinner cmbItem;
    Spinner cmbSetor;
    Spinner cmbFinalidade;
    EditText txtQuantidade;
    EditText txtDetalhes;
    ProgressBar progresso;
    List<Item> items;
    ArrayList<Movimentacao> movimentacoes;
    AdapterMovimentacao adapter;
    TextView txtStatus;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimentacao);
        setTitle("Movimentação de item");
        cmbItem = (SearchableSpinner) findViewById(R.id.cmbItem);
        cmbItem.setTitle("Escolha o item");
        cmbItem.setPositiveButton("OK");
        cmbSetor = (Spinner)findViewById(R.id.cmbSetor);
        cmbFinalidade = (Spinner)findViewById(R.id.cmbFinalidade);
        txtQuantidade = (EditText) findViewById(R.id.txtQuantidade);
        txtDetalhes = (EditText) findViewById(R.id.txtDetalhes);
        progresso = (ProgressBar)findViewById(R.id.progressoMovimentacao);
        progresso.setVisibility(View.GONE);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        TaskCarregarItems tci = new TaskCarregarItems(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),cmbItem,getApplicationContext());
        try {
            items = tci.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recyclerView = (RecyclerView)findViewById(R.id.tabelaMovimentacoes);
        movimentacoes = new ArrayList<>();
        adapter = new AdapterMovimentacao(movimentacoes);
        prepararRecycleView(recyclerView,adapter);
        prepararTabela();
    }

    public void prepararRecycleView(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
    }

    public void cadastrarClick(View v) {
        if (isNotEmpty(txtQuantidade) && isNotEmpty(txtDetalhes)) {
            int item = cmbItem.getSelectedItemPosition();
            int item_id = items.get(item).getId();
            float quantidade = 0;
            try {
                quantidade = Float.parseFloat(txtQuantidade.getText().toString());
            }
            catch (NumberFormatException e) {
                quantidade = 0;
            }

            String setor = cmbSetor.getSelectedItem().toString();
            String finalidade = cmbFinalidade.getSelectedItem().toString();
            String detalhes = txtDetalhes.getText().toString();
            TaskCadastrarMovimentacao submit = new TaskCadastrarMovimentacao(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),progresso,getApplicationContext(),item_id,quantidade,setor,finalidade,detalhes);
            String resposta = "";
            try {
                resposta = submit.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
                resposta = "ExecutionException";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resposta = "InterruptedException";
            }
            if (resposta.equals("200")) {
                txtStatus.setText("Movimentação cadastrada com sucesso");
                txtStatus.setBackgroundColor(Color.GREEN);
                txtStatus.setTextColor(Color.BLACK);
                prepararTabela();
            }
            else {
                txtStatus.setText(resposta);
                txtStatus.setBackgroundColor(Color.RED);
                txtStatus.setTextColor(Color.WHITE);
            }
        }
    }

    private boolean isNotEmpty(EditText edit) {
        if (edit.getText().toString().equals("")) {
            edit.setError("Precisa ser preenchido!");
            return (false);
        }
        else {
            return (true);
        }
    }

    public void prepararTabela() {
        TaskGetMovimentacoes tgm = new TaskGetMovimentacoes(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),progresso);
        try {
            movimentacoes = (ArrayList<Movimentacao>) tgm.execute().get();
            adapter.setItems(movimentacoes);
            adapter.notifyDataSetChanged();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void recarregarDados(View v) {
        prepararTabela();
    }
}