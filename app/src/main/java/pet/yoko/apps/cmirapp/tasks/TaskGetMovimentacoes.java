package pet.yoko.apps.cmirapp.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import pet.yoko.apps.cmirapp.db.AppDatabase;
import pet.yoko.apps.cmirapp.db.Movimentacao;

public class TaskGetMovimentacoes extends AsyncTask <Void,Void, List<Movimentacao>> {
    AppDatabase db;
    ProgressBar progresso;

    public TaskGetMovimentacoes(AppDatabase db, ProgressBar progresso) {
        this.db = db;
        this.progresso = progresso;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progresso.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<Movimentacao> movimentacaos) {
        super.onPostExecute(movimentacaos);
        progresso.setVisibility(View.GONE);
    }

    @Override
    protected List<Movimentacao> doInBackground(Void... voids) {
        List<Movimentacao> movimentacoes = db.movimentacaoDao().getAll();
        return (movimentacoes);
    }

}
