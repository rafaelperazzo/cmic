package pet.yoko.apps.cmirapp.tasks;

import java.util.ArrayList;

import pet.yoko.apps.cmirapp.db.Item;

public interface TaskCarregarItemsResponse {

    void processFinish(ArrayList<Item> items);

}
