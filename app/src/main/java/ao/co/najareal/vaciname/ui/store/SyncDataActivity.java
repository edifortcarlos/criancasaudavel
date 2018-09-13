package ao.co.najareal.vaciname.ui.store;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ao.co.najareal.vaciname.R;

public class SyncDataActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);

        Map<String , String> obj = new HashMap<>();

        obj.put("id","1");
        obj.put("nome", "Luis Alex");
        obj.put("idade","9");

        List<Map<String , String>> list = new ArrayList<>();
        list.add(obj);

        obj = new HashMap<>();
        obj.put("id","2");
        obj.put("nome", "Luis Joaquim");
        obj.put("idade","12");
        list.add(obj);

        obj = new HashMap<>();
        obj.put("morada","Luanda");

        firestore = FirebaseFirestore.getInstance();

        for(Map<String , String> s: list){

            firestore.collection("testeVaciname")
                    .document().collection(s.get("nome"))
                    .add(s)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(SyncDataActivity.this, "Enviado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SyncDataActivity.this, "Erro ao enviar", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }


}
