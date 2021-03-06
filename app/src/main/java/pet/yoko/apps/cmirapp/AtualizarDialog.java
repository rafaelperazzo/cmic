package pet.yoko.apps.cmirapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AtualizarDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("ATUALIZAÇÃO DISPONÍVEL")
                .setPositiveButton("ATUALIZAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=pet.yoko.apps.cmirapp");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }

}
