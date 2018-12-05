package com.example.henri.iwine.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.henri.iwine.DAO.ConfiguracaoFirebase;
import com.example.henri.iwine.Entidades.Produtos;
import com.example.henri.iwine.R;
import com.google.firebase.database.DatabaseReference;


public class TelaAdiciona extends AppCompatActivity {
    private Button adicionarVinho;
    private Button sairLogin;
    private Button voltarEscolha;
    private EditText et_Tipo;
    private EditText et_Marca;
    private EditText et_Ano;
    private EditText et_Acompanhamento;
    private Produtos produtos;
    private DatabaseReference firebase;
    View viewTelaAdiciona;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adiciona);
        viewTelaAdiciona = findViewById(R.id.viewTelaAdiciona);
        viewTelaAdiciona.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));



        et_Tipo = (EditText) findViewById(R.id.et_Tipo);
        et_Marca = (EditText) findViewById(R.id.et_Marca);
        et_Ano = (EditText) findViewById(R.id.et_Ano);
        et_Acompanhamento = (EditText) findViewById(R.id.et_Acompanhamento);
        adicionarVinho = (Button) findViewById(R.id.bt_AdicionarVinho);
        voltarEscolha = (Button) findViewById(R.id.bt_vtEscolha);
        sairLogin = (Button) findViewById(R.id.bt_Sair);




        adicionarVinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtos = new Produtos();
                produtos.setTipo(et_Tipo.getText().toString());
                produtos.setMarca(et_Marca.getText().toString());
                produtos.setAno(et_Ano.getText().toString());
                produtos.setAcompanhamento(et_Acompanhamento.getText().toString());
                salvarProduto(produtos);
            }
        });



        voltarEscolha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaAdiciona.this,TelaEscolha.class);
                startActivity(intent);
                finish();
            }
        });

        sairLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaAdiciona.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean salvarProduto (Produtos produtos){
        try{
            firebase = ConfiguracaoFirebase.getFirebase().child("add produtos");
            firebase.child(produtos.getTipo()).setValue(produtos);
            Toast.makeText(TelaAdiciona.this, "Produto inserido com sucesso", Toast.LENGTH_LONG).show();
            return true;
    }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

