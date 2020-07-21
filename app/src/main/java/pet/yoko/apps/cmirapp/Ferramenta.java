package pet.yoko.apps.cmirapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Ferramenta {

    private static SharedPreferences sharedPref;

    public static SharedPreferences getSharedPref() {
        return sharedPref;
    }

    public static void setSharedPref(SharedPreferences sharedPref) {
        Ferramenta.sharedPref = sharedPref;
    }

    public static void setPref(String config, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(config, value);
        editor.commit();
    }

    public static String getPref(String config, String defaultValue) {
        String atualizacao = sharedPref.getString(config,defaultValue);
        return (atualizacao);
    }

    public static void prepararRecycleView(RecyclerView recyclerView, ArrayList items, RecyclerView.Adapter adapter, Context c) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(c,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
    }

}
