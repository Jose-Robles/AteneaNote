package com.deepjose.ateneanote;


        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.deepjose.ateneanote.api.apiClient;
        import com.deepjose.ateneanote.interfaces.apiService;
        import com.deepjose.ateneanote.responses.ResponseUser;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.material.switchmaterial.SwitchMaterial;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

        import java.net.HttpURLConnection;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private static User user;
    private static boolean logged;
    private Button btnLog, btnReg, btnPass;
    private SwitchMaterial remember;
    private EditText email, pass ;
    private FirebaseAuth mAuth;
    private apiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email    = findViewById(R.id.userEmail) ;
        pass     = findViewById(R.id.passWord) ;
        btnLog   = findViewById(R.id.btnLogin);
        btnReg   = findViewById(R.id.btnRegister);
        btnPass  = findViewById(R.id.btnChangePass);
        remember = findViewById(R.id.rememberMe);
        mAuth    = FirebaseAuth.getInstance();

        api = apiClient.getService("http://ateneanote.deepjose.com/flaskapp/api/");

        email.setText("jose98_21@hotmail.com");
        pass.setText("contraseña");

        btnReg.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Register.class) ;
            startActivity(intent) ;
        });

        btnPass.setOnClickListener(v -> Toast.makeText(this, "You clicked the button 'dont remember my account'", Toast.LENGTH_SHORT).show());

        btnLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String accessEmail = email.getText().toString();
                String accessPassword = pass.getText().toString();
                Toast.makeText(MainActivity.this, "Conectando...", Toast.LENGTH_LONG).show();
                mAuth.signInWithEmailAndPassword(accessEmail, accessPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (!task.isSuccessful())
                            Toast.makeText(getApplicationContext(), "Se ha producido un error en el login.", Toast.LENGTH_LONG).show();
                        else {
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                Intent intent = new Intent(MainActivity.this, MainDrawer.class) ;
                                String UID = mAuth.getCurrentUser().getUid();

                                //creamos el bundle e insertamos user
                                Toast.makeText(MainActivity.this, String.valueOf(getUser(UID)), Toast.LENGTH_SHORT).show();
                                if(getUser(UID)) {
                                    SharedPreferences localData = getSharedPreferences("localData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = localData.edit();
                                    editor.putString("FUID", UID);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("User", user);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                                //Toast.makeText(MainActivity.this, UID, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(MainActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Debes verificar tu correo primero!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                //Toast.makeText(MainActivity.this, currentUser.getUid(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "Se ha pulsado el botón con login: "+ accessEmail + " y " + accessPassword, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean getUser(String UID){

        api.getUser(UID).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if(response.code()== HttpURLConnection.HTTP_OK){
                    MainActivity.user = new User(response.body().getName(), response.body().getSurname(), response.body().getEmail(), response.body().getFbuid());
                    MainActivity.logged = true;
                } else {
                    Toast.makeText(MainActivity.this, "Ususario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                MainActivity.logged = true;
            }
        });
        return MainActivity.logged;
    }
}