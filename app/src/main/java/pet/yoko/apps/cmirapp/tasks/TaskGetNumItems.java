package pet.yoko.apps.cmirapp.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import pet.yoko.apps.cmirapp.db.AppDatabase;
import pet.yoko.apps.cmirapp.db.Item;

public class TaskGetNumItems extends AsyncTask <Void,Void, List<Item>> {

    AppDatabase db;
    TaskCarregarItemsResponse delegate;

    public TaskGetNumItems(AppDatabase db, TaskCarregarItemsResponse delegate) {
        this.db = db;
        this.delegate = delegate;
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        List<Item> items = db.itemDao().getAll();
        return items;
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        super.onPostExecute(items);
        delegate.processFinish((ArrayList<Item>) items);
    }
}
