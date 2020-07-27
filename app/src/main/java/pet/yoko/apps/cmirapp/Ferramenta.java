package pet.yoko.apps.cmirapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import pet.yoko.apps.cmirapp.db.Movimentacao;

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

    public static void filtrarTabela(ArrayList<Movimentacao> listaMovimentacoes, AdapterMovimentacao adapter, String newText) {
        ArrayList<Movimentacao> filtrada = new ArrayList<>();
        if (!newText.isEmpty()) {
            for (Movimentacao linha: listaMovimentacoes) {
                if (linha.getItem().toLowerCase().contains(newText)) {
                    filtrada.add(linha);
                }
            }
            adapter.setItems(filtrada);
        }
        else {
            adapter.setItems(listaMovimentacoes);
        }
        adapter.notifyDataSetChanged();
    }

    public static int getAppPlayStoreVersion(String html) {
        Document doc = Jsoup.parse(html);
        int versaoPublicada = 0;
        Elements versao = doc.getElementsByClass("htlgb");
        try {
            versaoPublicada = Integer.parseInt(versao.get(7).text());
        }
        catch (NumberFormatException e) {
            versaoPublicada = 0;
        }
        return (versaoPublicada);
    }

}
