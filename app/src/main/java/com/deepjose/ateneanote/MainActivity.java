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
        import com.deepjose.ateneanote.responses.ResponseOk;
        import com.deepjose.ateneanote.responses.ResponseUser;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.material.switchmaterial.SwitchMaterial;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

        import java.net.HttpURLConnection;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private static User user;
    private static boolean logged;
    private static boolean signedUp = false;
    private Button btnLog, btnReg, btnPass;
    private static SwitchMaterial remember;
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

        api = apiClient.getService("http://ateneanote.herokuapp.com/api/");

        SharedPreferences sharedPref = getSharedPreferences("fbuid", Context.MODE_PRIVATE);
        if(sharedPref.getBoolean("islogged", false)){
            getUser(sharedPref.getString("fbuid", ""));
        }

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
                            Toast.makeText(getApplicationContext(), "Email o contraseña incorrectos", Toast.LENGTH_LONG).show();
                        else {
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                String UID = mAuth.getCurrentUser().getUid();
                                getUser(UID);
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
                Log.println(Log.VERBOSE, "WWWWWWWWWWWWWWWWWW", response.toString());
                if(response.code()== HttpURLConnection.HTTP_OK){

                    //First we can save our login id for all the application
                    SharedPreferences sharedPref = getSharedPreferences("fbuid", Context.MODE_PRIVATE);
                    if(!sharedPref.getBoolean("islogged", false)) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("fbuid", response.body().getFbuid());
                        editor.putBoolean("islogged", MainActivity.remember.isChecked());
                        editor.commit();
                    }

                    //And now we can initialize our drawer activity
                    Intent intent = new Intent(MainActivity.this, MainDrawer.class) ;
                    MainActivity.user = new User(response.body().getName(), response.body().getSurname(), response.body().getEmail(), response.body().getFbuid());
                    MainActivity.logged = true;
                    //creamos el bundle e insertamos la instancia de user
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("User", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    //we logged in only in firebase so we need
                    // to create a new user in the api server
                    newUser(UID, email.getText().toString().split("[@]")[0], "", email.getText().toString(), "");
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                //we logged in only in firebase so we need
                // to create a new user in the api server

                newUser(UID, email.getText().toString().split("[@]")[0], "", email.getText().toString(), "");
                MainActivity.logged = true;
            }
        });
        return MainActivity.logged;
    }


    private boolean newUser(String fbuid, String name, String surname, String email, String pass){
        api.newUser(fbuid, name, surname, email, pass).enqueue(new Callback<ResponseOk>() {
            @Override
            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                Log.println(Log.VERBOSE, "WWWWWWWWWWWWWWWWWW", "MMMMMMMMMMMMMMMMMMMMMM");
                Log.println(Log.VERBOSE, "Response", response.toString());
                if(response.code()== HttpURLConnection.HTTP_OK){
                    //newUser will ONLY be called in this activity every time we authenticate into
                    // firebase but we can't do that in the api server and thats why we call
                    // again getUser to kind of solve any trouble.
                    getUser(fbuid);
                } else{
                    this.onFailure(call, new Exception());
                    MainActivity.signedUp = false;
                }
            }
//
            @Override
            public void onFailure(Call<ResponseOk> call, Throwable t) {
                MainActivity.signedUp = false;
                Toast.makeText(MainActivity.this, "No se ha podido establecer una conexión con el servidor...", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, "Compruebe su conexión e inténtelo de nuevo en unos minutos", Toast.LENGTH_LONG).show();
            }
//
            //@Override
            //public void onRespuonse(Call<ResponseUser> call, Response<ResponseUser> response) {
            //    if(response.code()== HttpURLConnection.HTTP_OK){
            //        MainActivity.user = new User(response.body().getName(), response.body().getSurname(), response.body().getEmail(), response.body().getFbuid());
            //        MainActivity.logged = true;
            //    } else {
            //        Toast.makeText(MainActivity.this, "Ususario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            //    }
            //}
//
            //@Override
            //public void onFauilure(Call<ResponseUser> call, Throwable t) {
            //    Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            //    MainActivity.logged = true;
            //}
        });
        return MainActivity.signedUp;
    }
}