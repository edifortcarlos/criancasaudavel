package ao.co.najareal.vaciname.ui.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ao.co.najareal.vaciname.R;

public class Dialogos {

    public static void validar(Context context, String titulo, String descricao,
                         View.OnClickListener listenerSim, View.OnClickListener listenerNao,String textoSim,String textoNao) {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.modal_alert, null);
        final TextView txtDescricao = v.findViewById(R.id.txtDescricao);
        final TextView txtTitulo = v.findViewById(R.id.txtTitulo);
        TextView txtSim = v.findViewById(R.id.txtSim);
        TextView txtNao = v.findViewById(R.id.txtNao);
        View viewSeparador = v.findViewById(R.id.viewSeparador);

        txtSim.setText(textoSim);
        txtNao.setText(textoNao);
        txtTitulo.setText(titulo);
        txtDescricao.setText(descricao);

        dialog.setView(v);
        final android.app.AlertDialog ab = dialog.create();

        txtSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.dismiss();
            }
        });

        txtNao.setOnClickListener(listenerNao);
        if (listenerNao == null) {
            txtNao.setVisibility(View.GONE);
            viewSeparador.setVisibility(View.GONE);
        }
        /*
        if (listenerSim == null) {
            txtSim.setVisibility(View.GONE);
            viewSeparador.setVisibility(View.GONE);
        }*/

        ab.show();
    }
}
