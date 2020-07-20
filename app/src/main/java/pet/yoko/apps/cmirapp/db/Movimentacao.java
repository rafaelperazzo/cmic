package pet.yoko.apps.cmirapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Movimentacao implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int id;

    @ColumnInfo(name = "item")
    int item;

    @ColumnInfo(name = "quantidade")
    int quantidade;

    @ColumnInfo(name = "data")
    String data;

    @ColumnInfo(name = "ua")
    String ua;

    @ColumnInfo(name = "finalidade")
    String finalidade;

    @ColumnInfo(name = "username")
    String username;

    public Movimentacao(int id, int item, int quantidade, String data, String ua, String finalidade, String username) {
        this.id = id;
        this.item = item;
        this.quantidade = quantidade;
        this.data = data;
        this.ua = ua;
        this.finalidade = finalidade;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
