package com.example.henri.iwine.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.henri.iwine.DAO.ConfiguracaoFirebase;
import com.example.henri.iwine.Entidades.Usuarios;
import com.example.henri.iwine.Helper.Base64Custom;
import com.example.henri.iwine.Helper.Preferencias;
import com.example.henri.iwine.Helper.PreferenciasAndroid;
import com.example.henri.iwine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;


public class CadastroActivity extends AppCompatActivity {

    private EditText et_CadEMail;
    private EditText et_CadSenha;
    private EditText et_CadConfirmaSenha;
    private EditText et_CadNome;
    private EditText et_CadSobrenome;
    private EditText et_CadAniversario;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private Button bt_Gravar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;
    View viewCadastroActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        viewCadastroActivity = findViewById(R.id.viewCadastroActivity);
        viewCadastroActivity.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        et_CadEMail = (EditText)findViewById(R.id.et_CadEmail);
        et_CadSenha = (EditText)findViewById(R.id.et_CadSenha);
        et_CadConfirmaSenha = (EditText)findViewById(R.id.et_CadConfirmarSenha);
        et_CadNome = (EditText)findViewById(R.id.et_CadNome);
        et_CadSobrenome = (EditText)findViewById(R.id.et_CadSobrenome);
        et_CadAniversario= (EditText)findViewById(R.id.et_CadAniversario);
        rbMasculino = (RadioButton)findViewById(R.id.rbMasculino);
        rbFeminino = (RadioButton)findViewById(R.id.rbFeminino);
        bt_Gravar = (Button)findViewById(R.id.bt_Gravar);

        bt_Gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_CadSenha.getText().toString().equals(et_CadConfirmaSenha.getText().toString())){
                    usuarios = new Usuarios();
                    usuarios.setNome(et_CadNome.getText().toString());
                    usuarios.setSobrenome(et_CadSobrenome.getText().toString());
                    usuarios.setLogin(et_CadEMail.getText().toString());
                    usuarios.setSenha(et_CadSenha.getText().toString());
                    usuarios.setAniversario(et_CadAniversario.getText().toString());

                    if (rbFeminino.isChecked()){
                        usuarios.setSexo("Feminino");
                    }else{
                        usuarios.setSexo("Masculino");
                    }

                    cadastrarUsuario();

                }else{
                    Toast.makeText(CadastroActivity.this, "As senhas não são correspondendes", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(

            usuarios.getLogin(),
            usuarios.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso !", Toast.LENGTH_SHORT).show();

                    String identificadorUsuario = Base64Custom.codificarBase64(usuarios.getLogin());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();

                    PreferenciasAndroid preferenciasAndroid = new PreferenciasAndroid(CadastroActivity.this);
                    preferenciasAndroid.salvarUsuarioPreferencias(identificadorUsuario,usuarios.getNome());

                    abrirLoginUsuario();
                }else{
                    String erroExcecao = "";

                    try{

                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres entre numeros e letras.";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail.";
                    }catch (FirebaseAuthUserCollisionException e){
                    erroExcecao = "Esee e-mail já está cadastrado no sistema.";
                    }catch (Exception e){
                    erroExcecao = "Erroa ao efetuar o cadastro.";
                    e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, "Erro "+ erroExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
