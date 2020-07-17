package pet.yoko.apps.cmirapp;

import android.content.SharedPreferences;

public class Ferramenta {

    public static void setPref(SharedPreferences sharedPref,String config, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(config, value);
        editor.commit();
    }

    public static String getPref(SharedPreferences sharedPref,String config, String defaultValue) {
        String atualizacao = sharedPref.getString(config,defaultValue);
        return (atualizacao);
    }

}
