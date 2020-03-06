package com.deepjose.ateneanote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deepjose.ateneanote.api.apiClient;
import com.deepjose.ateneanote.interfaces.apiService;
import com.deepjose.ateneanote.responses.ResponseOk;
import com.deepjose.ateneanote.responses.ResponseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.HttpURLConnection;

public class Register extends AppCompatActivity {
    private Button btnReg, btnBack;
    private EditText name, surname, email, pass ;
    private FirebaseAuth mAuth;
    private apiService api = apiClient.getService("https://ateneanote.herokuapp.com/api/");
    private static boolean signedUp = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnReg = findViewById(R.id.btnRegReg);
        btnBack = findViewById(R.id.btnBackReg);
        email = findViewById(R.id.userEmailReg);
        pass = findViewById(R.id.passwordReg);
        name = findViewById(R.id.userNameReg);
        surname = findViewById(R.id.userSurnameReg);


        mAuth = FirebaseAuth.getInstance();


        email.setText("jose98_21@hotmail.com");
        pass.setText("contraseña");
        name.setText("José");
        surname.setText("Robles Bastidas");

        btnBack.setOnClickListener(v -> {
            setResult(RESULT_CANCELED) ;
            finish() ;
            return ;
        });

        btnReg.setOnClickListener(view -> {

            final String nam = getField(name) ;
            final String surnam = getField(surname) ;
            final String ema = getField(email) ;
            final String password = getField(pass);

            mAuth.createUserWithEmailAndPassword(ema, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification();
                                // Now create a new user on the api server side
                                RequestBody requestBody = new MultipartBody.Builder()
                                        .setType(MultipartBody.FORM)
                                        .addFormDataPart("FBUID", user.getUid())
                                        .addFormDataPart("name", nam)
                                        .addFormDataPart("surname", surnam)
                                        .addFormDataPart("email", ema)
                                        .build();
                                newUser(user.getUid(), nam, surnam, ema, password);

                            } else {
                                Toast.makeText(Register.this, R.string.ya_registrada, Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        });

    }
    private String getField(EditText edit)
    {
        return edit.getText().toString().trim() ;
    }

    private void newUser(String fbuid, String name, String surname, String email, String pass){
        api.newUser(fbuid, name, surname, email, pass).enqueue(new Callback<ResponseOk>() {
            @Override
            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                Log.println(Log.VERBOSE, "WWWWWWWWWWWWWWWWWW", "MMMMMMMMMMMMMMMMMMMMMM");
                Log.println(Log.VERBOSE, "Response", response.toString());
                if(response.code()== HttpURLConnection.HTTP_OK){
                    new MaterialAlertDialogBuilder(Register.this)
                            .setTitle(getResources().getString(R.string.email_dialog_title))
                            .setMessage(getResources().getString(R.string.email_dialog_body))
                            .setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    setResult(RESULT_CANCELED) ;
                                    finish() ;
                                    return ;
                                }
                            })
                            .show();
                    mAuth.signOut();
                } else{
                    this.onFailure(call, new Exception());
                    mAuth.getCurrentUser().delete();
                }
            }
            //
            @Override
            public void onFailure(Call<ResponseOk> call, Throwable t) {
                Register.signedUp = false;
                Toast.makeText(Register.this, "No se ha podido establecer una conexión con el servidor...", Toast.LENGTH_LONG).show();
                Toast.makeText(Register.this, "Compruebe su conexión e inténtelo de nuevo en unos minutos", Toast.LENGTH_LONG).show();
            }




            //    .enqueue(new Callback<ResponseOk>() {
            //@Override
            //public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
            //    if(response.code()== HttpURLConnection.HTTP_OK){
            //        Register.signedUp = true;
            //    } else{
            //        this.onFailure(call, new Exception());
            //        Register.signedUp = false;
            //    }
            //}
//
            //@Override
            //public void onFailure(Call<ResponseOk> call, Throwable t) {
            //    Register.signedUp = false;
            //    Toast.makeText(Register.this, "No se ha podido establecer una conexión con el servidor...", Toast.LENGTH_LONG).show();
            //    Toast.makeText(Register.this, "Compruebe su conexión e inténtelo de nuevo en unos minutos", Toast.LENGTH_LONG).show();
            //}

            ////////////////////////////////

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
    }
}
