package pet.yoko.apps.cmirapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import pet.yoko.apps.cmirapp.R;
import pet.yoko.apps.cmirapp.db.AppDatabase;
import pet.yoko.apps.cmirapp.db.Item;

public class TaskCarregarItems extends AsyncTask <Void,Void,List<Item>> {

    AppDatabase db;
    SearchableSpinner spinner;
    Context context;

    public TaskCarregarItems(AppDatabase db, SearchableSpinner items, Context context) {
        this.db = db;
        this.spinner = items;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        List<Item> items = db.itemDao().getAll();
        List<String> descricao = new ArrayList<String>();
        for (int i=0; i<items.size(); i++) {
            descricao.add(items.get(i).getDescricao());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,descricao);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return items;
    }

}
