package com.example.henri.iwine.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.henri.iwine.R;

public class TelaEscolha extends AppCompatActivity {
    private Button sairLogin;
    private Button entrarTelaBusca;
    private Button entrarTelaAdiciona;
    View viewTelaEscolha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_escolha);
        viewTelaEscolha = findViewById(R.id.viewTelaEscolha);
        viewTelaEscolha.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        sairLogin = (Button) findViewById(R.id.bt_Sair);
        sairLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEscolha.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        entrarTelaBusca = (Button) findViewById(R.id.bt_Buscar);
        entrarTelaBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEscolha.this,TelaBusca.class);
                startActivity(intent);
                finish();
            }
        });

        entrarTelaAdiciona = (Button) findViewById(R.id.bt_Add);
        entrarTelaAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEscolha.this,TelaAdiciona.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
