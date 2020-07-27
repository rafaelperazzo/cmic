package pet.yoko.apps.cmirapp.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pet.yoko.apps.cmirapp.R;

public class TaskCheckUpdate extends AsyncTask<Void,Void,Integer> {

    Context context;
    private OkHttpClient client = new OkHttpClient();

    public TaskCheckUpdate(Context context) {
        this.context = context;
    }

    public int getVersionCode() {
        int versionCode = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return (versionCode);
    }

    public int getAppPlayStoreVersion(String html) {
        Document doc = Jsoup.parse(html);
        int versaoPublicada = 0;
        Elements versao = doc.getElementsByClass("htlgb");
        try {
            versaoPublicada = (int)Float.parseFloat(versao.get(7).text());
        }
        catch (NumberFormatException e) {
            versaoPublicada = 0;
        }
        catch (IndexOutOfBoundsException e) {
            versaoPublicada = 0;
        }
        return (versaoPublicada);
    }

    public String run(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            return ("[]");
        }
    }

    private void confirmacao() {
        new AlertDialog.Builder(this.context)
                .setTitle("ATUALIZAÇÃO DISPONÍVEL")
                .setMessage("Uma nova versão do app está disponível.")
                .setIcon(context.getDrawable(R.drawable.smartphone))
                .setPositiveButton("ATUALIZAR", (dialog, whichButton) -> {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=pet.yoko.apps.cmirapp");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);

                }).show();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        String url = context.getResources().getString(R.string.play);
        String html = run(url);
        int versao_publicada = getAppPlayStoreVersion(html);
        return versao_publicada;
    }

    @Override
    protected void onPostExecute(Integer versao_publicada) {
        super.onPostExecute(versao_publicada);
        int versaoAtual = getVersionCode();
        if (versaoAtual<versao_publicada) {
            confirmacao();
        }
    }
}
