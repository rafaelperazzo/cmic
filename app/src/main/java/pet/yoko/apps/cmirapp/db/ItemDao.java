package pet.yoko.apps.cmirapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao  {

    @Query("SELECT * FROM Item ORDER BY descricao")
    List<Item> getAll();

    @Query("DELETE FROM Item")
    void delete_all();

    @Insert
    void insert(Item item);

}
