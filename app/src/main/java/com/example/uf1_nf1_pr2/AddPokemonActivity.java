package com.example.uf1_nf1_pr2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddPokemonActivity extends AppCompatActivity {
    Button btn_add;
    EditText et_nom, et_tipo, et_num;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pokemon);

        this.setTitle("Añadir pokemon");//Para cambiar el nombre de la pantalla
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mfirestore = FirebaseFirestore.getInstance();

        et_nom = findViewById(R.id.ET_nom);
        et_tipo = findViewById(R.id.ET_tipo);
        et_num = findViewById(R.id.ET_num);
        btn_add= findViewById(R.id.BTN_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomPokemon = et_nom.getText().toString().trim();
                String tipoPokemon = et_tipo.getText().toString().trim();
                String numPokemon = et_num.getText().toString().trim();

            if(nomPokemon.isEmpty() && tipoPokemon.isEmpty() && numPokemon.isEmpty()){
                Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
            }else{
                postPokemon(nomPokemon, tipoPokemon, numPokemon);
            }
            }
        });
    }

    private void postPokemon(String nomPokemon, String tipoPokemon, String numPokemon) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre",nomPokemon);
        map.put("tipo",tipoPokemon);
        map.put("numero",numPokemon);

        mfirestore.collection("pokemon").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Pokemon añadido", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al añadir", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;//Para retroceder de pantalla
    }
}