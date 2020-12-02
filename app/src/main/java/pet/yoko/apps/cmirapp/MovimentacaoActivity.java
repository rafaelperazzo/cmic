package pet.yoko.apps.cmirapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pet.yoko.apps.cmirapp.db.DatabaseClient;
import pet.yoko.apps.cmirapp.db.Item;
import pet.yoko.apps.cmirapp.db.Movimentacao;
import pet.yoko.apps.cmirapp.tasks.DownloadMovimentacoes;
import pet.yoko.apps.cmirapp.tasks.TaskCadastrarMovimentacao;
import pet.yoko.apps.cmirapp.tasks.TaskCadastrarMovimentacaoResponse;
import pet.yoko.apps.cmirapp.tasks.TaskCarregarItems;
import pet.yoko.apps.cmirapp.tasks.TaskCarregarItemsResponse;
import pet.yoko.apps.cmirapp.tasks.TaskGetMovimentacoes;

public class MovimentacaoActivity extends AppCompatActivity implements TaskCarregarItemsResponse, TaskCadastrarMovimentacaoResponse {
    SearchableSpinner cmbItem;
    Spinner cmbSetor;
    Spinner cmbFinalidade;
    EditText txtQuantidade;
    Spinner cmbMedida;
    EditText txtDetalhes;
    ProgressBar progresso;
    ArrayList<Item> items;
    ArrayList<Movimentacao> movimentacoes;
    AdapterMovimentacao adapter;
    TextView txtStatus;
    RecyclerView recyclerView;
    LinearLayout cadastro;
    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimentacao);
        setTitle("Movimentação de item");
        cmbItem = (SearchableSpinner) findViewById(R.id.cmbItem);
        cmbItem.setTitle("Escolha o item");
        cmbItem.setPositiveButton("OK");
        cmbSetor = (Spinner)findViewById(R.id.cmbSetor);
        prepararSetor();
        cmbFinalidade = (Spinner)findViewById(R.id.cmbFinalidade);
        txtQuantidade = (EditText) findViewById(R.id.txtQuantidade);
        cmbMedida = (Spinner)findViewById(R.id.txtMedida);
        txtDetalhes = (EditText) findViewById(R.id.txtDetalhes);
        progresso = (ProgressBar)findViewById(R.id.progressoMovimentacao);
        progresso.setVisibility(View.GONE);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        cadastro = (LinearLayout)findViewById(R.id.layoutCadastro);
        search = (SearchView)findViewById(R.id.searchMovimentacao);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Ferramenta.filtrarTabela(movimentacoes,adapter,query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Ferramenta.filtrarTabela(movimentacoes,adapter,newText);
                return false;
            }
        });

        TaskCarregarItems tci = new TaskCarregarItems(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),cmbItem,getApplicationContext(),this);
        tci.execute();
        recyclerView = (RecyclerView)findViewById(R.id.tabelaMovimentacoes);
        movimentacoes = new ArrayList<>();
        adapter = new AdapterMovimentacao(movimentacoes,this);
        prepararRecycleView(recyclerView,adapter);
        prepararTabela();
    }

    public void tabelaClick(View v) {
        if (cadastro.getVisibility()==View.GONE) {
            cadastro.setVisibility(View.VISIBLE);
        }
        else {
            cadastro.setVisibility(View.GONE);
        }
    }

    private void prepararSetor() {
        int index = 0;
        for (int i=0; i<cmbSetor.getAdapter().getCount(); i++) {
            if (cmbSetor.getAdapter().getItem(i).equals(Ferramenta.getPref("ua","null"))) {
                index = i;
                break;
            }
        }
        cmbSetor.setSelection(index);
        if (Ferramenta.getPref("permissao","-1").equals("1")) {
            cmbSetor.setEnabled(false);
        }
        else {
            cmbSetor.setEnabled(true);
        }
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
            String medida = cmbMedida.getSelectedItem().toString();
            TaskCadastrarMovimentacao submit = new TaskCadastrarMovimentacao(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),progresso,getApplicationContext(),item_id,quantidade,setor,finalidade,detalhes,medida,this);
            String resposta = "";
            submit.execute();

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
        TaskGetMovimentacoes tgm = new TaskGetMovimentacoes(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),progresso,adapter,movimentacoes);
        DownloadMovimentacoes dm = new DownloadMovimentacoes(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),getApplicationContext());
        dm.execute();
        tgm.execute();
    }

    public void recarregarDados(View v) {
        prepararTabela();
    }

    @Override
    public void processFinish(ArrayList<Item> items) {
        this.items = items;
        //https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
    }

    @Override
    public void cadastrarMovimentacaoFinish(String response) {
        if (response.equals("200")) {
            txtStatus.setText("Movimentação cadastrada com sucesso");
            txtStatus.setBackgroundColor(Color.GREEN);
            txtStatus.setTextColor(Color.BLACK);
            prepararTabela();
        }
        else {
            txtStatus.setText(response);
            txtStatus.setBackgroundColor(Color.RED);
            txtStatus.setTextColor(Color.WHITE);
        }
    }
}