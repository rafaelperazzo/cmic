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
    String item;

    @ColumnInfo(name = "quantidade")
    float quantidade;

    @ColumnInfo(name = "data")
    String data;

    @ColumnInfo(name = "ua")
    String ua;

    @ColumnInfo(name = "finalidade")
    String finalidade;

    @ColumnInfo(name = "username")
    String username;

    @ColumnInfo(name = "medida")
    String medida;

    public Movimentacao(int id, String item, float quantidade, String data, String ua, String finalidade, String username,String medida) {
        this.id = id;
        this.item = item;
        this.quantidade = quantidade;
        this.data = data;
        this.ua = ua;
        this.finalidade = finalidade;
        this.username = username;
        this.medida = medida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
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
    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }
}
