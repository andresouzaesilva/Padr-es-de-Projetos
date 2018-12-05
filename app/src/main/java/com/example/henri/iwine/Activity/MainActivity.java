package com.example.henri.iwine.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henri.iwine.DAO.ConfiguracaoFirebase;
import com.example.henri.iwine.Entidades.Usuarios;
import com.example.henri.iwine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button entrarTelaEscolha;
    private EditText etLogin;
    private EditText etSenha;
    private TextView tvAbreCadastro;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;
    View viewMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMainActivity = findViewById(R.id.viewMainActivity);
        viewMainActivity.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        etLogin = (EditText) findViewById(R.id.etLogin);
        etSenha = (EditText) findViewById(R.id.etSenha);
        tvAbreCadastro = (TextView) findViewById(R.id.tvAbreCadastro);
        entrarTelaEscolha = (Button) findViewById(R.id.bt_Entra);


        entrarTelaEscolha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etLogin.getText().toString().equals("") && !etSenha.getText().toString().equals("")){
                    usuarios = new Usuarios();
                    usuarios.setLogin(etLogin.getText().toString());
                    usuarios.setSenha(etSenha.getText().toString());

                    validarLogin();
                }else {
                    Toast.makeText(MainActivity.this, "Preencha os campos de login e senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroUsuario();
            }
        });


    }

    private void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getLogin(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    abrirTelaPrincipal();
                    Toast.makeText(MainActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void abrirTelaPrincipal(){

        Intent intentAbrirTelaEscolha = new Intent(MainActivity.this, TelaEscolha.class);
        startActivity(intentAbrirTelaEscolha);
    }

    public void abreCadastroUsuario(){
        Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
        startActivity(intent);
        finish();
    }
}
