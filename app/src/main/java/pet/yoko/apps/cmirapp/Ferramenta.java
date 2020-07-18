package pet.yoko.apps.cmirapp;

import android.content.SharedPreferences;

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

}
