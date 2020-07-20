package pet.yoko.apps.cmirapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movimentacao.class, Item.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract MovimentacaoDao movimentacaoDao();
    public abstract ItemDao itemDao();

}
