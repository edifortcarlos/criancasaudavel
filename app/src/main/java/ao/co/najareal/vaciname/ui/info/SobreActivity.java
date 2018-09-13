
package ao.co.najareal.vaciname.ui.info;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ao.co.najareal.vaciname.R;

public class SobreActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtTelefone1,txtTelefone2,txtEmail,txtVersao;
    private String telefoneSelecionado = "00244937304095";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sobre_app);
        txtTelefone1 = findViewById(R.id.txtTelefone1);
        txtVersao = findViewById(R.id.txtVersao);
        txtTelefone2 = findViewById(R.id.txtTelefone2);
        txtEmail = findViewById(R.id.txtEmail);

        txtTelefone1.setOnClickListener(this);
        txtTelefone2.setOnClickListener(this);
        txtEmail.setOnClickListener(this);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            txtVersao.setText(getString(R.string.vacina) + " " + version + " ( " + pInfo.versionCode + " )");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            txtVersao.setText("-");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtTelefone1:
                telefoneSelecionado = "00244937304095";
                if(verifyPermitions()){
                    ligar(telefoneSelecionado);
                }
                break;
            case R.id.txtTelefone2:
                telefoneSelecionado = "00244916146281";
                if(verifyPermitions()){
                    ligar(telefoneSelecionado);
                }
                break;
        }
    }


    private final int REQUEST_PERMISSION = 0;
    private boolean hasPermitions = true;


    private void askPermitions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.CALL_PHONE
                }, REQUEST_PERMISSION);
    }

    // retorna true se todas permissoes forem concedidas
    private boolean verifyPermitions() {
        hasPermitions = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;

        if (!hasPermitions) {
            askPermitions();
        }
        return hasPermitions;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ligar(telefoneSelecionado);
                } else {
                    Toast.makeText(this, "Para Ligar sugestões de as permissões necessarias ao aplicativo", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void ligar(String numero) {
        Intent chamada = null;

        if (chamada == null) {
            chamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        startActivity(chamada);
    }
}
