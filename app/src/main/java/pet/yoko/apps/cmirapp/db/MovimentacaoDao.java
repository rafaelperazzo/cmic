package pet.yoko.apps.cmirapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovimentacaoDao {

    @Query("SELECT * FROM Movimentacao")
    List<Movimentacao> getAll();

    @Query("SELECT * FROM Movimentacao WHERE username=:username")
    List<Movimentacao> getAllUser(String username);

    @Query("DELETE FROM Movimentacao")
    void delete_all();

    @Insert
    void insert(Movimentacao movimentacao);

    @Query("DELETE FROM Movimentacao WHERE id=:id")
    void delete_id(int id);

}
