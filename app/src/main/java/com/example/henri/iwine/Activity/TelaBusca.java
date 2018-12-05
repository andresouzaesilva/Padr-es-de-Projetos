package com.example.henri.iwine.Activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henri.iwine.Adapter.ProdutosAdapter;
import com.example.henri.iwine.DAO.ConfiguracaoFirebase;
import com.example.henri.iwine.Entidades.Produtos;
import com.example.henri.iwine.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TelaBusca extends AppCompatActivity {
    private Button sairLogin;
    private Button voltarEscolha;
    private ListView listView;
    private ArrayAdapter<Produtos> adpter;
    private ArrayList<Produtos> produtos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerProdutos;
    private AlertDialog alerta;
    private Produtos produtosExcluir;
    View viewTelaBusca;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_busca);
        viewTelaBusca = findViewById(R.id.viewTelaBusca);
        viewTelaBusca.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        produtos =  new ArrayList<>();

        listView = (ListView) findViewById(R.id.listViewProdutos);
        adpter = new ProdutosAdapter(this, produtos);
        listView.setAdapter(adpter);

        firebase = ConfiguracaoFirebase.getFirebase().child("add produtos");

        valueEventListenerProdutos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtos.clear();

                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Produtos produtosNovo = dados.getValue(Produtos.class);

                    produtos.add(produtosNovo);
                }

                adpter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        sairLogin = (Button) findViewById(R.id.bt_Sair);
        sairLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaBusca.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        voltarEscolha = (Button) findViewById(R.id.bt_vtEscolha);
        voltarEscolha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaBusca.this,TelaEscolha.class);
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                produtosExcluir = adpter.getItem(position);

                //cria o gerador do alertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(TelaBusca.this);

                //define o titulo
                builder.setTitle("Confirma exclusão? ");

                //define uma mensagem
                builder.setMessage("Você deseja excluir - " + produtosExcluir.getTipo().toString() + produtosExcluir.getMarca().toString() + " - ?");

                //define botão sim
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebase = ConfiguracaoFirebase.getFirebase().child("add produtos");
                        firebase.child(produtosExcluir.getTipo().toString()).removeValue();

                        Toast.makeText(TelaBusca.this, "Exclusão efetuada! ", Toast.LENGTH_LONG).show();
                    }
                });

                //define o botão não

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TelaBusca.this, "Exclusão cancelada! ", Toast.LENGTH_LONG).show();

                    }
                });

                //criar o alertDialog
                alerta = builder.create();

                //exibe alertDialog
                alerta.show();

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerProdutos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerProdutos);
    }
}
